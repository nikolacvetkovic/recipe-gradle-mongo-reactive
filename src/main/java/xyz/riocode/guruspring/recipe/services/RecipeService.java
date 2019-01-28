package xyz.riocode.guruspring.recipe.services;

import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findCommandById(String id);
    void deleteById(String id);
}
