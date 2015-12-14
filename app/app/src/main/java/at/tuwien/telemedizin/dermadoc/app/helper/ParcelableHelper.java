package at.tuwien.telemedizin.dermadoc.app.helper;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.MedicationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PatientParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionBoolParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AnamnesisQuestionTextParc;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestion;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionBool;
import at.tuwien.telemedizin.dermadoc.entities.casedata.AnamnesisQuestionText;

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

    public static UserParc mapUserToUserParc(User user) {
        // as user is abstract, its type has to be considered
        if (user instanceof Patient) {
            return new PatientParc((Patient)user);
        } else {
           return new PhysicianParc((Physician)user);
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

    public static List<AnamnesisQuestionParc> mapAnamnesisQuestionsToParc(List<AnamnesisQuestion> anamnesisQuestions) {

        if (anamnesisQuestions == null) {
            return null;
        }

        List<AnamnesisQuestionParc> questionsParcList = new ArrayList<>();
        for (AnamnesisQuestion q : anamnesisQuestions) {
            if (q instanceof AnamnesisQuestionBool) {
                questionsParcList.add(new AnamnesisQuestionBoolParc((AnamnesisQuestionBool)q));
            } else{
                questionsParcList.add(new AnamnesisQuestionTextParc((AnamnesisQuestionText)q));
            }

        }
        return questionsParcList;
    }
}
