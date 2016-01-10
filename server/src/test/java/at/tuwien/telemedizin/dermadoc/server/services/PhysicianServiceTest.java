package at.tuwien.telemedizin.dermadoc.server.services;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.entities.casedata.PhotoMessage;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import at.tuwien.telemedizin.dermadoc.entities.rest.UserList;
import at.tuwien.telemedizin.dermadoc.server.Application;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.CaseDataRepository;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.CaseRepository;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.MedicationRepository;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.UserRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by daniel on 10.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PhysicianServiceTest {
    @Autowired
    private PhysicianService physicianService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void listNearestPhysiciansTest() throws Exception {

        Physician pBregenz = new Physician();
        pBregenz.setName("Bregenz");
        pBregenz.setMail("Bregenz");
        pBregenz.setPassword("Bregenz");
        pBregenz.setLocation(new GeoLocation("Bregenz", 47.5071327, 9.7169709));
        userRepository.save(pBregenz);

        Physician pInnsbruck = new Physician();
        pInnsbruck.setName("Innsbruck");
        pInnsbruck.setMail("Innsbruck");
        pInnsbruck.setPassword("Innsbruck");
        pInnsbruck.setLocation(new GeoLocation("Innsbruck", 47.2855502, 11.3087505));
        userRepository.save(pInnsbruck);

        Physician pSalzburg = new Physician();
        pSalzburg.setName("Salzburg");
        pSalzburg.setMail("Salzburg");
        pSalzburg.setPassword("Salzburg");
        pSalzburg.setLocation(new GeoLocation("Salzburg", 47.8028623, 13.0214107));
        userRepository.save(pSalzburg);

        Physician pWien = new Physician();
        pWien.setName("Wien");
        pWien.setMail("Wien");
        pWien.setPassword("Wien");
        pWien.setLocation(new GeoLocation("Wien", 48.220778, 16.3100205));
        userRepository.save(pWien);

        List<Physician> list;

        list = physicianService.listNearestPhysicians(new GeoLocation("Innsbruck", 47.2855502, 11.3087505));
        Assert.assertEquals("Item 0", "Innsbruck", list.get(0).getName());
        Assert.assertEquals("Item 1", "Bregenz", list.get(1).getName());
        Assert.assertEquals("Item 2", "Salzburg", list.get(2).getName());
        Assert.assertEquals("Item 3", "Wien", list.get(3).getName());

        list = physicianService.listNearestPhysicians(new GeoLocation("Wien", 48.220778, 16.3100205));
        Assert.assertEquals("Item 0", "Wien", list.get(0).getName());
        Assert.assertEquals("Item 1", "Salzburg", list.get(1).getName());
        Assert.assertEquals("Item 2", "Innsbruck", list.get(2).getName());
        //Assert.assertEquals("Item 3", "Bregenz", list.get(3).getName());

        list = physicianService.listNearestPhysicians(null);
        Assert.assertTrue(list.size() > 0);
    }
}