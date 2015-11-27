package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by daniel on 24.11.2015.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger Log = Logger.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody Error defaultExceptionHandler(HttpServletRequest request, Exception exception) {

        Log.error("Handle exception: "+exception.getMessage());

        return new Error(exception);
    }
}
