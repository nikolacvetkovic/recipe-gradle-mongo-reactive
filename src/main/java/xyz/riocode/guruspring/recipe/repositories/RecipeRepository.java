package xyz.riocode.guruspring.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.riocode.guruspring.recipe.domain.Recipe;


public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
