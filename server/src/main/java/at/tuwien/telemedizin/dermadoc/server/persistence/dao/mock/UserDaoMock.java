package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by daniel on 23.11.2015.
 */
public class UserDaoMock implements UserDao {
    public UserDaoMock() {
        // create initial mocked data

        Physician p1 = new Physician();
        p1.setName("Dr. Acula");
        p1.setPassword("a");
        p1.setMail("a");
        p1.setId(0);
        p1.setLocation(new GeoLocation("Transsilvanien", 45.7340837, 21.1990513));
        users.add(p1);

        Patient p2 = new Patient();
        p2.setName("Rainer Zufall");
        p2.setGender(Gender.Male);
        Calendar birthTime = GregorianCalendar.getInstance();
        birthTime.set(1990,01,01);
        p2.setBirthTime(birthTime);
        p2.setSvnr("1234010190");
        p2.setMail("p");
        p2.setPassword("p");
        p2.setId(1);
        users.add(p2);
    }

    private ArrayList<User> users = new ArrayList<>();


    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        for (User u:users) {
            if (u.getId() == id) {
                return  u;
            }
        }
        throw new EntityNotFoundException("id="+id);
    }

    @Override
    public User getUserByMail(String mail) throws EntityNotFoundException {
        for (User u:users) {
            if (u.getMail().toLowerCase() == mail.toLowerCase()) {
                return  u;
            }
        }
        throw new EntityNotFoundException("mail="+mail);
    }
}
