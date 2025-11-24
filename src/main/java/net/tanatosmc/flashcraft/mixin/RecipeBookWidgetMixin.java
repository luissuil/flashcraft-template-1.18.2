package net.tanatosmc.flashcraft.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.tanatosmc.flashcraft.access.IRecipeBookResults;
import net.tanatosmc.flashcraft.config.FlashCraftConfig;
import net.tanatosmc.flashcraft.crafting.CraftingState;
import net.tanatosmc.flashcraft.keybind.FlashCraftKeybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBookWidget.class)
public abstract class RecipeBookWidgetMixin {

    @Shadow
    private RecipeBookResults recipesArea;

    @Shadow
    protected MinecraftClient client;

    @Shadow
    protected AbstractRecipeScreenHandler<?> craftingScreenHandler;

    @Shadow
    private int leftOffset;

    @Shadow
    private int parentWidth;

    @Shadow
    private int parentHeight;

    @Unique
    private long lastCraftTime = 0;

    @Unique
    private boolean keyHeld = false;

    @Unique
    private int pendingCraftAmount = 0;

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        FlashCraftConfig config = FlashCraftConfig.get();
        CraftingState state = CraftingState.get();
        
        // Verificar si es tecla numérica para cantidad específica
        if (Screen.hasControlDown()) {
            int num = FlashCraftKeybindings.getNumberKey(keyCode);
            if (num > 0) {
                pendingCraftAmount = pendingCraftAmount * 10 + num;
                if (pendingCraftAmount > 64) pendingCraftAmount = 64; // Limitar a stack
                cir.setReturnValue(true);
                return;
            }
        }
        
        // Verificar tecla de favoritos
        if (FlashCraftKeybindings.isFavoriteKeyPressed(keyCode) && config.enableFavorites) {
            if (this.recipesArea instanceof IRecipeBookResults accessor) {
                Recipe<?> recipe = accessor.quickcraft$getLastRecipe();
                if (recipe != null) {
                    boolean added = state.toggleFavorite(recipe, config.maxFavorites);
                    // Sonido de feedback
                    float pitch = added ? 1.2F : 0.8F;
                    this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, pitch));
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
        
        // Verificar tecla de crafteo rápido
        if (FlashCraftKeybindings.isQuickCraftKeyPressed(keyCode) && this.recipesArea instanceof IRecipeBookResults) {
            IRecipeBookResults accessor = (IRecipeBookResults) this.recipesArea;
            Recipe<?> recipe = accessor.quickcraft$getLastRecipe();
            RecipeResultCollection collection = accessor.quickcraft$getLastCollection();
            
            if (recipe != null && collection != null) {
                // Configurar cantidad objetivo si hay pendiente
                if (pendingCraftAmount > 0) {
                    state.setTargetAmount(pendingCraftAmount);
                    pendingCraftAmount = 0;
                }
                
                // Verificar cooldown
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCraftTime < config.cooldownMs) {
                    if (config.playCooldownSound) {
                        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 0.5F));
                    }
                    cir.setReturnValue(true);
                    return;
                }
                
                // Verificar si ya alcanzamos la cantidad objetivo
                if (state.hasReachedTarget()) {
                    state.clearTarget();
                    this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F));
                    cir.setReturnValue(true);
                    return;
                }
                
                lastCraftTime = currentTime;
                keyHeld = true;

                boolean placeAll = Screen.hasShiftDown();
                this.client.interactionManager.clickRecipe(this.craftingScreenHandler.syncId, recipe, placeAll);
                
                // Actualizar contadores
                state.incrementCraftCount();
                state.incrementCurrentAmount();
                
                // Actualizar última receta
                state.setLastRecipe(recipe, collection);
                
                this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "keyReleased", at = @At("HEAD"))
    private void onKeyReleased(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (FlashCraftKeybindings.isQuickCraftKeyPressed(keyCode)) {
            keyHeld = false;
            CraftingState.get().stopBatchCrafting();
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        FlashCraftConfig config = FlashCraftConfig.get();
        CraftingState state = CraftingState.get();
        
        if (!config.showCraftCounter) return;
        
        // Mostrar contador si está activo
        if (state.shouldShowCounter(config.counterDisplayTimeMs)) {
            TextRenderer textRenderer = this.client.textRenderer;
            
            int x = (this.parentWidth - 147) / 2 - this.leftOffset;
            int y = (this.parentHeight - 166) / 2 + 10;
            
            String countText = "§a✦ " + state.getCraftCount();
            
            // Si hay objetivo, mostrar progreso
            if (state.getTargetAmount() > 0) {
                countText += " §7/ §e" + state.getTargetAmount();
            }
            
            // Mostrar índice de favorito si aplica
            Recipe<?> lastRecipe = state.getLastRecipe();
            if (config.enableFavorites && lastRecipe != null) {
                int favIndex = state.getFavoriteIndex(lastRecipe);
                if (favIndex >= 0) {
                    countText += " §6★" + (favIndex + 1);
                }
            }
            
            // Renderizar con fondo
            int textWidth = textRenderer.getWidth(countText.replaceAll("§.", ""));
            fill(matrices, x - 2, y - 2, x + textWidth + 2, y + 10, 0x80000000);
            textRenderer.drawWithShadow(matrices, Text.of(countText), x, y, 0xFFFFFF);
        }
        
        // Mostrar cantidad pendiente
        if (pendingCraftAmount > 0) {
            TextRenderer textRenderer = this.client.textRenderer;
            int x = (this.parentWidth - 147) / 2 - this.leftOffset;
            int y = (this.parentHeight - 166) / 2 + 25;
            
            String amountText = "§bCantidad: §f" + pendingCraftAmount;
            textRenderer.drawWithShadow(matrices, Text.of(amountText), x, y, 0xFFFFFF);
        }
        
        // Crafteo en lote: si la tecla está presionada, seguir crafteando
        if (keyHeld && config.enableBatchCrafting) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastCraftTime >= config.cooldownMs) {
                if (this.recipesArea instanceof IRecipeBookResults accessor) {
                    Recipe<?> recipe = accessor.quickcraft$getLastRecipe();
                    RecipeResultCollection collection = accessor.quickcraft$getLastCollection();
                    
                    if (recipe != null && collection != null && !state.hasReachedTarget()) {
                        lastCraftTime = currentTime;
                        
                        boolean placeAll = Screen.hasShiftDown();
                        this.client.interactionManager.clickRecipe(this.craftingScreenHandler.syncId, recipe, placeAll);
                        
                        state.incrementCraftCount();
                        state.incrementCurrentAmount();
                        state.setLastRecipe(recipe, collection);
                        
                        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    }
                }
            }
        }
    }
    
    @Unique
    private static void fill(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        Screen.fill(matrices, x1, y1, x2, y2, color);
    }
}
