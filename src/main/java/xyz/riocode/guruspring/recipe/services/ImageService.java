package xyz.riocode.guruspring.recipe.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<Void> saveImageFile(String recipeId, MultipartFile multipartFile);
}
