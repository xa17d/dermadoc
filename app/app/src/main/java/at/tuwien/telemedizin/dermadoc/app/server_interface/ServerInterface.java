package at.tuwien.telemedizin.dermadoc.app.server_interface;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Notification;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.AuthenticationData;

/**
 * Created by FAUser on 10.12.2015.
 */
public interface ServerInterface {

    public boolean login(AuthenticationData authenticationData); // TODO throws Error?

    /**
     * get the current user
     * @return
     */
    public User getUser();


    /**
     * get the cases of the current user
     * @return
     */
    public List<Case> getCases();

    public Case getCase(long id);

    public List<CaseData> getCaseData(long caseId);

    public Case createCase(Case caseItem);

    public CaseData addCaseData(CaseData caseData, long caseId);

    public List<Physician> getPhysicians();

    public List<Notification> getNotifications();

    public Boolean deleteNotification(long notificationId);

}
