package xyz.riocode.guruspring.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.domain.Category;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

    Mono<Category> findByDescription(String description);
}
