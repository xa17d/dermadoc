package at.tuwien.telemedizin.dermadoc.app.entities.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by FAUser on 08.01.2016.
 */
public class CaseListItem implements Parcelable {

    public static final String INTENT_KEY = CaseListItem.class.getSimpleName() + "caseListItem";

    private CaseParc caseItem;
    private List<NotificationParc> notifications;

    public CaseListItem(CaseParc caseItem, List<NotificationParc> notifications) {
        this.caseItem = caseItem;
        this.notifications = notifications;
    }

    public CaseParc getCaseItem() {
        return caseItem;
    }

    public void setCaseItem(CaseParc caseItem) {
        this.caseItem = caseItem;
    }

    public List<NotificationParc> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationParc> notifications) {
        this.notifications = notifications;
    }


    // parcelable ################################

    public CaseListItem(Parcel in) {

        this.caseItem = in.readParcelable(CaseParc.class.getClassLoader());
        notifications = new ArrayList<>();
        in.readList(notifications, NotificationParc.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(caseItem, flags);
        dest.writeList(notifications);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<CaseListItem> CREATOR = new Parcelable.Creator<CaseListItem>() {
        public CaseListItem createFromParcel(Parcel in) {
            return new CaseListItem(in);
        }

        public CaseListItem[] newArray(int size) {
            return new CaseListItem[size];
        }
    };
}
