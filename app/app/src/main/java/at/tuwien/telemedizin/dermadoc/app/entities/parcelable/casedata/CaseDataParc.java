package at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.UserParc;
import at.tuwien.telemedizin.dermadoc.app.helper.FormatHelper;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;

/**
 * Abstract CaseData.
 * A case can have multiple CaseData elements.
 */
public abstract class CaseDataParc implements Parcelable {

    public CaseDataParc(long id, Calendar created, UserParc author) {
        this.id = id;
        this.created = created;
        this.author = author;
    }

    private long id;
    public long getId() { return id; }

    private Calendar created;
    public Calendar getCreated() { return created; }

    private UserParc author;
    public UserParc getAuthor() { return author; }

    private boolean isPrivate;
    public boolean getPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }

    private CaseDataParc nextVersion;
    public CaseDataParc getNextVersion() { return nextVersion; }
    public void setNextVersion(CaseDataParc nextVersion) { this.nextVersion = nextVersion; }

    private boolean isObsolete() { return (nextVersion != null); }

    @Override
    public String toString() {
        return "CaseDataParc{" +
                "id=" + id +
                ", created=" + FormatHelper.calendarToDateFormatString(created) +
                ", author=" + author +
                ", isPrivate=" + isPrivate +
                ", isObsolete=" + isObsolete() +
                '}';
    }

    /**
     * mapping constructor
     * @param caseData
     */
    public CaseDataParc(CaseData caseData) {
        this(caseData.getId(), caseData.getCreated(), ParcelableHelper.mapUserToUserParc(caseData.getAuthor()));
        this.isPrivate = caseData.getPrivate();

//        CaseData nextVersionIn = caseData.getNextVersion();
//        if (nextVersionIn != null) {
//            this.nextVersion = new CaseDataParc(nextVersionIn);
//        }
        // für jede Klasse einzeln?!  // TODO
    }

    // parcelable ################################

    public CaseDataParc(Parcel in) {

        this.id = in.readLong();

        long createdTimeInMillis = in.readLong();
        this.created = ParcelableHelper.longToCalendar(createdTimeInMillis);

        this.author = in.readParcelable(User.class.getClassLoader()); // null -> should use the default classLoader specified in the non-abstract class .. NOT WORKING WITH NULL

        int isPrivateAsInt = in.readInt();
        this.isPrivate = (isPrivateAsInt == 1);
        //
        this.nextVersion = in.readParcelable(null); // null -> should use the default classLoader specified in the non-abstract class
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);

        long createdInMillis = -1;
        if (created != null) { // avoid nullpointer exception
            createdInMillis = created.getTimeInMillis();
        }
        dest.writeLong(createdInMillis);

        dest.writeParcelable(author, flags);

        int isPrivateAsInt = isPrivate ? 1 : 0;
        dest.writeInt(isPrivateAsInt);

        dest.writeParcelable(nextVersion, flags);

    }

}
