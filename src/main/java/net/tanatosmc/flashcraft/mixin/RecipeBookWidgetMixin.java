package net.tanatosmc.flashcraft.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.tanatosmc.flashcraft.access.IRecipeBookResults;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBookWidget.class)
public class RecipeBookWidgetMixin {

    @Shadow
    private RecipeBookResults recipesArea;

    @Shadow
    protected MinecraftClient client;

    @Shadow
    protected AbstractRecipeScreenHandler<?> craftingScreenHandler;

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if ((keyCode == GLFW.GLFW_KEY_SPACE || keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) && this.recipesArea instanceof IRecipeBookResults) {
            IRecipeBookResults accessor = (IRecipeBookResults) this.recipesArea;
            Recipe<?> recipe = accessor.quickcraft$getLastRecipe();
            RecipeResultCollection collection = accessor.quickcraft$getLastCollection();
            
            if (recipe != null && collection != null) {
                boolean placeAll = Screen.hasShiftDown();
                this.client.interactionManager.clickRecipe(this.craftingScreenHandler.syncId, recipe, placeAll);
                this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                cir.setReturnValue(true);
            }
        }
    }
}
