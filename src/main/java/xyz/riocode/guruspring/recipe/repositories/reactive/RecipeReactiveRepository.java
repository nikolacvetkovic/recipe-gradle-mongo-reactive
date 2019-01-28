package xyz.riocode.guruspring.recipe.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import xyz.riocode.guruspring.recipe.domain.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
