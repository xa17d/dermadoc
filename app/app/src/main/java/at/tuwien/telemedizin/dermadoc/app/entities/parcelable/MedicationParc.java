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

    private Long medicationId;
    public Long getMedicationId() {
        return medicationId;
    }

    public MedicationParc(String name, String dosis) {
        this.name = name;
        this.dosis = dosis;
    }

    private String dosis;
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public String toString() {
        return "MedicationParc{" +
                "name='" + name + '\'' +
                ", medicationId=" + medicationId +
                ", dosis='" + dosis + '\'' +
                '}';
    }

    /**
     * mapping constructor
     * @param medication
     */
    public MedicationParc(Medication medication) {
        this(medication.getName(), medication.getDosis());
        this.medicationId = medication.getMedicationId();
    }

    // parcelable ################################

    public  MedicationParc(Parcel in) {
        medicationId = in.readLong();
        String nameIn = in.readString();
        this.name = nameIn;
        dosis = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(medicationId);
        dest.writeString(getName());
        dest.writeString(dosis);
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
