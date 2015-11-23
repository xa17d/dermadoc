package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * Created by daniel on 11.11.2015.
 */
@RestController
public class TestController {
    @RequestMapping(value = "/")
    public String helloWorld() {
        return "Hello Derma Doc";
    }

    @RequestMapping(value = "/testpatient")
    public Patient patient() {

        Patient p = new Patient();

        p.setId(123);
        p.setName("John Doe");
        p.setBirthTime(Calendar.getInstance());
        p.setGender(Gender.Male);
        p.setSvnr("123401011990");
        p.setLocation(new GeoLocation("Wien", 48.2082, 16.3738));
        p.setMail("john.doe@example.com");
        p.setPassword("*****");

        return p;
    }

    @RequestMapping(value = "/testdb")
    public User testDb() throws EntityNotFoundException {

        return userDao.getUserById(0);

    }

    @Autowired
    private UserDao userDao;
}
