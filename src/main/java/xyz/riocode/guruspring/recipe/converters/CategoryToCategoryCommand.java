package xyz.riocode.guruspring.recipe.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.riocode.guruspring.recipe.commands.CategoryCommand;
import xyz.riocode.guruspring.recipe.domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if(category == null) return null;

        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(category.getId());
        categoryCommand.setDescription(category.getDescription());

        return categoryCommand;
    }
}
