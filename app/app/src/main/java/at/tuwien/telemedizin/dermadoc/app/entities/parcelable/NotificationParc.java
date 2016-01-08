package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Notification;

/**
 * Created by Lucas on 17.11.2015.
 */
public class NotificationParc implements Parcelable {

    public NotificationParc() {

    }

    private long id;
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    private long userId;
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    private long caseId;
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

    @Override
    public boolean equals(Object o) {

        if(o == null)
            return false;

        if(this.hashCode() != o.hashCode())
            return false;

        if(this.getClass() != o.getClass())
            return false;

        Notification n = (Notification) o;
        return this.getId() == n.getId();
    }

    public NotificationParc(Notification notification) {
        this.setId(notification.getId());
        this.setCaseId(notification.getId());
        this.setText(notification.getText());
        this.setUserId(notification.getUserId());
    }


    // parcelable ################################

    public  NotificationParc(Parcel in) {

        this.setId(in.readLong());
        this.setCaseId(in.readLong());


        String textIn = in.readString();
        setText(textIn);

        this.setUserId(in.readLong());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeLong(caseId);

        dest.writeString(getText());

        dest.writeLong(getUserId());
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
