package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.Icd10DiagnosisParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Diagnosis;

/**
 * Diagnosis by a physician
 */
public class DiagnosisParc extends CaseDataParc {

    public DiagnosisParc(Long id, Calendar created, UserParc author, String message, List<Icd10DiagnosisParc> diagnosisList) {
        super(id, created, author);

        this.message = message;
        this.diagnosisList = diagnosisList;
    }

    private String message;
    public String getMessage() { return message; }

    private List<Icd10DiagnosisParc> diagnosisList;
    public List<Icd10DiagnosisParc> getDiagnosisList() { return diagnosisList; }

    @Override
    public String toString() {
        return super.toString() +
                "DiagnosisParc{" +
                "message='" + message + '\'' +
                "', diagnosisList='" + diagnosisList +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param diagnosis
     */
    public DiagnosisParc(Diagnosis diagnosis) {
        this(diagnosis.getId(),
                diagnosis.getCreated(),
                ParcelableHelper.mapUserToUserParc(diagnosis.getAuthor()),
                diagnosis.getMessage(),
                ParcelableHelper.mapIcd10DiagnosesToParc(diagnosis.getDiagnosisList()));
        setObsolete(diagnosis.isObsolete());
        setPrivate(diagnosis.getPrivate());
    }


// parcelable ################################

    public DiagnosisParc(Parcel in) {

        super(in);

        this.message = in.readString();
        diagnosisList = new ArrayList<>();
        in.readList(diagnosisList, Icd10DiagnosisParc.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(message);
        dest.writeList(diagnosisList);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<DiagnosisParc> CREATOR = new Parcelable.Creator<DiagnosisParc>() {
        public DiagnosisParc createFromParcel(Parcel in) {
            return new DiagnosisParc(in);
        }

        public DiagnosisParc[] newArray(int size) {
            return new DiagnosisParc[size];
        }
    };
}
