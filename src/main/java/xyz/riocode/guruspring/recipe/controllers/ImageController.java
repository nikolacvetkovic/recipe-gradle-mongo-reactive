package xyz.riocode.guruspring.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import xyz.riocode.guruspring.recipe.services.ImageService;
import xyz.riocode.guruspring.recipe.services.RecipeService;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getNewImageForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam(name = "imagefile") MultipartFile multipartFile){

        imageService.saveImageFile(recipeId, multipartFile).block();

        return "redirect:/recipe/" + recipeId + "/show";
    }

//    @GetMapping(value = "/recipe/{recipeId}/recipeimage", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void getRecipeImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
//
//        Recipe recipe = recipeService.findById(recipeId).block();
//        //todo check image exists
//
//        if(recipe.getImage() == null){
//            InputStream in = getClass().getResourceAsStream("/no-image-icon-23.jpg");
//            IOUtils.copy(in, response.getOutputStream());
//        } else {
//            byte[] byteArray = new byte[recipe.getImage().length];
//            int i = 0;
//
//            for (Byte b : recipe.getImage()) {
//                byteArray[i++] = b;
//            }
//
//            InputStream is = new ByteArrayInputStream(byteArray);
//            IOUtils.copy(is, response.getOutputStream());
//        }
//    }
}
