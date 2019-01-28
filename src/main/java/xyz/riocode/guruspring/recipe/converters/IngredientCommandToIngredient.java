package xyz.riocode.guruspring.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;
import xyz.riocode.guruspring.recipe.domain.Ingredient;
import xyz.riocode.guruspring.recipe.domain.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null) return null;

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setAmount(ingredientCommand.getAmount());
        if(ingredientCommand.getRecipeId() != null) {
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
            //ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setUom(uomConverter.convert(ingredientCommand.getUom()));

        return ingredient;
    }
}
