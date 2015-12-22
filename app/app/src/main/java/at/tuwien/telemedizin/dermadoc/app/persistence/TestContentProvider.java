package at.tuwien.telemedizin.dermadoc.app.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Anamnesis;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestion;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionBool;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionText;

/**
 * Created by FAUser on 02.12.2015.
 */
public class TestContentProvider implements ContentProvider {
    @Override
    public Patient getCurrentUser() {
        return null;
    }

    @Override
    public List<Physician> getNearbyPhysicians(GeoLocation geoLocation) {
        return null;
    }

    @Override
    public Anamnesis getAnamnesisForm() {
        return mockAnamnesis();
    }

    private Anamnesis mockAnamnesis() {

        AnamnesisQuestion q1 = new AnamnesisQuestionBool();
        q1.setQuestion("Has your cat shown similar symptoms?");
        AnamnesisQuestion q2 = new AnamnesisQuestionText();
        q2.setQuestion("What's the name of your cat?");
        AnamnesisQuestion q3 = new AnamnesisQuestionText();
        q3.setQuestion("Why didn't you name her \"Samtpfote\"?");
        AnamnesisQuestion q4 = new AnamnesisQuestionBool();
        q4.setQuestion("I just need another question for this test-list. You can ignore it and do not have to bother answering");
        List<AnamnesisQuestion> qList = new ArrayList<>();
        qList.add(q1);
        qList.add(q2);
        qList.add(q3);
        qList.add(q4);

        return new Anamnesis(0, Calendar.getInstance(), new Physician(), "what message", qList);
    }

    @Override
    public List<Case> getCurrentCasesOfUser() {
        return loadCurrentCaseLists();
    }

    /**
     * loads the case-list(s) from the server
     * TODO
     */
    private List<Case> loadCurrentCaseLists() {

        List<Case> currentCaseList;

        Patient patient = new Patient();
        patient.setId(1l);
        patient.setMail("mail@mail.at");
        patient.setPassword("no");
        patient.setName("Peter Hans Gruber dings Norbert");
        patient.setLocation(new GeoLocation("hier", 2.0, 2.0));

        patient.setSvnr("1212");
        patient.setGender(Gender.Female);
        patient.setBirthTime(Calendar.getInstance());


        long startNumber = 100000;

        currentCaseList = new ArrayList<Case>();
        Case testCase1 = new Case(startNumber+2045, patient, new GregorianCalendar());
        testCase1.setStatus(CaseStatus.Active);
        testCase1.setName("First Case");
        currentCaseList.add(testCase1);
        Case testCase2 = new Case(startNumber+451, patient, new GregorianCalendar());
        testCase2.setStatus(CaseStatus.LookingForPhysician);
        testCase2.setName("Second Case");
        currentCaseList.add(testCase2);
        for (int i = 0; i < 5; i++) {
            Case testCaseA = new Case((startNumber+10 + i), patient, new GregorianCalendar());
            testCaseA.setStatus(CaseStatus.values()[i%3]); // 3 because 4 would be closed
            testCaseA.setName("My " + i + ". Case");
            currentCaseList.add(testCaseA);
        }

//        closedCaseList = new ArrayList<Case>();
//        Case testCase3 = new Case(startNumber+4345, patient, new GregorianCalendar());
//        testCase3.setStatus(CaseStatus.Closed);
//        testCase3.setName("Third Case");
//        closedCaseList.add(testCase3);
//        for (int i = 0; i < 5; i++) {
//            Case testCaseB = new Case((startNumber+7645 + i), patient, new GregorianCalendar());
//            testCaseB.setStatus(CaseStatus.values()[i % 4]);
//            testCaseB.setName("My " + i + ". Case");
//            closedCaseList.add(testCaseB);
//        }

        return currentCaseList;
    }


    @Override
    public long saveNewCase(Case caseItem) {
        return 0;
    }

    @Override
    public boolean saveModifiedCase(Case caseItem) {
        return false;
    }
}
