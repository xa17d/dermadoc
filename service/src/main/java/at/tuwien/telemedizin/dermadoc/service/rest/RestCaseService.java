package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import at.tuwien.telemedizin.dermadoc.service.rest.requests.GetRequest;

import java.net.URL;
import java.util.List;

/**
 * Created by Lucas on 27.11.2015.
 */
public class RestCaseService implements IRestCaseService {

    private static final String URL_ = "http://dermadoc.xa1.at:82/";
    private static final String USER = "user";
    private static final String CASES = "cases";
    private static final String CASES_OPEN = "cases/open";
    private static final String _ACCEPT = "/accept";

    private AuthenticationToken token;

    public RestCaseService(AuthenticationToken token) {

        //TODO token after login
        this.token = token;
    }


    @Override
    public void getOpenCases(RestListener<CaseList> listener) {
        new Thread(new GetOpenCases(token, listener)).start();
    }

    @Override
    public void getAllCases(RestListener<CaseList> listener) {
        new Thread(new GetAllCases(token, listener)).start();
    }

    @Override
    public void getCaseData(RestListener<List<CaseData>> listener, Case aCase) {

    }

    @Override
    public void getUser(RestListener<User> listener) {
        new Thread(new GetUser(token, listener)).start();
    }

    @Override
    public void setNotificationHandler(DermadocNotificationHandler handler) {

    }

    @Override
    public void postAcceptCase(RestListener<Void> listener, Case aCase) {

    }

    @Override
    public void postCaseData(RestListener<CaseData> listener, Case aCase, CaseData caseData) {

    }

    @Override
    public void postNewCase(RestListener<Case> listener, Case aCase) {

    }



    /*
     * RUNNABLES
     */

    public class GetOpenCases implements Runnable {

        private AuthenticationToken token;
        private RestListener<CaseList> listener;
        public GetOpenCases(AuthenticationToken token, RestListener<CaseList> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<CaseList> rest = new GetRequest<>(token, CaseList.class);
            rest.get(URL_ + CASES_OPEN, listener);
        }
    }

    public class GetAllCases implements Runnable {

        private AuthenticationToken token;
        private RestListener<CaseList> listener;
        public GetAllCases(AuthenticationToken token, RestListener<CaseList> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<CaseList> rest = new GetRequest<>(token, CaseList.class);
            rest.get(URL_ + CASES, listener);
        }
    }

    public class GetUser implements Runnable {

        private AuthenticationToken token;
        private RestListener<User> listener;
        public GetUser(AuthenticationToken token, RestListener<User> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            //TODO also for patient!!
            GetRequest<User> rest = new GetRequest<>(token, Physician.class);
            rest.get(URL_ + USER, listener);
        }
    }
}
