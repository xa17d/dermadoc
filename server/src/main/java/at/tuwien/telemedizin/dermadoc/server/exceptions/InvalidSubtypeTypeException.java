package at.tuwien.telemedizin.dermadoc.server.exceptions;

import at.tuwien.telemedizin.dermadoc.entities.User;
import org.springframework.http.HttpStatus;

/**
 * Created by daniel on 27.11.2015.
 */
public class InvalidSubtypeTypeException extends RestException {
    public InvalidSubtypeTypeException(Class<?> baseClass, Class<?> invalidClass) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "invalid subtype of "+baseClass.getSimpleName()+": "+invalidClass.getName());
    }
}
