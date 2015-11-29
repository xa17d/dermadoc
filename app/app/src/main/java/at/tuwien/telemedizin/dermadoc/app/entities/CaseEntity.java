package at.tuwien.telemedizin.dermadoc.app.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;

/**
 * Created by FAUser on 19.11.2015.
 *
 */
public class CaseEntity extends Case implements Parcelable {

    // key for the intent
    public static final String INTENT_KEY = CaseEntity.class.getName() + "_INTENT_KEY";

    public CaseEntity(long id, Patient patient, Calendar created) {
        super(id, patient, created);
    }

    public CaseEntity(Case caseObject) {
        super(caseObject.getId(), caseObject.getPatient(), caseObject.getCreated());
        super.setPhysician(caseObject.getPhysician());
        super.setStatus(caseObject.getStatus());
        super.setName(caseObject.getName());
        // TODO
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static CaseEntity createCaseEntity(Parcel in) {
        long id = in.readLong();
        String name = in.readString();
        long createdTimeInMillis = in.readLong();
        Calendar created = ParcelableHelper.longToCalendar(createdTimeInMillis);


        String statusStr = in.readString();

        Patient pPatient = in.readParcelable(PatientEntity.class.getClassLoader());

//        Log.d("CaseEntity", "Patient: " + pPatient.getId() + ", " + pPatient.getMail() + ", "
//                + pPatient.getPassword() + ", " + pPatient.getName() + ", " + ", "
//                + (pPatient.getLocation() != null ? pPatient.getLocation().getName() : "no location")
//                + pPatient.getSvnr() + ", " + FormatHelper.calendarToDateFormatString(pPatient.getBirthTime()) + ", "
//                + pPatient.getGender());

        Physician pPhysician = in.readParcelable(PhysicianEntity.class.getClassLoader());

        CaseEntity caseEntity = new CaseEntity(id, pPatient, created);
        if (statusStr != null) {
            caseEntity.setStatus(CaseStatus.valueOf(statusStr));
        }
        caseEntity.setName(name);
        caseEntity.setPhysician(pPhysician);
        return caseEntity;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(super.getId());
        dest.writeString(super.getName());
        long createdTimeInMillis = -1;
        Calendar createdTime = super.getCreated();
        if (createdTime != null) { // avoid nullpointer erxception
            createdTimeInMillis = createdTime.getTimeInMillis();
        }
        dest.writeLong(createdTimeInMillis);

        CaseStatus status = super.getStatus();
        String statusStr = (status != null ? status.name() : null);
        dest.writeString(statusStr);

        dest.writeParcelable(new PatientEntity(super.getPatient()), flags);

        PhysicianEntity pPhysician = null;
        if (super.getPhysician() != null) {
            pPhysician = new PhysicianEntity(super.getPhysician());
        }
        dest.writeParcelable(pPhysician, flags);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Case> CREATOR = new Parcelable.Creator<Case>() {
        public Case createFromParcel(Parcel in) {
            return createCaseEntity(in);
        }

        public Case[] newArray(int size) {
            return new Case[size];
        }
    };


}
