package xyz.riocode.guruspring.recipe.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.riocode.guruspring.recipe.commands.CategoryCommand;
import xyz.riocode.guruspring.recipe.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if(categoryCommand == null){
            return null;
        }

        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setDescription(categoryCommand.getDescription());

        return category;
    }
}
