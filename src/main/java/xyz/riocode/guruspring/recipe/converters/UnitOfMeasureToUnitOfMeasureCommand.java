package xyz.riocode.guruspring.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if(unitOfMeasure == null) return null;

        final UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(unitOfMeasure.getId());
        uomCommand.setDescription(unitOfMeasure.getDescription());

        return uomCommand;
    }


}
