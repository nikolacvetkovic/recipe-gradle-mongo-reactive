package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.CategoryCommand;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;
import xyz.riocode.guruspring.recipe.commands.NotesCommand;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Difficulty;
import xyz.riocode.guruspring.recipe.domain.Recipe;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";
    private static final Integer COOK_TIME = 5;
    private static final Integer PREP_TIME = 10;
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final String SOURCE = "Source";
    private static final Integer SERVINGS = 4;
    private static final String URL = "Url";
    private static final String NOTES_ID = "5";
    private static final String CAT1_ID = "1";
    private static final String CAT2_ID = "2";
    private static final String INGRED1_ID = "1";
    private static final String INGRED2_ID = "2";

    private CategoryCommandToCategory categoryCommandToCategory;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private NotesCommandToNotes notesCommandToNotes;
    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        categoryCommandToCategory = new CategoryCommandToCategory();
        ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
        notesCommandToNotes = new NotesCommandToNotes();
        recipeCommandToRecipe = new RecipeCommandToRecipe(categoryCommandToCategory, ingredientCommandToIngredient, notesCommandToNotes);
    }

    @Test
    public void testConvert() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setUrl(URL);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT1_ID);
        recipeCommand.getCategories().add(categoryCommand1);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT2_ID);
        recipeCommand.getCategories().add(categoryCommand2);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGRED1_ID);
        recipeCommand.getIngredients().add(ingredientCommand1);
        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(INGRED2_ID);
        recipeCommand.getIngredients().add(ingredientCommand2);

        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());

    }

    @Test
    public void testConvertNullObject() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    public void testConvertEmptyObject() {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }
}