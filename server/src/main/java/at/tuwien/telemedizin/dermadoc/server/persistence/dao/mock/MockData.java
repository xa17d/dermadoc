package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;

import java.util.*;

/**
 * Created by daniel on 27.11.2015.
 */

public class MockData {

    // Create Mocked data
    private static void createMockData() {
        users = new ArrayList<>();
        cases = new ArrayList<>();
        caseData = new HashMap<>();
        notifications = new ArrayList<>();

        Physician drAcula = new Physician();
        drAcula.setName("Dr. Acula");
        drAcula.setPassword("a");
        drAcula.setMail("a");
        drAcula.setId(new Long(0));
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
        johnD.setId(new Long(1));
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

        List<AnamnesisQuestion> questions = new ArrayList<>();
        AnamnesisQuestionText q1 = new AnamnesisQuestionText();
        q1.setQuestion("question 1");
        q1.setAnswer("text answer 1");
        AnamnesisQuestionText q2 = new AnamnesisQuestionText();
        q2.setQuestion("question 2");
        q2.setAnswer("text answer 2");
        AnamnesisQuestionText q3 = new AnamnesisQuestionText();
        q3.setQuestion("question 3");
        q3.setAnswer("text answer 3");
        AnamnesisQuestionText q4 = new AnamnesisQuestionText();
        q4.setQuestion("question 4");
        q4.setAnswer("text answer 4");
        AnamnesisQuestionBool q5 = new AnamnesisQuestionBool();
        q5.setQuestion("question 5");
        q5.setAnswer(true);
        AnamnesisQuestionBool q6 = new AnamnesisQuestionBool();
        q6.setQuestion("question 6");
        q6.setAnswer(false);
        AnamnesisQuestionBool q7 = new AnamnesisQuestionBool();
        q7.setQuestion("question 7");
        q7.setAnswer(true);

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);

        //caseJohnRashData.add(new Anamnesis(999, Calendar.getInstance(), johnD, "this is my anamnesis", questions));
        caseJohnRashData.add(new TextMessage(1000, GregorianCalendar.getInstance(), johnD, "hey doc, my left arm hurts like hell!"));
        caseJohnRashData.add(new TextMessage(1001, Calendar.getInstance(), drAcula, "does it hurt a lot?"));
        caseJohnRashData.add(new TextMessage(1002, GregorianCalendar.getInstance(), johnD, "it feels like it goes up in flames any moment!"));
        caseJohnRashData.add(new Diagnosis(1003, GregorianCalendar.getInstance(), drAcula, "looks like it's a common eczema!", Arrays.asList(new Icd10Diagnosis[] { new Icd10Diagnosis("I12.0", "common Eczema"), new Icd10Diagnosis("I12.2", "inflamed Eczema") })));
        caseJohnRashData.add(new Advice(1004, GregorianCalendar.getInstance(), drAcula, "take some pills and it will get better...", Arrays.asList(new Medication[] { new Medication("a big red pill"), new Medication("a small blue pill"), new Medication("a verry expensive pill") })));
        caseJohnRashData.add(new TextMessage(1005, GregorianCalendar.getInstance(), johnD, "thank you :)"));
        //caseJohnRashData.add(new PhotoMessage(1005, GregorianCalendar.getInstance(), drAcula, "image/jpeg", new byte[] {1,2,3,4,65,66}));
    }

    public static ArrayList<User> users;
    public static ArrayList<Case> cases;
    public static HashMap<Long, ArrayList<CaseData>> caseData;
    public static ArrayList<Notification> notifications;

    static {
        createMockData();
    }

}
