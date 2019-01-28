package xyz.riocode.guruspring.recipe.services;

import reactor.core.publisher.Flux;
import xyz.riocode.guruspring.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();


}
