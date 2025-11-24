package net.tanatosmc.flashcraft.crafting;

import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Estado global del sistema de crafteo rápido
 */
public class CraftingState {
    
    private static final CraftingState INSTANCE = new CraftingState();
    
    // ===== CONTADOR DE CRAFTEOS =====
    private int sessionCraftCount = 0;
    private long lastCraftDisplayTime = 0;
    
    // ===== CRAFTEO EN LOTE =====
    private boolean isBatchCrafting = false;
    private int batchCraftCount = 0;
    
    // ===== CANTIDAD ESPECÍFICA =====
    private int targetAmount = 0;
    private int currentAmount = 0;
    
    // ===== FAVORITOS =====
    private final List<Identifier> favoriteRecipes = new ArrayList<>();
    
    // ===== ÚLTIMA RECETA =====
    private Recipe<?> lastRecipe;
    private RecipeResultCollection lastCollection;
    
    public static CraftingState get() {
        return INSTANCE;
    }
    
    // ===== CONTADOR =====
    
    public void incrementCraftCount() {
        sessionCraftCount++;
        lastCraftDisplayTime = System.currentTimeMillis();
    }
    
    public int getCraftCount() {
        return sessionCraftCount;
    }
    
    public void resetCraftCount() {
        sessionCraftCount = 0;
    }
    
    public long getLastCraftDisplayTime() {
        return lastCraftDisplayTime;
    }
    
    public boolean shouldShowCounter(int displayTimeMs) {
        return System.currentTimeMillis() - lastCraftDisplayTime < displayTimeMs;
    }
    
    // ===== CRAFTEO EN LOTE =====
    
    public void startBatchCrafting() {
        isBatchCrafting = true;
        batchCraftCount = 0;
    }
    
    public void stopBatchCrafting() {
        isBatchCrafting = false;
        batchCraftCount = 0;
    }
    
    public boolean isBatchCrafting() {
        return isBatchCrafting;
    }
    
    public void incrementBatchCount() {
        batchCraftCount++;
    }
    
    public int getBatchCount() {
        return batchCraftCount;
    }
    
    // ===== CANTIDAD ESPECÍFICA =====
    
    public void setTargetAmount(int amount) {
        this.targetAmount = amount;
        this.currentAmount = 0;
    }
    
    public int getTargetAmount() {
        return targetAmount;
    }
    
    public void incrementCurrentAmount() {
        currentAmount++;
    }
    
    public int getCurrentAmount() {
        return currentAmount;
    }
    
    public boolean hasReachedTarget() {
        return targetAmount > 0 && currentAmount >= targetAmount;
    }
    
    public void clearTarget() {
        targetAmount = 0;
        currentAmount = 0;
    }
    
    // ===== FAVORITOS =====
    
    public boolean isFavorite(Recipe<?> recipe) {
        if (recipe == null) return false;
        return favoriteRecipes.contains(recipe.getId());
    }
    
    public boolean toggleFavorite(Recipe<?> recipe, int maxFavorites) {
        if (recipe == null) return false;
        
        Identifier id = recipe.getId();
        if (favoriteRecipes.contains(id)) {
            favoriteRecipes.remove(id);
            return false;
        } else {
            if (favoriteRecipes.size() < maxFavorites) {
                favoriteRecipes.add(id);
                return true;
            }
            return false;
        }
    }
    
    public List<Identifier> getFavorites() {
        return new ArrayList<>(favoriteRecipes);
    }
    
    public Identifier getFavorite(int index) {
        if (index >= 0 && index < favoriteRecipes.size()) {
            return favoriteRecipes.get(index);
        }
        return null;
    }
    
    public int getFavoriteIndex(Recipe<?> recipe) {
        if (recipe == null) return -1;
        return favoriteRecipes.indexOf(recipe.getId());
    }
    
    // ===== ÚLTIMA RECETA =====
    
    public void setLastRecipe(Recipe<?> recipe, RecipeResultCollection collection) {
        this.lastRecipe = recipe;
        this.lastCollection = collection;
    }
    
    public Recipe<?> getLastRecipe() {
        return lastRecipe;
    }
    
    public RecipeResultCollection getLastCollection() {
        return lastCollection;
    }
}
