package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";

    private UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Before
    public void setUp() throws Exception {
        uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testConvert() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        UnitOfMeasure unitOfMeasure = uomConverter.convert(unitOfMeasureCommand);

        assertNotNull(unitOfMeasure);
        assertEquals(ID, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());

    }

    @Test
    public void testConvertNullObject() {
        assertNull(uomConverter.convert(null));
    }

    @Test
    public void testConvertEmptyObject() {
        assertNotNull(uomConverter.convert(new UnitOfMeasureCommand()));
    }
}