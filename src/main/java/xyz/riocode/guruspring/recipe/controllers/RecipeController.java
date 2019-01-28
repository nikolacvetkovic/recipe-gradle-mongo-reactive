package xyz.riocode.guruspring.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.riocode.guruspring.recipe.commands.RecipeCommand;
import xyz.riocode.guruspring.recipe.exceptions.NotFoundException;
import xyz.riocode.guruspring.recipe.services.RecipeService;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "/recipe/recipeform";
        }

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/"+ savedRecipeCommand.getId() +"/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable String id){
        recipeService.deleteById(id);

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex){
        log.error("handling not found exception");
        log.error(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.setViewName("404error");

        return modelAndView;
    }
}
