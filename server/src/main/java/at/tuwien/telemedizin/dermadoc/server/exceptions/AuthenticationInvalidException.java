package at.tuwien.telemedizin.dermadoc.server.exceptions;

/**
 * Created by daniel on 23.11.2015.
 */
public class AuthenticationInvalidException extends Exception {
    public AuthenticationInvalidException() {
        super("Username or Password is invalid");
    }
}
