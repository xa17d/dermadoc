package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by daniel on 23.11.2015.
 */
@Repository
public class UserDaoMock implements UserDao {
    public UserDaoMock() {
        // create initial mocked data
        users.add(createMockPhysician());
        users.add(createMockPatient());
    }

    private ArrayList<User> users = new ArrayList<>();

    public static User createMockPhysician() {
        Physician p1 = new Physician();
        p1.setName("Dr. Acula");
        p1.setPassword("a");
        p1.setMail("a");
        p1.setId(0);
        p1.setLocation(new GeoLocation("Transsilvanien", 45.7340837, 21.1990513));
        return p1;
    }

    public static User createMockPatient() {
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
        return p2;
    }


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
            if (u.getMail().toLowerCase().equals(mail.toLowerCase())) {
                return  u;
            }
        }
        throw new EntityNotFoundException("mail="+mail);
    }
}
