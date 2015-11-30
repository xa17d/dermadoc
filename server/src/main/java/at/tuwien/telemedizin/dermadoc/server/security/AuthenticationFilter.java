package at.tuwien.telemedizin.dermadoc.server.security;

import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.server.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by daniel on 26.11.2015.
 */
public class AuthenticationFilter extends GenericFilterBean {

    public AuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) (request);
        HttpServletResponse httpResponse = (HttpServletResponse) (response);

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (!StringUtils.isEmpty(authorizationHeader)) {

            String[] parts = authorizationHeader.split(" ", 2);

            if (parts.length == 2) {
                String tokenType = parts[0];
                if (SecurityConfig.TokenType.equals(tokenType)) {
                    String token = parts[1];
                    try {
                        tryToAuthenticate(new SecurityToken(token));
                    }
                    catch (BadCredentialsException badCredentialsException) {
                        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        XmlMapper mapper = new XmlMapper();
                        mapper.writeValue(httpResponse.getOutputStream(), new Error(badCredentialsException));
                        return;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }
}
