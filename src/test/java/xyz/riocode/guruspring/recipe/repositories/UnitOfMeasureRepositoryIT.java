package xyz.riocode.guruspring.recipe.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.riocode.guruspring.recipe.bootstrap.RecipeBootstrap;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    RecipeBootstrap recipeBootstrap;

    @Before
    public void setUp() throws Exception {
        recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);

        recipeBootstrap.onApplicationEvent(null);
    }


    @Test
    public void findByDescription() {

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", unitOfMeasure.get().getDescription());

    }

    @Test
    public void findByDescriptionCup(){
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", uomOptional.get().getDescription());
    }

    @After
    public void tearDown(){
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();
        recipeRepository.deleteAll();
    }
}