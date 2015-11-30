package at.tuwien.telemedizin.dermadoc.server.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by daniel on 23.11.2015.
 */
public class AuthenticationInvalidException extends RestException {
    public AuthenticationInvalidException() {
        super(HttpStatus.UNAUTHORIZED, "Username or Password is invalid");
    }
}
