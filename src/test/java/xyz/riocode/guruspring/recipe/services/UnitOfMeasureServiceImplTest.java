package xyz.riocode.guruspring.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;
import xyz.riocode.guruspring.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasureServiceImpl unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId("1");

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId("2");

        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        unitOfMeasures.add(unitOfMeasure1);
        unitOfMeasures.add(unitOfMeasure2);

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(unitOfMeasure1, unitOfMeasure2));

        List<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms().collectList().block();

        assertEquals(2, unitOfMeasureCommands.size());
    }
}