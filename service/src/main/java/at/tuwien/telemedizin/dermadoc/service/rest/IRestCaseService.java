package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

import java.util.List;

/**
 * Created by Lucas on 26.11.2015.
 */
public interface IRestCaseService {

    /*
     * GET
     */

    void getOpenCases(RestListener<List<Case>> listener);

    void getAllCases(RestListener<List<Case>> listener);

    void getCaseData(RestListener<List<CaseData>> listener, Case aCase);

    //TODO
    void getUser(RestListener<User> listener);


    /*
     * POST
     */

    void postAcceptCase(RestListener<Void> listener, Case aCase);

    void postCaseData(RestListener<CaseData> listener, Case aCase, CaseData caseData);

    void postNewCase(RestListener<Case> listener, Case aCase);
}
