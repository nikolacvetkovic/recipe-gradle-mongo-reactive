package xyz.riocode.guruspring.recipe.repositories.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.riocode.guruspring.recipe.domain.UnitOfMeasure;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    public static final String EACH = "Each";

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveUom() throws Exception {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(EACH);

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        Long count = unitOfMeasureReactiveRepository.count().block();

        assertEquals(Long.valueOf(1), count);
    }

    @Test
    public void testFindByDescription() throws Exception {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(EACH);

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure unitOfMeasure1 = unitOfMeasureReactiveRepository.findByDescription(EACH).block();

        assertEquals(EACH, unitOfMeasure1.getDescription());
    }

}
