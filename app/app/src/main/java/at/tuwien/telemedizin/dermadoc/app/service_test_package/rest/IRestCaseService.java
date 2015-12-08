package at.tuwien.telemedizin.dermadoc.app.service_test_package.rest;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseDataList;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.entities.rest.NotificationList;
import at.tuwien.telemedizin.dermadoc.app.service_test_package.rest.listener.RestListener;

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

    void getCaseData(RestListener<CaseDataList> listener, Case aCase);

    void getNotifications(RestListener<NotificationList> listener);


    /*
     * POST
     */

    void postAcceptCase(RestListener<Void> listener, Case aCase);

    void postCaseData(RestListener<CaseData> listener, Case aCase, CaseData caseData);

    void postNewCase(RestListener<Case> listener, Case aCase);
}
