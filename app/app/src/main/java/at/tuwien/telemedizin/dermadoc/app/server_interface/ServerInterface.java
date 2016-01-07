package at.tuwien.telemedizin.dermadoc.app.server_interface;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.NotificationList;

/**
 * Created by FAUser on 10.12.2015.
 */
public interface ServerInterface {

    public boolean login(AuthenticationData authenticationData); // TODO throws Error?

    /**
     * get the current user
     * @return
     */
    public Patient getUser();

    /**
     * get the cases of the current user
     * @return
     */
    public List<Case> getCases();

    public Case getCase(long id);

    public Case createCase();

    public CaseData addCaseData(CaseData caseData, long caseId);

    public List<Physician> getPhysicians();

}
