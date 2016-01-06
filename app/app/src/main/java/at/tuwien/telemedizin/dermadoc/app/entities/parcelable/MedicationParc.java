package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Medication;

/**
 * Medication
 */
public class MedicationParc implements Parcelable {
    public MedicationParc(String name) {
        this.name = name;
    }

    private String name;
    public String getName() { return name; }

    @Override
    public String toString() {
        return "MedicationParc{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * mapping constructor
     * @param medication
     */
    public MedicationParc(Medication medication) {
        this(medication.getName());
    }

    // parcelable ################################

    public  MedicationParc(Parcel in) {

        String nameIn = in.readString();
        this.name = nameIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getName());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<MedicationParc> CREATOR = new Parcelable.Creator<MedicationParc>() {
        public MedicationParc createFromParcel(Parcel in) {
            return new MedicationParc(in);
        }

        public MedicationParc[] newArray(int size) {
            return new MedicationParc[size];
        }
    };
}
