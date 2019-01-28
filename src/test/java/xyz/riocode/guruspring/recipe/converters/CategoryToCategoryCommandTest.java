package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.CategoryCommand;
import xyz.riocode.guruspring.recipe.domain.Category;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    private static final String ID = "1";
    private static final String DESCRIPTION = "Description";

    private CategoryToCategoryCommand categoryToCategoryCommand;

    @Before
    public void setUp() throws Exception {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void testConvert() {
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }

    @Test
    public void testConvertNullObject(){
        assertNull(categoryToCategoryCommand.convert(null));
    }

    @Test
    public void testConvertEmptyObject(){
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

}