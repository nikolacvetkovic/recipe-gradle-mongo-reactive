package xyz.riocode.guruspring.recipe.services;

import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
    Mono<Void> deleteIngredientById(String recipeId, String ingredientId);

}
