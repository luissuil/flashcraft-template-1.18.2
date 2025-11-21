package net.tanatosmc.flashcraft.mixin;

import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.Recipe;
import net.tanatosmc.flashcraft.access.IRecipeBookResults;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeBookResults.class)
public class RecipeBookResultsMixin implements IRecipeBookResults {

    @Shadow
    private List<AnimatedResultButton> resultButtons;

    @Unique
    private Recipe<?> lastRecipe;

    @Unique
    private RecipeResultCollection lastCollection;

    @Override
    public void quickcraft$setLastRecipe(Recipe<?> recipe) {
        this.lastRecipe = recipe;
    }

    @Override
    public void quickcraft$setLastCollection(RecipeResultCollection collection) {
        this.lastCollection = collection;
    }

    @Override
    public Recipe<?> quickcraft$getLastRecipe() {
        return this.lastRecipe;
    }

    @Override
    public RecipeResultCollection quickcraft$getLastCollection() {
        return this.lastCollection;
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void onMouseClicked(double mouseX, double mouseY, int button, int areaLeft, int areaTop, int areaWidth, int areaHeight, CallbackInfoReturnable<Boolean> cir) {
        for (AnimatedResultButton resultButton : this.resultButtons) {
            if (resultButton.isMouseOver(mouseX, mouseY) && resultButton.visible) {
                this.lastRecipe = resultButton.currentRecipe();
                this.lastCollection = resultButton.getResultCollection();
            }
        }
    }
}
