package xyz.riocode.guruspring.recipe.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesToNotesCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand, CategoryToCategoryCommand categoryToCategoryCommand, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) return null;

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setNotes(notesToNotesCommand.convert(recipe.getNotes()));
        if(recipe.getCategories() != null && recipe.getCategories().size() > 0) {
            recipe.getCategories().forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }
        if(recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
            recipe.getIngredients().forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
