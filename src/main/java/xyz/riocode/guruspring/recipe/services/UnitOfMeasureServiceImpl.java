package xyz.riocode.guruspring.recipe.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.repositories.UnitOfMeasureRepository;
import xyz.riocode.guruspring.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

        return unitOfMeasureReactiveRepository.findAll()
                                                .map(unitOfMeasureToUnitOfMeasureCommand::convert);

//        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
//                            .map(unitOfMeasureToUnitOfMeasureCommand::convert)
//                            .collect(Collectors.toSet());
    }
}
