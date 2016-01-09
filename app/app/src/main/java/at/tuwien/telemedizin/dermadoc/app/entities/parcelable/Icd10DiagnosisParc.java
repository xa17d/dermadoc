package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Icd10Diagnosis;

/**
 * Created by Lucas on 01.12.2015.
 */
public class Icd10DiagnosisParc implements Parcelable {

    //TODO actually use icd 10

    public Icd10DiagnosisParc() {  }

    public Icd10DiagnosisParc(String icd10Code, String icd10Name) {
        this.icd10Code = icd10Code;
        this.icd10Name = icd10Name;
    }

    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String icd10Code;
    public void setIcd10Code(String icd10Code) { this.icd10Code = icd10Code; }
    public String getIcd10Code() { return icd10Code; }

    private String icd10Name;
    public void setIcd10Name(String icd10Name) { this.icd10Name = icd10Name; }
    public String getIcd10Name() { return icd10Name; }

    @Override
    public String toString() {
        return icd10Code + " - " + icd10Name;
    }

    public Icd10DiagnosisParc(Icd10Diagnosis icd10Diagnosis) {
        this.id = icd10Diagnosis.getId();
        this.icd10Code = icd10Diagnosis.getIcd10Code();
        this.icd10Name = icd10Diagnosis.getIcd10Name();
    }

    // parcelable ################################

    public Icd10DiagnosisParc(Parcel in) {
        this.id = in.readLong();
        this.icd10Code = in.readString();
        this.icd10Name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(icd10Code);
        dest.writeString(icd10Name);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Icd10DiagnosisParc> CREATOR = new Parcelable.Creator<Icd10DiagnosisParc>() {
        public Icd10DiagnosisParc createFromParcel(Parcel in) {
            return new Icd10DiagnosisParc(in);
        }

        public Icd10DiagnosisParc[] newArray(int size) {
            return new Icd10DiagnosisParc[size];
        }
    };
}
