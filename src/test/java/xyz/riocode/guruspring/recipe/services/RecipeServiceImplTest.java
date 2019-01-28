package xyz.riocode.guruspring.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.converters.RecipeCommandToRecipe;
import xyz.riocode.guruspring.recipe.converters.RecipeToRecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.exceptions.NotFoundException;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void testGetRecipes() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> recipesReturned = recipeService.getRecipes();

        assertEquals(recipesReturned, recipes);

        verify(recipeRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void testFindById() {
        Recipe recipeToBeReturned = new Recipe();
        recipeToBeReturned.setId("1");
        Optional<Recipe> recipeOpt = Optional.of(recipeToBeReturned);

        when(recipeRepository.findById(any())).thenReturn(recipeOpt);

        Recipe recipe = recipeService.findById("1");

        assertNotNull("Null recipe returned", recipe);
        verify(recipeRepository, times(1)).findById(any());
        verify(recipeRepository, never()).findAll();

    }

    @Test(expected = NotFoundException.class)
    public void testFindByIdNotFound(){
        when(recipeRepository.findById(any())).thenReturn(Optional.empty());

        Recipe recipe = recipeService.findById(any());
    }

    @Test
    public void testFindRecipeCommandById(){
        String id = "1";
        Recipe recipe = new Recipe();
        recipe.setId(id);

        RecipeCommand recipeCommandToBeReturned = new RecipeCommand();
        recipeCommandToBeReturned.setId(id);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(recipeToRecipeCommand.convert(any())).thenReturn((recipeCommandToBeReturned));

        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        assertNotNull(recipeCommand);
        assertEquals(id, recipeCommand.getId());
        verify(recipeRepository).findById(any());
    }

    @Test
    public void testDeleteById(){
        recipeService.deleteById("1");
        verify(recipeRepository).deleteById(any());
    }
}