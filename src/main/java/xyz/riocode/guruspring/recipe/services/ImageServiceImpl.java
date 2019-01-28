package xyz.riocode.guruspring.recipe.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.repositories.RecipeRepository;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String recipeId, MultipartFile multipartFile) {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        //check if recipe exists
        try {
            Byte[] imageBytes = new Byte[multipartFile.getBytes().length];

            int i = 0;

            for (byte b : multipartFile.getBytes()){
                imageBytes[i++] = b;
            }

            recipe.setImage(imageBytes);

            recipeRepository.save(recipe);

        } catch (IOException e) {
            //todo
            e.printStackTrace();
        }
    }
}
