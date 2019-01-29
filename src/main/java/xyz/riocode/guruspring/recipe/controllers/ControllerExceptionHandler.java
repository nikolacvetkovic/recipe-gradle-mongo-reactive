package xyz.riocode.guruspring.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberFormatException.class)
//    public ModelAndView handleNumberFormat(Exception ex){
//        log.error("handling number format exception");
//        log.error(ex.getMessage());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", ex);
//        modelAndView.setViewName("400error");
//
//        return modelAndView;
//    }

}
