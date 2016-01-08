package at.tuwien.telemedizin.dermadoc.app.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.GeoLocationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.Icd10DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.MedicationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AdviceParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionBoolParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionTextParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.app.general_entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Gender;
import at.tuwien.telemedizin.dermadoc.app.general_entities.PainIntensity;

/**
 * Created by FAUser on 02.12.2015.
 *
 * provides mocked data for testing purpose
 */
public class TestContentProvider implements ContentProvider {
    @Override
    public PatientParc getCurrentUser() {

        PatientParc patient = new PatientParc();
        patient.setId(1l);
        patient.setMail("mail@mail.at");
        patient.setPassword("no");
        patient.setName("Peter Hans Gruber dings Norbert");
        patient.setLocation(new GeoLocationParc("hier", 2.0, 2.0));

        patient.setSvnr("1212");
        patient.setGender(Gender.Female);
        patient.setBirthTime(Calendar.getInstance());
        return patient;
    }

    @Override
    public List<PhysicianParc> getNearbyPhysicians(GeoLocationParc geoLocation) {
                PhysicianParc a = new PhysicianParc(); // TODO remove
        a.setId(0);
        a.setName("a");

        PhysicianParc b = new PhysicianParc();
        b.setId(1);
        b.setName("b");

        PhysicianParc c = new PhysicianParc();
        c.setId(2);
        c.setName("c");

        List<PhysicianParc> list = new ArrayList<PhysicianParc>();
        list.add(a);
        list.add(b);
        list.add(c);

        for(int i = 0; i < 5; i++) {
            PhysicianParc p = new PhysicianParc();
            int aAsChar = (int) 'd';
            p.setName("" + (char) (aAsChar + i));
            list.add(p);
        }
        return list;
    }

    @Override
    public AnamnesisParc getAnamnesisForm() {
        return mockAnamnesis2();
    }

    private AnamnesisParc mockAnamnesis1() {

        AnamnesisQuestionParc q1 = new AnamnesisQuestionBoolParc();
        q1.setQuestion("Has your cat shown similar symptoms?");
        AnamnesisQuestionParc q2 = new AnamnesisQuestionTextParc();
        q2.setQuestion("What's the name of your cat?");
        AnamnesisQuestionParc q3 = new AnamnesisQuestionTextParc();
        q3.setQuestion("Why didn't you name her \"Samtpfote\"?");
        AnamnesisQuestionParc q4 = new AnamnesisQuestionBoolParc();
        q4.setQuestion("I just need another question for this test-list. You can ignore it and do not have to bother answering");
        List<AnamnesisQuestionParc> qList = new ArrayList<>();
        qList.add(q1);
        qList.add(q2);
        qList.add(q3);
        qList.add(q4);

        return new AnamnesisParc(0, Calendar.getInstance(), new PhysicianParc(), "Please fill out this form - be honest!", qList);
    }

    private AnamnesisParc mockAnamnesis2() {

        AnamnesisQuestionParc q1 = new AnamnesisQuestionBoolParc();
        q1.setQuestion("Have you ever encounterd similar symptoms before?");
        AnamnesisQuestionParc q1b = new AnamnesisQuestionTextParc();
        q1b.setQuestion("Have there ever been or are there similar cases within you family?");

        AnamnesisQuestionParc q2 = new AnamnesisQuestionTextParc();
        q2.setQuestion("If you ever had problems concerning your skin, pleas describe them here:");

        AnamnesisQuestionParc q3 = new AnamnesisQuestionBoolParc();
        q3.setQuestion("Are you taking any medication at the moment concerning skin-related problems?");

        AnamnesisQuestionParc q4 = new AnamnesisQuestionBoolParc();
        q4.setQuestion("Do you have any allergies?");

        AnamnesisQuestionParc q5 = new AnamnesisQuestionBoolParc();
        q5.setQuestion("Do you smoke?");

        AnamnesisQuestionParc q6 = new AnamnesisQuestionTextParc();
        q6.setQuestion("Please cite any encounters with the following: Skin diseases, diabetes, cancer therapy, cardiovascular diseases, lovesickness.");

        AnamnesisQuestionParc q7 = new AnamnesisQuestionBoolParc();
        q7.setQuestion("Do you want an excellent mark for \"Telemedizin\"?");

        List<AnamnesisQuestionParc> qList = new ArrayList<>();
        qList.add(q1);
        qList.add(q2);
        qList.add(q3);
        qList.add(q4);
        qList.add(q5);
        qList.add(q6);
        qList.add(q7);

        return new AnamnesisParc(0, Calendar.getInstance(), new PhysicianParc(), "Please fill out this form - be honest!", qList);
    }

    @Override
    public List<CaseParc> getCurrentCasesOfUser() {
        return loadCurrentCaseLists();
    }

