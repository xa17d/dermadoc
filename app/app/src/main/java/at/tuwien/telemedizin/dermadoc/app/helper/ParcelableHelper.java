package at.tuwien.telemedizin.dermadoc.app.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.GeoLocationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.Icd10DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.MedicationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.NotificationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AdviceParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionBoolParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionTextParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.PhotoMessageParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.TextMessageParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Medication;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Notification;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Patient;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Advice;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Anamnesis;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestion;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestionBool;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestionText;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Diagnosis;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.PhotoMessage;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.TextMessage;

/**
 * Created by FAUser on 20.11.2015.
 */
public class ParcelableHelper {
    public static final int AUTHOR_FLAG_NULL = -1;
    public static final int AUTHOR_FLAG_PATIENT = 0;
    public static final int AUTHOR_FLAG_PHYSICIAN = 1;

    public static Calendar longToCalendar(long timeInMillis) {
        if (timeInMillis != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timeInMillis);
            return cal;
        } else {
            return null;
        }
    }

    public static List<UserParc> mapUserListToParc(List<User> userList) {

        if (userList == null) {
            return null;
        }

        List<UserParc> userParcList = new ArrayList<>();
        for (User u : userList) {
            userParcList.add(mapUserToUserParc(u));
        }
        return userParcList;
    }

    public static List<PhysicianParc> mapPhysicianListToParc(List<Physician> userList) {

        if (userList == null) {
            return null;
        }

        List<PhysicianParc> userParcList = new ArrayList<>();
        for (Physician u : userList) {
            userParcList.add(new PhysicianParc((u)));
        }
        return userParcList;
    }

    public static List<Physician> mapPhysicianParcList(List<PhysicianParc> userParcList) {

        if (userParcList == null) {
            return null;
        }

        List<Physician> userList = new ArrayList<>();
        for (PhysicianParc u : userParcList) {
            userList.add(mapToPhysician(u));
        }
        return userList;
    }

    public static UserParc mapUserToUserParc(User user) {
        // as user is abstract, its type has to be considered
        if (user instanceof Patient) {
            return new PatientParc((Patient) user);
        } else {
            return new PhysicianParc((Physician) user);
        }
    }


    public static List<MedicationParc> mapMedicationToParc(List<Medication> medications) {

        if (medications == null) {
            return null;
        }

        List<MedicationParc> medParcList = new ArrayList<>();
        for (Medication m : medications) {
            medParcList.add(new MedicationParc(m));
        }
        return medParcList;
    }

    public static List<NotificationParc> mapToNotificationParcList(List<Notification> notifications) {

        if (notifications == null) {
            return null;
        }

        List<NotificationParc> medParcList = new ArrayList<>();
        for (Notification m : notifications) {
            medParcList.add(new NotificationParc(m));
        }
        return medParcList;
    }

    public static List<Notification> mapToNotificationList(List<NotificationParc> notificationsParc) {

        if (notificationsParc == null) {
            return null;
        }

        List<Notification> medParcList = new ArrayList<>();
        for (NotificationParc m : notificationsParc) {
            medParcList.add(mapNotificationParc(m));
        }
        return medParcList;
    }

    public static Notification mapNotificationParc(NotificationParc notificationParc) {

        if (notificationParc == null) {
            return null;
        }
        Notification nf = new Notification();
        nf.setId(notificationParc.getId());
        nf.setCaseId(notificationParc.getCaseId());
        nf.setText(notificationParc.getText());
        nf.setUserId(notificationParc.getUserId());

        return nf;
    }

    public static List<AnamnesisQuestionParc> mapAnamnesisQuestionsToParc(List<AnamnesisQuestion> anamnesisQuestions) {

        if (anamnesisQuestions == null) {
            return null;
        }

        List<AnamnesisQuestionParc> questionsParcList = new ArrayList<>();
        for (AnamnesisQuestion q : anamnesisQuestions) {
            if (q instanceof AnamnesisQuestionBool) {
                questionsParcList.add(new AnamnesisQuestionBoolParc((AnamnesisQuestionBool) q));
            } else {
                questionsParcList.add(new AnamnesisQuestionTextParc((AnamnesisQuestionText) q));
            }

        }
        return questionsParcList;
    }

    public static List<Icd10DiagnosisParc> mapIcd10DiagnosesToParc(List<Icd10Diagnosis> diagnosisList) {

        if (diagnosisList == null) {
            return null;
        }

        List<Icd10DiagnosisParc> diagParcList = new ArrayList<>();
        for (Icd10Diagnosis d : diagnosisList) {
            diagParcList.add(new Icd10DiagnosisParc(d));
        }
        return diagParcList;
    }

