package xyz.riocode.guruspring.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

    Mono<UnitOfMeasure> findByDescription(String description);
}
