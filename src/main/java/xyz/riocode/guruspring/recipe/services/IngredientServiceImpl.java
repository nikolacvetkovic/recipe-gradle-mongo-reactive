package xyz.riocode.guruspring.recipe.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;
import xyz.riocode.guruspring.recipe.converters.IngredientCommandToIngredient;
import xyz.riocode.guruspring.recipe.converters.IngredientToIngredientCommand;
import xyz.riocode.guruspring.recipe.domain.Ingredient;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;
import xyz.riocode.guruspring.recipe.repositories.UnitOfMeasureRepository;
import xyz.riocode.guruspring.recipe.repositories.reactive.RecipeReactiveRepository;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

//        return recipeReactiveRepository.findById(recipeId)
//                    .map(recipe -> recipe.getIngredients()
//                    .stream()
//                    .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
//                    .findFirst())
//                .filter(Optional::isPresent)
//                .map(ingredient -> {
//                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient.get());
//                    command.setRecipeId(recipeId);
//                    return command;
//                });

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            //todo impl error handling
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            // todo impl error handling
        }

//        enhance command object with recipe id
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipe.getId());

        return Mono.just(ingredientCommandOptional.get());
    }

    @Transactional
    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            // todo throw error if not found
            return Mono.just(new IngredientCommand());
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom()
                                                    .getId()).orElseThrow(() -> new RuntimeException("UOM Not Found"))); //todo
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                //ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());
            return Mono.just(ingredientCommandSaved);
        }

    }

    public Mono<Void> deleteIngredientById(String recipeId, String ingredientId){
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){

        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                //ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);
            }
        }
        return Mono.empty();
    }
}
