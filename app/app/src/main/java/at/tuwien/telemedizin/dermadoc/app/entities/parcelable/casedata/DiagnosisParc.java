package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Diagnosis;

/**
 * Diagnosis by a physician
 */
public class DiagnosisParc extends CaseDataParc {

    public DiagnosisParc(long id, Calendar created, UserParc author, String message) {
        super(id, created, author);

        this.message = message;
    }

    private String message;
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return super.toString() +
                "DiagnosisParc{" +
                "message='" + message + '\'' +
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
                diagnosis.getMessage());
    }


// parcelable ################################

    public DiagnosisParc(Parcel in) {

        super(in);

        this.message = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(message);
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
