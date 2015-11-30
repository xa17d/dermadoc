package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

import java.util.List;

/**
 * Created by Lucas on 26.11.2015.
 */
public interface IRestCaseService {

    /*
     * GET
     */

    void getOpenCases(RestListener<CaseList> listener);

    void getAllCases(RestListener<CaseList> listener);

    void getCaseById(RestListener<Case> listener, long caseId);

    void getCaseData(RestListener<List<CaseData>> listener, Case aCase);

    /**
     * set a notification listerner that gets called when a new
     * notification is available
     * @param handler notification handler
     */
    void setNotificationHandler(DermadocNotificationHandler handler);


    /*
     * POST
     */

    void postAcceptCase(RestListener<Void> listener, Case aCase);

    void postCaseData(RestListener<CaseData> listener, Case aCase, CaseData caseData);

    void postNewCase(RestListener<Case> listener, Case aCase);
}
