package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.server.exceptions.AuthenticationInvalidException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.apache.tomcat.util.net.jsse.openssl.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daniel on 23.11.2015.
 */
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationToken login(@RequestBody AuthenticationData authenticationData) throws AuthenticationInvalidException {
        User u;

        try {
            u = userDao.getUserByMail(authenticationData.getMail());
        }
        catch (EntityNotFoundException e) {
            // User not found
            throw new AuthenticationInvalidException();
        }

        if (u.getPassword() == authenticationData.getPassword()) {
            return new AuthenticationToken("uid", Long.toString(u.getId())); // TODO: create session and token
        } else {
            // Password invalid
            throw new AuthenticationInvalidException();
        }
    }
}
