package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseDataList;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.entities.rest.NotificationList;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

import java.util.List;

/**
 * Created by Lucas on 27.11.2015.
 */
public class RestCaseService implements IRestCaseService {

    private static final String URL_ = "http://dermadoc.xa1.at:82/";
    //private static final String URL_ = "http://localhost:8080/";
    private static final String CASES_ = "cases/";
    private static final String NOTIFICATIONS_ = "notifications/";
    private static final String OPEN = "open";
    private static final String _DATA = "/data";
    private static final String _ACCEPT = "/accept";

    private AuthenticationToken token;

    public RestCaseService(AuthenticationToken token) {

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
    public void getCaseById(RestListener<Case> listener, long caseId) {
        new Thread(new GetCase(token, listener, caseId)).start();
    }

    @Override
    public void getCaseData(RestListener<CaseDataList> listener, Case aCase) {
        new Thread(new GetCaseData(token, listener, aCase.getId())).start();
    }

    @Override
    public void getNotifications(RestListener<NotificationList> listener) {
        new Thread(new GetNotifications(token, listener)).start();
    }

    @Override
    public void postAcceptCase(RestListener<Void> listener, Case aCase) {

    }

    @Override
    public void postCaseData(RestListener<CaseData> listener, Case aCase, CaseData caseData) {
        new Thread(new PostNewCaseData(token, listener, aCase.getId(), caseData)).start();
    }

    @Override
    public void postNewCase(RestListener<Case> listener, Case aCase) {
        new Thread(new PostNewCase(token, listener, aCase)).start();
    }



    /*
     * RUNNABLES
     */

    private class GetOpenCases implements Runnable {

        private AuthenticationToken token;
        private RestListener<CaseList> listener;
        public GetOpenCases(AuthenticationToken token, RestListener<CaseList> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<CaseList> rest = new GetRequest<>(token, CaseList.class);
            rest.get(URL_ + CASES_ + OPEN, listener);
        }
    }

    private class GetAllCases implements Runnable {

        private AuthenticationToken token;
        private RestListener<CaseList> listener;
        public GetAllCases(AuthenticationToken token, RestListener<CaseList> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<CaseList> rest = new GetRequest<>(token, CaseList.class);
            rest.get(URL_ + CASES_, listener);
        }
    }

    private class GetCase implements Runnable {

        private AuthenticationToken token;
        private RestListener<Case> listener;
        private long caseId;
        public GetCase(AuthenticationToken token, RestListener<Case> listener, long caseId) {
            this.token = token;
            this.listener = listener;
            this.caseId = caseId;
        }

        @Override
        public void run() {
            GetRequest<Case> rest = new GetRequest<>(token, Case.class);
            rest.get(URL_ + CASES_ + String.valueOf(caseId), listener);
        }
    }

    private class GetCaseData implements Runnable {

        private AuthenticationToken token;
        private RestListener<CaseDataList> listener;
        private long caseId;
        public GetCaseData(AuthenticationToken token, RestListener<CaseDataList> listener, long caseId) {
            this.token = token;
            this.listener = listener;
            this.caseId = caseId;
        }

        @Override
        public void run() {
            GetRequest<CaseDataList> rest = new GetRequest<>(token, CaseDataList.class);
            rest.get(URL_ + CASES_ + String.valueOf(caseId) + _DATA, listener);
        }
    }

    private class GetNotifications implements Runnable {

        private AuthenticationToken token;
        private RestListener<NotificationList> listener;
        public GetNotifications(AuthenticationToken token, RestListener<NotificationList> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<NotificationList> rest = new GetRequest<>(token, NotificationList.class);
            rest.get(URL_ + NOTIFICATIONS_, listener);
        }
    }



    private class PostNewCase implements Runnable {

        private AuthenticationToken token;
        private RestListener<Case> listener;
        private Case aCase;
        public PostNewCase(AuthenticationToken token, RestListener<Case> listener, Case aCase) {
            this.token = token;
            this.listener = listener;
            this.aCase = aCase;
        }

        @Override
        public void run() {
            PostRequest<Case, Case> rest = new PostRequest<>(token, Case.class);
            rest.post(URL_ + CASES_, listener, aCase);
        }
    }

    private class PostNewCaseData implements Runnable {

        private AuthenticationToken token;
        private RestListener<CaseData> listener;
        private long caseId;
        private CaseData caseData;
        public PostNewCaseData(AuthenticationToken token, RestListener<CaseData> listener, long caseId, CaseData caseData) {
            this.token = token;
            this.listener = listener;
            this.caseId = caseId;
            this.caseData = caseData;
        }

        @Override
        public void run() {
            PostRequest<CaseData, CaseData> rest = new PostRequest<>(token, CaseData.class);
            rest.post(URL_ + CASES_ + String.valueOf(caseId) + _DATA, listener, caseData);
        }
    }
}
