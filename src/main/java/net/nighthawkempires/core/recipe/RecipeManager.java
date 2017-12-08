package net.nighthawkempires.core.recipe;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

import java.util.List;

public class RecipeManager {

    private List<Recipe> recipes;

    public RecipeManager() {
        recipes = Lists.newArrayList();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void registerRecipe(Recipe recipe) {
        getRecipes().add(recipe);
        Bukkit.getServer().addRecipe(recipe);
    }

    public void resetRecipes() {
        Bukkit.getServer().resetRecipes();
    }
}
