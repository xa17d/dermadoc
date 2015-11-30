package at.tuwien.telemedizin.dermadoc.server.exceptions;

import at.tuwien.telemedizin.dermadoc.entities.User;
import org.springframework.http.HttpStatus;

/**
 * Created by daniel on 27.11.2015.
 */
public class InvalidUserTypeException extends RestException {
    public InvalidUserTypeException(Class<? extends User> invalidClass) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "invalid user type: "+invalidClass.getName());
    }
}
