package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.CaseDataRepository;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.CaseRepository;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by daniel on 11.11.2015.
 */

@RestController
public class TestController {

    @RequestMapping(value = "/")
    public String helloWorld() {
        return "<html><h1>Hello Derma Doc</h1><p>JSON + DB</p></html>";
    }

    @RequestMapping(value = "/testpatient")
    public Patient patient() {

        Patient p = new Patient();

        p.setId(new Long(123));
        p.setName("John Doe");
        p.setBirthTime(Calendar.getInstance());
        p.setGender(Gender.Male);
        p.setSvnr("123401011990");
        p.setLocation(new GeoLocation("Wien", 48.2082, 16.3738));
        p.setMail("john.doe@example.com");
        p.setPassword("*****");

        return p;
    }

}
