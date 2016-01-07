package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;

/**
 * Physician
 */
public class PhysicianParc extends UserParc {

    public PhysicianParc() {

    }

    public PhysicianParc (Physician physician) {
        super(physician);


    }


    @Override
    public String toString() {
        return  super.toString() +
                "PhysicianParc{}";
    }

    // parcelable ################################

    public  PhysicianParc(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<PhysicianParc> CREATOR = new Parcelable.Creator<PhysicianParc>() {
        public PhysicianParc createFromParcel(Parcel in) {
            return new PhysicianParc(in);
        }

        public PhysicianParc[] newArray(int size) {
            return new PhysicianParc[size];
        }
    };

}
