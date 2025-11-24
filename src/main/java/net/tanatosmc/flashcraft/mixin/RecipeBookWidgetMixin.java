package net.tanatosmc.flashcraft.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.tanatosmc.flashcraft.access.IRecipeBookResults;
import org.lwjgl.glfw.GLFW;
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

    /** Cooldown en milisegundos entre crafteos */
    @Unique
    private static final long COOLDOWN_MS = 150;

    @Unique
    private long lastCraftTime = 0;

    @Unique
    private boolean keyHeld = false;

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!isQuickCraftKey(keyCode)) return;
        if (!(this.recipesArea instanceof IRecipeBookResults accessor)) return;
        
        Recipe<?> recipe = accessor.quickcraft$getLastRecipe();
        RecipeResultCollection collection = accessor.quickcraft$getLastCollection();
        
        if (recipe == null || collection == null) return;
        
        // Verificar cooldown
        long now = System.currentTimeMillis();
        if (now - lastCraftTime < COOLDOWN_MS) {
            cir.setReturnValue(true);
            return;
        }
        
        lastCraftTime = now;
        keyHeld = true;
        
        boolean placeAll = Screen.hasShiftDown();
        this.client.interactionManager.clickRecipe(this.craftingScreenHandler.syncId, recipe, placeAll);
        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        
        cir.setReturnValue(true);
    }

    @Inject(method = "keyReleased", at = @At("HEAD"))
    private void onKeyReleased(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (isQuickCraftKey(keyCode)) {
            keyHeld = false;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Crafteo en lote: si la tecla está presionada, seguir crafteando
        if (!keyHeld) return;
        if (!(this.recipesArea instanceof IRecipeBookResults accessor)) return;
        
        long now = System.currentTimeMillis();
        if (now - lastCraftTime < COOLDOWN_MS) return;
        
        Recipe<?> recipe = accessor.quickcraft$getLastRecipe();
        RecipeResultCollection collection = accessor.quickcraft$getLastCollection();
        
        if (recipe == null || collection == null) return;
        
        lastCraftTime = now;
        
        boolean placeAll = Screen.hasShiftDown();
        this.client.interactionManager.clickRecipe(this.craftingScreenHandler.syncId, recipe, placeAll);
        this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
    
    @Unique
    private boolean isQuickCraftKey(int keyCode) {
        return keyCode == GLFW.GLFW_KEY_SPACE || keyCode == GLFW.GLFW_KEY_ENTER;
    }
}
