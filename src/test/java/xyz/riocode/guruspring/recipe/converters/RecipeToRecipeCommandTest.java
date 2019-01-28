package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.domain.*;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

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

    private NotesToNotesCommand notesToNotesCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        notesToNotesCommand = new NotesToNotesCommand();
        categoryToCategoryCommand = new CategoryToCategoryCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        recipeToRecipeCommand = new RecipeToRecipeCommand(notesToNotesCommand, categoryToCategoryCommand, ingredientToIngredientCommand);
    }

    @Test
    public void testConvert() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setUrl(URL);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setSource(SOURCE);
        recipe.setServings(SERVINGS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDescription(DESCRIPTION);
        recipe.setDirections(DIRECTIONS);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        Category category1 = new Category();
        category1.setId(CAT1_ID);
        recipe.getCategories().add(category1);
        Category category2 = new Category();
        category2.setId(CAT2_ID);
        recipe.getCategories().add(category2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED1_ID);
        recipe.getIngredients().add(ingredient1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED2_ID);
        recipe.getIngredients().add(ingredient2);

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        assertNotNull(recipeCommand);
        assertEquals(ID, recipeCommand.getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }

    @Test
    public void testConvertEmptyObject(){
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }

    @Test
    public void testConvertNullObject(){
        assertNull(recipeToRecipeCommand.convert(null));
    }
}