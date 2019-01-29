package xyz.riocode.guruspring.recipe.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import xyz.riocode.guruspring.recipe.domain.Recipe;
import xyz.riocode.guruspring.recipe.repositories.reactive.RecipeReactiveRepository;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeReactiveRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile) {
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    try {
                        Byte[] imageBytes = new Byte[multipartFile.getBytes().length];
                        int i = 0;

                        for (byte b : multipartFile.getBytes()) {
                            imageBytes[i++] = b;
                        }

                        recipe.setImage(imageBytes);

                        return recipe;

                    } catch (IOException e){
                        //todo
                        throw new RuntimeException(e);
                    }
                });

        recipeReactiveRepository.save(recipeMono.block()).block();

        return Mono.empty();
        //check if recipe exists
//        try {
//            Byte[] imageBytes = new Byte[multipartFile.getBytes().length];
//
//            int i = 0;
//
//            for (byte b : multipartFile.getBytes()){
//                imageBytes[i++] = b;
//            }
//
//            recipe.setImage(imageBytes);
//
//            recipeReactiveRepository.save(recipe);
//
//        } catch (IOException e) {
//            //todo
//            e.printStackTrace();
//        }
    }
}
