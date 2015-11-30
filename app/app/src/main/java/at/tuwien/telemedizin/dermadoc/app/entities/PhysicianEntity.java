package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by FAUser on 20.11.2015.
 */
public class PhysicianEntity extends Physician implements Parcelable {

    public PhysicianEntity(Physician physician) {
        super.setId(physician.getId());
        super.setMail(physician.getMail());
        super.setPassword(physician.getPassword());
        super.setName(physician.getName());
        super.setLocation(physician.getLocation());


    }

    public static PhysicianEntity createPhysicianEntity(Parcel in) {
        User user = in.readParcelable(UserEntity.class.getClassLoader());

        Physician p = new Physician();
        p.setId(user.getId());
        p.setMail(user.getMail());
        p.setPassword(user.getPassword());
        p.setName(user.getName());
        p.setLocation(user.getLocation());

        return new PhysicianEntity(p);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(new UserEntity(this), flags);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Physician> CREATOR = new Parcelable.Creator<Physician>() {
        public Physician createFromParcel(Parcel in) {
            return createPhysicianEntity(in);
        }

        public Physician[] newArray(int size) {
            return new Physician[size];
        }
    };
}
