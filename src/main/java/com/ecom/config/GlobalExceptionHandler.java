package com.ecom.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        System.err.println("Global Exception Handler caught: " + ex.getMessage());
        ex.printStackTrace();
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMsg", "An error occurred: " + ex.getMessage());
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("error");
        
        return modelAndView;
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        System.err.println("Global Runtime Exception Handler caught: " + ex.getMessage());
        ex.printStackTrace();
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMsg", "A runtime error occurred: " + ex.getMessage());
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("error");
        
        return modelAndView;
    }
}
