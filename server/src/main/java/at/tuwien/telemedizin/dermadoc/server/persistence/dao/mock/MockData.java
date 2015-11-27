package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by daniel on 27.11.2015.
 */
public class MockData {

    // Create Mocked data
    private static void createMockData() {
        users = new ArrayList<>();
        cases = new ArrayList<>();

        Physician drAcula = new Physician();
        drAcula.setName("Dr. Acula");
        drAcula.setPassword("a");
        drAcula.setMail("a");
        drAcula.setId(0);
        drAcula.setLocation(new GeoLocation("Transsilvanien", 45.7340837, 21.1990513));
        users.add(drAcula);

        Patient johnD = new Patient();
        johnD.setName("John Doe");
        johnD.setGender(Gender.Male);
        Calendar birthTime = GregorianCalendar.getInstance();
        birthTime.set(1990,01,01);
        johnD.setBirthTime(birthTime);
        johnD.setSvnr("1234010190");
        johnD.setMail("p");
        johnD.setPassword("p");
        johnD.setId(1);
        users.add(johnD);

        Case caseJohnRash = new Case(100, johnD, GregorianCalendar.getInstance());
        caseJohnRash.setName("Rash on left arm");
        caseJohnRash.setPhysician(drAcula);
        caseJohnRash.setStatus(CaseStatus.Active);
        cases.add(caseJohnRash);

        Case caseJohnBlister = new Case(101, johnD, GregorianCalendar.getInstance());
        caseJohnBlister.setName("Bilster on foot");
        caseJohnBlister.setPhysician(null);
        caseJohnBlister.setStatus(CaseStatus.LookingForPhysician);
        cases.add(caseJohnBlister);
    }

    public static ArrayList<User> users;
    public static ArrayList<Case> cases;

    static {
        createMockData();
    }

}
