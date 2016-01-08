package at.tuwien.telemedizin.dermadoc.app.comparators;

import java.util.Comparator;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseListItem;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.NotificationParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.CaseStatus;

/**
 * Created by FAUser on 18.11.2015.
 */
public class CaseListItemNotificationComparator implements Comparator<List<NotificationParc>> {


    @Override
    public int compare(List<NotificationParc> lhs, List<NotificationParc> rhs) {
        int compResult;
        // take null values into account
        if (lhs == null) {
            if (rhs == null) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (rhs == null) {
                return -1;
            } else {
                if (lhs.size() > rhs.size()) {
                    return -1;
                } else if (lhs.size() == rhs.size()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
    }

}
