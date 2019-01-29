package xyz.riocode.guruspring.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.converters.RecipeCommandToRecipe;
import xyz.riocode.guruspring.recipe.converters.RecipeToRecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;
import xyz.riocode.guruspring.recipe.repositories.reactive.RecipeReactiveRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void testGetRecipes() {

        Recipe recipe = new Recipe();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        List<Recipe> recipesReturned = recipeService.getRecipes().collectList().block();

        assertEquals(recipesReturned, recipes);

        verify(recipeReactiveRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void testFindById() {
        Recipe recipeToBeReturned = new Recipe();
        recipeToBeReturned.setId("1");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipeToBeReturned));

        Recipe recipe = recipeService.findById("1").block();

        assertNotNull("Null recipe returned", recipe);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();

    }

    @Test
    public void testFindRecipeCommandById(){
        String id = "1";
        Recipe recipe = new Recipe();
        recipe.setId(id);

        RecipeCommand recipeCommandToBeReturned = new RecipeCommand();
        recipeCommandToBeReturned.setId(id);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeToRecipeCommand.convert(any())).thenReturn((recipeCommandToBeReturned));

        RecipeCommand recipeCommand = recipeService.findCommandById(id).block();

        assertNotNull(recipeCommand);
        assertEquals(id, recipeCommand.getId());
        verify(recipeReactiveRepository).findById(anyString());
    }

    @Test
    public void testDeleteById(){
        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());
        recipeService.deleteById("1");
        verify(recipeReactiveRepository).deleteById(anyString());
    }
}