//    public static List<BodyLocalization> mapLocalizationToList(BodyLocalization bodyLocalization) {
//        List<BodyLocalization> localizations = new ArrayList<BodyLocalization>();
//        localizations.add(bodyLocalization);
//        return localizations;
//
//    }

    public static List<CaseParc> mapCaseListToParc(List<Case> caseList) {

        if (caseList == null) {
            return null;
        }

        List<CaseParc> caseParcList = new ArrayList<>();
        for (Case c : caseList) {
            caseParcList.add(new CaseParc(c));
        }
        return caseParcList;
    }

    public static List<CaseDataParc> mapCaseDataListToParc(List<CaseData> caseList) {

        if (caseList == null) {
            return null;
        }

        List<CaseDataParc> caseParcList = new ArrayList<>();
        for (CaseData c : caseList) {

            CaseDataParc cP = null;
            if (c instanceof TextMessage) {
                cP = new TextMessageParc((TextMessage) c);
            } else if (c instanceof PhotoMessage) {
                cP = new PhotoMessageParc((PhotoMessage) c);
            } else if (c instanceof Diagnosis) {
                cP = new DiagnosisParc((Diagnosis) c);
            } else if (c instanceof Advice) {
                cP = new AdviceParc((Advice) c);
            } else if (c instanceof Anamnesis) {
                cP = new AnamnesisParc((Anamnesis) c);
            } else if (c instanceof CaseInfo) {
                cP = new CaseInfoParc((CaseInfo) c);
            }

            caseParcList.add(cP);
        }
        return caseParcList;
    }

    public static Case mapToCase(CaseParc caseParc) {
        if (caseParc == null) {
            return null;
        }
        Case caseItem = new Case(caseParc.getId(), mapToPatient(caseParc.getPatient()), caseParc.getCreated());
        caseItem.setPhysician(mapToPhysician(caseParc.getPhysician()));
        caseItem.setStatus(caseParc.getStatus());
        caseItem.setName(caseParc.getName());
        return caseItem;
    }

    public static List<CaseData> mapToCaseDataList(List<CaseDataParc> caseDataParcList) {
        List<CaseData> export = new ArrayList<CaseData>();

        if (caseDataParcList == null) {
            return export;
        }

        for (CaseDataParc cdP : caseDataParcList) {
            CaseData cd = mapToCaseData(cdP);
            export.add(cd);
        }

        return export;
    }

    public static CaseData mapToCaseData(CaseDataParc caseDataParc) {
        if (caseDataParc == null) {
            return null;
        }

        CaseData export;

        if (caseDataParc instanceof TextMessageParc) {
            TextMessageParc cdP = (TextMessageParc) caseDataParc;
            export = new TextMessage(cdP.getId(), cdP.getCreated(), mapToUser(cdP.getAuthor()), cdP.getMessage());
        } else if (caseDataParc instanceof PhotoMessageParc) {
            PhotoMessageParc cdP = (PhotoMessageParc) caseDataParc;
            export = new PhotoMessage(cdP.getId(), cdP.getCreated(), mapToUser(cdP.getAuthor()), cdP.getMime(), cdP.getPhotoData());
        } else if (caseDataParc instanceof DiagnosisParc) {
            DiagnosisParc cdP = (DiagnosisParc) caseDataParc;
            export = new Diagnosis(cdP.getId(), cdP.getCreated(), mapToUser(cdP.getAuthor()), cdP.getMessage(), mapToIcd10DiagnosisList(cdP.getDiagnosisList()));
        } else if (caseDataParc instanceof AdviceParc) {
            AdviceParc cdP = (AdviceParc) caseDataParc;
            export = new Advice(cdP.getId(), cdP.getCreated(), mapToUser(cdP.getAuthor()), cdP.getMessage(), mapToMedicationList(cdP.getMedications()));
        } else if (caseDataParc instanceof CaseInfoParc) {
            CaseInfoParc cdP = (CaseInfoParc) caseDataParc;
            export = new CaseInfo(cdP.getId(), cdP.getCreated(), mapToUser(cdP.getAuthor()), cdP.getLocalizations(), cdP.getPain(), cdP.getSize());
        } else if (caseDataParc instanceof AnamnesisParc) {
            AnamnesisParc cdP = (AnamnesisParc) caseDataParc;
            export = new Anamnesis(cdP.getId(), cdP.getCreated(), mapToUser(cdP.getAuthor()), cdP.getMessage(), mapToQuestionList(cdP.getQuestions()));
        } else {
            return null;
        }

        if (export != null) {
            export.setObsolete(caseDataParc.isObsolete());
            export.setPrivate(caseDataParc.getPrivate());
        }

        return export;
    }

    public static List<AnamnesisQuestion> mapToQuestionList(List<AnamnesisQuestionParc> dList) {
        List<AnamnesisQuestion> export = new ArrayList<>();

        if (dList == null) {
            return export;
        }

        for (AnamnesisQuestionParc icd : dList) {
            export.add(mapToQuestion(icd));
        }
        return export;
    }

    public static AnamnesisQuestion mapToQuestion(AnamnesisQuestionParc questionParc) {
        if (questionParc == null) {
            return null;
        }

        if (questionParc instanceof AnamnesisQuestionBoolParc) {
            return mapToQuestionBool((AnamnesisQuestionBoolParc) questionParc);
        } else if (questionParc instanceof AnamnesisQuestionTextParc) {
            return mapToQuestionBool((AnamnesisQuestionTextParc) questionParc);
        } else {
            return null;
        }
    }

    public static AnamnesisQuestionBool mapToQuestionBool(AnamnesisQuestionBoolParc question) {
        if (question == null) {
            return null;
        }

        AnamnesisQuestionBool export = new AnamnesisQuestionBool();
        export.setQuestion(question.getQuestion());
        export.setAnswer(question.getAnswer());
        return export;

    }

    public static AnamnesisQuestionText mapToQuestionBool(AnamnesisQuestionTextParc question) {
        if (question == null) {
            return null;
        }

        AnamnesisQuestionText export = new AnamnesisQuestionText();
        export.setQuestion(question.getQuestion());
        export.setAnswer(question.getAnswer());
        return export;

    }

    public static List<Icd10Diagnosis> mapToIcd10DiagnosisList(List<Icd10DiagnosisParc> dList) {
        List<Icd10Diagnosis> export = new ArrayList<>();

        if (dList == null) {
            return export;
        }

        for (Icd10DiagnosisParc icd : dList) {
            export.add(mapToIcd10Diagnosis(icd));
        }
        return export;
    }

    public static Icd10Diagnosis mapToIcd10Diagnosis(Icd10DiagnosisParc diagnosisParc) {
        if (diagnosisParc == null) {
            return null;
        }

        return new Icd10Diagnosis(diagnosisParc.getIcd10Code(), diagnosisParc.getIcd10Name());

    }

    public static List<Medication> mapToMedicationList(List<MedicationParc> dList) {
        List<Medication> export = new ArrayList<>();

        if (dList == null) {
            return export;
        }

        for (MedicationParc icd : dList) {
            export.add(mapToMedication(icd));
        }
        return export;
    }

    public static Medication mapToMedication(MedicationParc medicationParc) {
        if (medicationParc == null) {
            return null;
        }

        return new Medication(medicationParc.getName());

    }

    public static User mapToUser(UserParc userParc) {
        if (userParc == null) {
            return null;
        }

        if (userParc instanceof PatientParc) {
            return mapToPatient((PatientParc) userParc);
        } else if (userParc instanceof PhysicianParc) {
            return mapToPhysician((PhysicianParc) userParc);
        } else {
            return null;
        }
    }

    public static Physician mapToPhysician(PhysicianParc physicianParc) {
        if (physicianParc == null) {
            return null;
        }
        Physician export = new Physician();
        export.setId(physicianParc.getId());
        export.setName(physicianParc.getName());
        export.setMail(physicianParc.getMail());
        export.setPassword(physicianParc.getPassword());
        export.setLocation(ParcelableHelper.mapToGeoLocation(physicianParc.getLocation()));
        return export;
    }

    public static Patient mapToPatient(PatientParc patientParc) {
        if (patientParc == null) {
            return null;
        }
        Patient export = new Patient();
        export.setId(patientParc.getId());
        export.setName(patientParc.getName());
        export.setMail(patientParc.getMail());
        export.setPassword(patientParc.getPassword());
        export.setLocation(ParcelableHelper.mapToGeoLocation(patientParc.getLocation()));

        export.setBirthTime(patientParc.getBirthTime());
        export.setGender(patientParc.getGender());
        export.setSvnr(patientParc.getSvnr());
        return export;
    }


    public static GeoLocation mapToGeoLocation(GeoLocationParc geoLocationParc) {
        if (geoLocationParc == null) {
            return null;
        }
        GeoLocation export = new GeoLocation(geoLocationParc.getName(), geoLocationParc.getLatitude(), geoLocationParc.getLongitude());
        return export;
    }


}
