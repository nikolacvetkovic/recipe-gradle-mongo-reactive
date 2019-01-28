package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.IngredientCommand;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.domain.Ingredient;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal AMOUNT = new BigDecimal(1000);
    private static final String UOM_ID = "5";

    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    private IngredientCommand ingredientCommand;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
        ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
    }

    @Test
    public void testConvert() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUom(unitOfMeasureCommand);

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID, ingredient.getUom().getId());

    }

    @Test
    public void testConvertWithNullUom(){
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
    }

    @Test
    public void testConvertWithNullObject(){
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    public void testConvertWithEmptyObject(){
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }
}