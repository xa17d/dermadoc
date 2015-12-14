package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.entities.Physician;

/**
 * Created by daniel on 11.11.2015.
 */
public class CaseParc implements Parcelable {

    // key for the intent
    public static final String INTENT_KEY = CaseParc.class.getName() + "_INTENT_KEY";

    public CaseParc(long id, PatientParc patient, Calendar created) {
        this.id = id;
        this.patient = patient;
        this.created = created;
    }

    private long id;
    public long getId() { return id; }

    private PatientParc patient;
    public PatientParc getPatient() { return patient; }

    private PhysicianParc physician;
    public PhysicianParc getPhysician() { return physician; }
    public void setPhysician(PhysicianParc physician) { this.physician = physician; }

    private Calendar created;
    public Calendar getCreated() { return created; }

    private CaseStatus status;
    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /**
     * mapping constructor
     */
    public CaseParc(Case caseItem) {

        this(caseItem.getId(), new PatientParc(caseItem.getPatient()), caseItem.getCreated());
        this.physician = new PhysicianParc(caseItem.getPhysician());
        this.status = caseItem.getStatus();
        this.name = caseItem.getName();
    }

    @Override
    public String toString() {
        return "CaseParc{" +
                "id=" + id +
                ", patient=" + patient +
                ", physician=" + physician +
                ", created=" + FormatHelper.calendarToDateFormatString(created) +
                ", status=" + status +
                ", name='" + name + '\'' +
                '}';
    }

    // parcelable ################################

    public CaseParc(Parcel in) {

        this.id = in.readLong();
        this.name = in.readString();

        long createdTimeInMillis = in.readLong();
        this.created = ParcelableHelper.longToCalendar(createdTimeInMillis);

        String statusStr = in.readString();
        if (statusStr != null) {
            this.status = CaseStatus.valueOf(statusStr);
        }

        this.patient = in.readParcelable(PatientParc.class.getClassLoader());
        this.physician = in.readParcelable(PhysicianParc.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(name);

        long createdInMillis = -1;
        if (created != null) { // avoid nullpointer exception
            createdInMillis = created.getTimeInMillis();
        }
        dest.writeLong(createdInMillis);

        String statusStr = status != null ? status.name() : null;
        dest.writeString(statusStr);

        dest.writeParcelable(patient, flags);
        dest.writeParcelable(physician, flags);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<CaseParc> CREATOR = new Parcelable.Creator<CaseParc>() {
        public CaseParc createFromParcel(Parcel in) {
            return new CaseParc(in);
        }

        public CaseParc[] newArray(int size) {
            return new CaseParc[size];
        }
    };
}
