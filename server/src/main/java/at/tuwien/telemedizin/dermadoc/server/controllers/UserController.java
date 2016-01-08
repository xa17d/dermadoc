package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.server.exceptions.AuthenticationInvalidException;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.exceptions.InvalidSubtypeTypeException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.UserRepository;
import at.tuwien.telemedizin.dermadoc.server.security.AccessUser;
import at.tuwien.telemedizin.dermadoc.server.security.CurrentUser;
import at.tuwien.telemedizin.dermadoc.server.security.SecurityConfig;
import at.tuwien.telemedizin.dermadoc.server.security.SecurityToken;
import at.tuwien.telemedizin.dermadoc.server.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by daniel on 23.11.2015.
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationToken login(@RequestBody AuthenticationData authenticationData) throws AuthenticationInvalidException {
        User user;

        try {
            user = userRepository.getUserByMail(authenticationData.getMail());
        }
        catch (EntityNotFoundException e) {
            // User not found
            throw new AuthenticationInvalidException();
        }

        if (user.getPassword().equals(authenticationData.getPassword())) {
            // Login successful
            SecurityToken token = tokenService.generateNewToken(user);
            tokenService.store(token);

            return new AuthenticationToken(SecurityConfig.TokenType, token.getToken()); // TODO: create session and token
        } else {
            // Password invalid
            throw new AuthenticationInvalidException();
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @AccessUser
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof SecurityToken) {
            SecurityToken token = (SecurityToken)authentication;
            tokenService.evict(token.getToken());
        }
        else {
            throw new InvalidSubtypeTypeException(Authentication.class, authentication.getClass());
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @AccessUser
    public User user(@CurrentUser User user) {
        return user;
    }
}
