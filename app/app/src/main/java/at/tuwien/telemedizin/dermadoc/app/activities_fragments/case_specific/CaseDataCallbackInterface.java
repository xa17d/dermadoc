package at.tuwien.telemedizin.dermadoc.app.activities_fragments.case_specific;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.NotificationParc;

/**
 * Created by FAUser on 20.11.2015.
 */
public interface CaseDataCallbackInterface {

    public CaseParc getCase();

    public List<NotificationParc> getNotifications();

    public void deleteNotifications();
}
