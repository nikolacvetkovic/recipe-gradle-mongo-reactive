package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";

    private UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @Before
    public void setUp() throws Exception {
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testConvert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);

        UnitOfMeasureCommand unitOfMeasureCommand = uomConverter.convert(unitOfMeasure);

        assertNotNull(unitOfMeasureCommand);
        assertEquals(ID, unitOfMeasureCommand.getId());
        assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());

    }

    @Test
    public void testConvertNullObject() {
        assertNull(uomConverter.convert(null));
    }

    @Test
    public void testConvertEmptyObject() {
        assertNotNull(uomConverter.convert(new UnitOfMeasure()));
    }
}