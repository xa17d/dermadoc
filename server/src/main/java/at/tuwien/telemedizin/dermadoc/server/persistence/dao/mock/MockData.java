package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.PhotoMessage;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by daniel on 27.11.2015.
 */
public class MockData {

    // Create Mocked data
    private static void createMockData() {
        users = new ArrayList<>();
        cases = new ArrayList<>();
        caseData = new HashMap<>();

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

        ArrayList<CaseData> caseJohnRashData = new ArrayList<>();
        caseData.put(caseJohnRash.getId(), caseJohnRashData);
        caseJohnRashData.add(new TextMessage(1000, GregorianCalendar.getInstance(), johnD, "hey doc, my left arm hurts like hell!"));
        caseJohnRashData.add(new TextMessage(1001, GregorianCalendar.getInstance(), johnD, "it feels like it goes up in flames any moment!"));
        caseJohnRashData.add(new PhotoMessage(1002, GregorianCalendar.getInstance(), drAcula, "image/jpeg", new byte[] {1,2,3,4,65,66}));
    }

    public static ArrayList<User> users;
    public static ArrayList<Case> cases;
    public static HashMap<Long, ArrayList<CaseData>> caseData;

    static {
        createMockData();
    }

}
