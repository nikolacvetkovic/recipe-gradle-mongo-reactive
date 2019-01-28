package xyz.riocode.guruspring.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
