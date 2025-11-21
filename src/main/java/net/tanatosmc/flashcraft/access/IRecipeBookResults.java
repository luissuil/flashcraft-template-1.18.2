package net.tanatosmc.flashcraft.access;

import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.Recipe;

public interface IRecipeBookResults {
    void quickcraft$setLastRecipe(Recipe<?> recipe);
    void quickcraft$setLastCollection(RecipeResultCollection collection);
    Recipe<?> quickcraft$getLastRecipe();
    RecipeResultCollection quickcraft$getLastCollection();
}
