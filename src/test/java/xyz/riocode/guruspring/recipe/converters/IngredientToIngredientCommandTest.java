package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;
import xyz.riocode.guruspring.recipe.domain.Ingredient;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal AMOUNT = new BigDecimal(1000);
    private static final String UOM_ID = "5";

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    private Ingredient ingredient;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
        ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
    }

    @Test
    public void testConvert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        ingredient.setUom(unitOfMeasure);

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUom());
        assertEquals(ID, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(UOM_ID, ingredientCommand.getUom().getId());


    }

    @Test
    public void testConvertWithNullUom() {

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertNotNull(ingredientCommand);
        assertNull(ingredientCommand.getUom());
        assertEquals(ID, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
    }

    @Test
    public void testConvertNullObject(){
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    public void testConvertEmptyObject(){
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }
}