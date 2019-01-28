package xyz.riocode.guruspring.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.converters.RecipeCommandToRecipe;
import xyz.riocode.guruspring.recipe.converters.RecipeToRecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.exceptions.NotFoundException;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipeSet = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }

    @Override
    public Recipe findById(String id) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if(!recipeOpt.isPresent()) {
            throw new NotFoundException("Recipe not found for ID="+ id +" !");
        }

        return recipeOpt.get();
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(String id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(String id){
        recipeRepository.deleteById(id);
    }
}
