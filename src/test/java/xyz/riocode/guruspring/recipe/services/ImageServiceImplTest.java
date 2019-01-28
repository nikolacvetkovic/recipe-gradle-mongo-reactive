package xyz.riocode.guruspring.recipe.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageServiceImpl imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws IOException {
        //given
        String id = "1";
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "SpringFramework".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, multipartFile);

        //then
        verify(recipeRepository).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        Assert.assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);


    }
}