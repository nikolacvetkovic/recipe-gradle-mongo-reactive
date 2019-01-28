package xyz.riocode.guruspring.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.riocode.guruspring.recipe.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByDescription(String description);
}
