package at.tuwien.telemedizin.dermadoc.app.helper;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;
import at.tuwien.telemedizin.dermadoc.app.general_entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Patient;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Anamnesis;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestion;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestionBool;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.AnamnesisQuestionText;

/**
 * Created by FAUser on 02.12.2015.
 */
public class ToStringHelper {

    public static String toString(Case caseItem) {
        String s = "";
        s += "Case{" +
                "id=" + caseItem.getId() +
                ", patient=" + toString(caseItem.getPatient()) + "\n" +
                ", physician=" + toString(caseItem.getPhysician()) + "\n" +
                ", created=" + FormatHelper.calendarToDateFormatString(caseItem.getCreated()) + "\n" +
                ", status=" + caseItem.getStatus() + "\n" +
                ", name='" + caseItem.getName() + '\'' + "\n" +
                '}';

        return s;
    }

    public static String toString(Patient patient) {
        if (patient == null) {
            return null;
        }
        String s = "";
        s += "Patient{" +
                "svnr='" + patient.getSvnr() + '\'' + "\n" +
                ", birthTime=" + FormatHelper.calendarToDateFormatString(patient.getBirthTime()) + "\n" +
                ", gender=" + patient.getGender() + "\n";
        ;
        s += "User{" +
                "id=" + patient.getId() +
                ", mail='" + patient.getMail() + '\'' +
                ", password='" + patient.getPassword() + '\'' +
                ", name='" + patient.getName() + '\'' +
                ", location=" + patient.getLocation() +
                '}' + " " +
                '}';
        return s;
    }

    public static String toString(Physician physician) {
        if (physician == null) {
            return null;
        }
        String s = "";
        s += "Physician{";

        ;
        s += "User{" +
                "id=" + physician.getId() +
                ", mail='" + physician.getMail() + '\'' +
                ", password='" + physician.getPassword() + '\'' +
                ", name='" + physician.getName() + '\'' +
                ", location=" + physician.getLocation() +
                '}' + " " +
                '}';
        return s;
    }

    public static String toString(Anamnesis anamnesis) {
        String s = "";
        s += "Anamnesis {";
        if (anamnesis == null) {
            return s + "null}";
        }
        for(AnamnesisQuestion q : anamnesis.getQuestions()) {
            s += "q: " + q.getQuestion() + "\n";
            if (q instanceof AnamnesisQuestionBool) {
                s += ((AnamnesisQuestionBool)q).getAnswer() + ",\n";

            } else {
                s += ((AnamnesisQuestionText)q).getAnswer() + ",\n";

            }
        }

        s += "}";
        return s;
    }


    public static String toStringPics(List<PictureHelperEntity> pictures) {

        String s = "Pictures{";
        if (pictures == null) {
            return s + "null}";
        }
        for (PictureHelperEntity p : pictures) {
            s += "Picture Description: " + p.getDescription() + ",\n" +
                    "pic != null " + (p.getPicture() != null)
                    + ", thumbnail != null " + (p.getThumbnail() != null);
        }

        s += " }";
        return s;
    }

    public static String toStringLoc(List<BodyLocalization> bodyLocalizations) {
        String s = "BodyLocalizations{";

        if (bodyLocalizations == null) {
            return s + "null}";
        }
        for (BodyLocalization p : bodyLocalizations) {
            s += p.name() + ", \n";
        }

        s += " }";
        return s;
    }

}
