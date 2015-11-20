package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.User;

/**
 * Created by FAUser on 19.11.2015.
 */
public class PatientEntity extends Patient implements Parcelable {

    public PatientEntity(Patient patient) {
        super.setId(patient.getId());
        super.setMail(patient.getMail());
        super.setPassword(patient.getPassword());
        super.setName(patient.getName());
        super.setLocation(patient.getLocation());

        super.setSvnr(patient.getSvnr());
        super.setBirthTime(patient.getBirthTime());
        super.setGender(patient.getGender());

    }

    public static PatientEntity createPatientEntity(Parcel in) {
        User user = in.readParcelable(UserEntity.class.getClassLoader());

        Patient p = new Patient();
        p.setId(user.getId());
        p.setMail(user.getMail());
        p.setPassword(user.getPassword());
        p.setName(user.getName());
        p.setLocation(user.getLocation());

        String svnr = in.readString();
        p.setSvnr(svnr);

        long birthTimeInMillis = in.readLong();
        Calendar birthTime = ParcelableHelper.longToCalendar(birthTimeInMillis);
        p.setBirthTime(birthTime);

        String genderStr = in.readString();
        if (genderStr != null) {
            p.setGender(Gender.valueOf(genderStr));
        }
        return new PatientEntity(p);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(new UserEntity(this), flags);
        dest.writeString(super.getSvnr());
        long birthTimeInMilli = -1;
        Calendar birthTime = super.getBirthTime();
        if (birthTime != null) { // avoid nullpointer erxception
            birthTimeInMilli = birthTime.getTimeInMillis();
        }
        dest.writeLong(birthTimeInMilli);

        Gender gender = super.getGender();
        String genderStr = gender != null ? gender.name() : null;
        dest.writeString(genderStr);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
        public Patient createFromParcel(Parcel in) {
            return createPatientEntity(in);
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}

