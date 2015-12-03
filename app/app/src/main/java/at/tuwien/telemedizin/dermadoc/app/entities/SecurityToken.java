package at.tuwien.telemedizin.dermadoc.app.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

/**
 * Created by daniel on 26.11.2015.
 */
public class SecurityToken extends PreAuthenticatedAuthenticationToken {

    public SecurityToken(String token) {
        this(token, null, null, null);
    }

    public SecurityToken(String token, Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
        setToken(token);
    }

    public void setToken(String token) {
        setDetails(token);
    }

    public String getToken() {
        return (String)getDetails();
    }
}
