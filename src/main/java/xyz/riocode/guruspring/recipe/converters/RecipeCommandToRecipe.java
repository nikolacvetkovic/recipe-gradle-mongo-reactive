package xyz.riocode.guruspring.recipe.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory, IngredientCommandToIngredient ingredientCommandToIngredient, NotesCommandToNotes notesCommandToNotes) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null) return null;

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setNotes(notesCommandToNotes.convert(recipeCommand.getNotes()));
        if(recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0) {
            recipeCommand.getCategories().forEach(categoryCommand -> recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand)));
        }

        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients().forEach(ingredientCommand -> recipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand)));
        }

        return recipe;
    }
}
