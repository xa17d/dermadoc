package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.UserList;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.UserRepository;
import at.tuwien.telemedizin.dermadoc.server.security.AccessUser;
import at.tuwien.telemedizin.dermadoc.server.security.CurrentUser;
import at.tuwien.telemedizin.dermadoc.server.services.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daniel on 01.12.2015.
 */
@RestController
public class PhysiciansController {

    @Autowired
    PhysicianService physicianService;

    @RequestMapping(value = "/physicians", method = RequestMethod.GET)
    @AccessUser
    public UserList listPhysicians(@CurrentUser User user) {
        return new UserList(physicianService.listNearestPhysicians(user.getLocation()));
    }
}
