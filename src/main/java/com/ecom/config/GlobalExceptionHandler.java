package com.ecom.config;

import java.io.IOException;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        if (ex instanceof NoResourceFoundException) {
            return null; // Let default 404 handling proceed silently
        }
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

    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbort(HttpServletRequest request, ClientAbortException ex) {
        System.err.println("Client aborted connection while handling: " + request.getRequestURI());
    }

    @ExceptionHandler(IOException.class)
    public void handleIOException(HttpServletRequest request, IOException ex) throws IOException {
        if (isBrokenPipe(ex)) {
            System.err.println("Broken pipe detected for: " + request.getRequestURI());
            return;
        }
        throw ex;
    }

    private boolean isBrokenPipe(Throwable ex) {
        Throwable current = ex;
        int depth = 0;
        while (current != null && depth < 10) {
            String message = current.getMessage();
            if (message != null && message.toLowerCase().contains("broken pipe")) {
                return true;
            }
            current = current.getCause();
            depth++;
        }
        return false;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNoResourceFound(HttpServletRequest request, NoResourceFoundException ex) {
        // Quietly return 404 without stack trace noise
    }
}
