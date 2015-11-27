package at.tuwien.telemedizin.dermadoc.server.exceptions;

import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by daniel on 27.11.2015.
 */
public class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException(Class<? extends User> invalidClass) {
        super("invalid user type: "+invalidClass.getName());
    }
}
