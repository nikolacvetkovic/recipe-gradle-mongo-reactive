package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.CategoryCommand;
import xyz.riocode.guruspring.recipe.domain.Category;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";

    private CategoryCommandToCategory categoryCommandToCategory;

    @Before
    public void setUp() throws Exception {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void testConvert() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);

        Category category = categoryCommandToCategory.convert(categoryCommand);

        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void testConvertNullObject(){
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    public void testConvertEmptyObject(){
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }
}