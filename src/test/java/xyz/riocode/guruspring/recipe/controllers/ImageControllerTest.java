package xyz.riocode.guruspring.recipe.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.services.ImageService;
import xyz.riocode.guruspring.recipe.services.RecipeService;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    ImageController imageController;

    ControllerExceptionHandler controllerExceptionHandler;

    ServletContext servletContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService, recipeService, servletContext);
        controllerExceptionHandler = new ControllerExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    public void getNewImageForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeService.findCommandById(any())).thenReturn(Mono.just(recipeCommand));

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findCommandById(any());
    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain", "SpringFramework".getBytes());

        when(imageService.saveImageFile(any(), any())).thenReturn(Mono.empty());

        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService).saveImageFile(any(), any());
    }

    @Test
    public void testGetRecipeImage() throws Exception {
        Recipe recipe = new Recipe();
        String s = "This is picture";

        Byte[] image = new Byte[s.getBytes().length];

        int i = 0;
        for (byte b : s.getBytes()){
            image[i++] = b;
        }

        recipe.setImage(image);

        when(recipeService.findById(any())).thenReturn(Mono.just(recipe));

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertArrayEquals(s.getBytes(), responseBytes);
    }

    @Ignore
    @Test
    public void testGetImageNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/asdf/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}