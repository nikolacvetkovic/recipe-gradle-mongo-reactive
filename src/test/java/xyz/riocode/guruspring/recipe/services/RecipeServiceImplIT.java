package xyz.riocode.guruspring.recipe.services;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.converters.RecipeCommandToRecipe;
import xyz.riocode.guruspring.recipe.converters.RecipeToRecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceImplIT {

    private static final String DESCRIPTION = "Description";

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Ignore
    @Transactional
    @Test
    public void testSaveOfDescription() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();

        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        testRecipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        assertEquals(DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