    /**
     * loads the mocked case-list(s)
     */
    private List<CaseParc> loadCurrentCaseLists() {

        List<CaseParc> currentCaseList;

        PatientParc patient = new PatientParc();
        patient.setId(1l);
        patient.setMail("mail@mail.at");
        patient.setPassword("no");
        patient.setName("Peter Hans Gruber dings Norbert");
        patient.setLocation(new GeoLocationParc("hier", 2.0, 2.0));

        patient.setSvnr("1212");
        patient.setGender(Gender.Female);
        patient.setBirthTime(Calendar.getInstance());


        long startNumber = 100000;
        long startNumberCD = 1;

        currentCaseList = new ArrayList<CaseParc>();
        CaseParc testCase1 = new CaseParc(startNumber, patient, new GregorianCalendar());
        testCase1.setStatus(CaseStatus.Active);
        testCase1.setName("First Case");


        PhysicianParc physician = new PhysicianParc();
        physician.setId(1l);
        physician.setMail("mail@phy.at");
        physician.setPassword("no");
        physician.setName("Dr. Brause");
        physician.setLocation(new GeoLocationParc("dort", 3.0, 3.0));

        testCase1.setPhysician(physician);

        List<BodyLocalization> localizations = new ArrayList<>();
        localizations.add(BodyLocalization.HAND_LEFT);
        localizations.add(BodyLocalization.FOOT_LEFT);

        Calendar timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -7);

        CaseInfoParc caseInfo = new CaseInfoParc(startNumberCD, timestamp, patient, localizations, PainIntensity.Mild, 2);
        testCase1.addDataElement(caseInfo);

        timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -6);

        List<Icd10DiagnosisParc> d1_icList = new ArrayList<>();
        d1_icList.add(new Icd10DiagnosisParc("101010", "Hühnerauge"));
        d1_icList.add(new Icd10DiagnosisParc("02221", "Nase im Gesicht"));
        DiagnosisParc d1 = new DiagnosisParc(startNumberCD+1, timestamp, physician,"test Diagnose 1 ", d1_icList);
        testCase1.addDataElement(d1);

        timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -5);
        timestamp.add(Calendar.HOUR_OF_DAY, -2);

        // text msg
        TextMessageParc textMsg1 = new TextMessageParc(startNumberCD+2, timestamp, physician,"Ich schicke Ihnen gleich ein paar Ratschläge und Medikamenten liste.");
        testCase1.addDataElement(textMsg1);

        timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -5);
        timestamp.add(Calendar.HOUR_OF_DAY, -1);
        // advice
        List<MedicationParc> mediacations1 = new ArrayList<>();
        MedicationParc med1 = new MedicationParc("Paralala");
        MedicationParc med2 = new MedicationParc("Bilill");
        mediacations1.add(med1);
        mediacations1.add(med2);
        AdviceParc advice1 = new AdviceParc(startNumberCD+3, timestamp, physician,"Bleiben aus der Sonne. Immer nur 60 Hz Strahlung ", mediacations1);

        testCase1.addDataElement(advice1);

        timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -5);

        // text msg
        TextMessageParc textMsg2 = new TextMessageParc(startNumberCD+4, timestamp, physician,"Falls Sie fragen haben, schreiben Sie mir bitte oder rufen Sie in der Ordination an.");
        testCase1.addDataElement(textMsg2);

        timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -3);

        // text msg
        TextMessageParc textMsg3 = new TextMessageParc(startNumberCD+5, timestamp, patient,"Danke!.");
        testCase1.addDataElement(textMsg3);

        // pic

//        PhotoMessageParc photoMsg1 = new PhotoMessageParc(-1, Calendar.getInstance(), patient,"hier ein Bild von meinem dings...", null);
//        testCase1.addDataElement(photoMsg1);

        timestamp = Calendar.getInstance();
        timestamp.add(Calendar.DAY_OF_MONTH, -2);
        timestamp.add(Calendar.HOUR_OF_DAY, -6);


        List<Icd10DiagnosisParc> d2_icList = new ArrayList<>();
        DiagnosisParc d2 = new DiagnosisParc(startNumberCD+6, timestamp, physician,"Das ist, wie als würden Sie eine Tasse Rohrfrei trinken. Natürlich reinigt das einen - aber mit der Zeit wird man hohl. ", d2_icList);
        testCase1.addDataElement(d2);

        currentCaseList.add(testCase1);
        CaseParc testCase2 = new CaseParc(startNumber+1, patient, new GregorianCalendar());
        testCase2.setStatus(CaseStatus.Active);
        testCase2.setName("Second Case");
        currentCaseList.add(testCase2);
        for (int i = 0; i < 5; i++) {
            CaseParc testCaseA = new CaseParc((startNumber+10 + i), patient, new GregorianCalendar());
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
    public long saveNewCase(CaseParc caseItem) {
        return 0;
    }

    @Override
    public boolean saveModifiedCase(CaseParc caseItem) {
        return false;
    }
}
