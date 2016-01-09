package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.MedicationParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.Advice;

/**
 * Advice from a Physician
 */
public class AdviceParc extends CaseDataParc {

    public AdviceParc(Long id, Calendar created, UserParc author, String message, List<MedicationParc> medications) {
        super(id, created, author);

        this.message = message;
        this.medications = medications;
    }

    private String message;
    public String getMessage() { return message; }

    private List<MedicationParc> medications;
    public List<MedicationParc> getMedications() { return medications; }

    @Override
    public String toString() {
        return super.toString() +
                "AdviceParc{" +
                "message='" + message + '\'' +
                ", medications=" + medications +
                '}';
    }

    /**
     * mapping constructor
     *
     * @param advice
     */
    public AdviceParc(Advice advice) {
        this(advice.getId(),
                advice.getCreated(),
                ParcelableHelper.mapUserToUserParc(advice.getAuthor()),
                advice.getMessage(),
                ParcelableHelper.mapMedicationToParc(advice.getMedications()));
    }


// parcelable ################################

    public AdviceParc(Parcel in) {

        super(in);

        this.message = in.readString();
        this.medications = new ArrayList<>();
        in.readList(this.medications, MedicationParc.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        super.writeToParcel(dest, flags);
        dest.writeString(message);

        dest.writeList(medications);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<AdviceParc> CREATOR = new Parcelable.Creator<AdviceParc>() {
        public AdviceParc createFromParcel(Parcel in) {
            return new AdviceParc(in);
        }

        public AdviceParc[] newArray(int size) {
            return new AdviceParc[size];
        }
    };
}
