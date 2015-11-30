package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.server.exceptions.RestException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by daniel on 24.11.2015.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger Log = Logger.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = Throwable.class)
    public @ResponseBody Error defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {

        Log.error("Handle exception: "+exception.getMessage());

        if (exception instanceof RestException) {
            response.setStatus(((RestException)exception).getStatusCode().value());
        }
        else if (exception instanceof AccessDeniedException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return new Error(exception);
    }
}
