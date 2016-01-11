package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Gender;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Patient;

/**
 * Patient
 */
public class PatientParc extends UserParc {
    private String svnr;
    public String getSvnr() { return svnr; }
    public void setSvnr(String svnr) { this.svnr = svnr; }

    private Calendar birthTime;
    public Calendar getBirthTime() { return birthTime; }
    public void setBirthTime(Calendar birthTime) { this.birthTime = birthTime; }

    private Gender gender;
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public PatientParc() {

    }

    public PatientParc (Patient patient) {
        super(patient);

        setSvnr(patient.getSvnr());
        setBirthTime(patient.getBirthTime());
        setGender(patient.getGender());

    }

    @Override
    public String toString() {
        return super.toString() +
                "PatientParc{" +
                "svnr='" + svnr + '\'' +
                ", birthTime=" + FormatHelper.calendarToDateFormatString(birthTime) +
                ", gender=" + gender +
                '}';
    }

    // parcelable ################################

    public  PatientParc(Parcel in) {
        super(in);

        String svnr = in.readString();
        setSvnr(svnr);

        long birthTimeInMillis = in.readLong();
        Calendar birthTime = ParcelableHelper.longToCalendar(birthTimeInMillis);
        setBirthTime(birthTime);

        String genderStr = in.readString();
        if (genderStr != null) {
            setGender(Gender.valueOf(genderStr));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeString(getSvnr());
        long birthTimeInMilli = -1;
        Calendar birthTime = getBirthTime();
        if (birthTime != null) { // avoid nullpointer exception
            birthTimeInMilli = birthTime.getTimeInMillis();
        }
        dest.writeLong(birthTimeInMilli);

        Gender gender = getGender();
        String genderStr = gender != null ? gender.name() : null;
        dest.writeString(genderStr);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<PatientParc> CREATOR = new Parcelable.Creator<PatientParc>() {
        public PatientParc createFromParcel(Parcel in) {
            return new PatientParc(in);
        }

        public PatientParc[] newArray(int size) {
            return new PatientParc[size];
        }
    };
}
