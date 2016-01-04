package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas on 17.11.2015.
 */
public class NotificationParc implements Parcelable {

    private String text;
    private long caseId;

    public NotificationParc() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCaseId() {
        return caseId;
    }

    public void setCaseId(long caseId) {
        this.caseId = caseId;
    }

    @Override
    public String toString() {
        return text;
    }


    // parcelable ################################

    public  NotificationParc(Parcel in) {

        String textIn = in.readString();
        setText(textIn);

        long caseIdIn = in.readLong();
        setCaseId(caseIdIn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getText());

        dest.writeLong(getCaseId());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<NotificationParc> CREATOR = new Parcelable.Creator<NotificationParc>() {
        public NotificationParc createFromParcel(Parcel in) {
            return new NotificationParc(in);
        }

        public NotificationParc[] newArray(int size) {
            return new NotificationParc[size];
        }
    };
}
