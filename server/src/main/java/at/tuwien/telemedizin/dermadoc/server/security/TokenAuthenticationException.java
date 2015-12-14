package at.tuwien.telemedizin.dermadoc.server.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by daniel on 26.11.2015.
 */
public class TokenAuthenticationException extends AuthenticationException {
    public TokenAuthenticationException() {
        super("Token is invalid");
    }
}
