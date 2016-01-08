package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.service.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.Error;
import at.tuwien.telemedizin.dermadoc.service.rest.RestCaseService;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lucas on 26.11.2015.
 */
public class CaseService implements ICaseService {

    private IRestCaseService rest;

    private ObservableList<Case> obsOpenCaseList = FXCollections.observableArrayList();
    private ObservableList<Notification> obsNotificationList = FXCollections.observableArrayList();
    private PatientCaseMap obsPatientCaseMap = new PatientCaseMap();

    private HashMap<Case, ObservableList<CaseData>> caseCaseDataMap= new HashMap<>();

    public CaseService(AuthenticationToken token) {

        rest = new RestCaseService(token);

        startPollingNotifications();
    }


    /*
     * REST CALLS
     */

    @Override
    public ObservableList<Case> getOpenCases() throws DermadocException {

        rest.getOpenCases(openCasesListener);
        return obsOpenCaseList;
    }

    @Override
    public PatientCaseMap getAllCases() throws DermadocException {

        rest.getAllCases(allCasesListener);
        return obsPatientCaseMap;
    }

    @Override
    public ObservableList<CaseData> getCaseData(Case aCase) throws DermadocException {

        ObservableList<CaseData> obsCaseDataList;
        if(caseCaseDataMap.containsKey(aCase)) {
            obsCaseDataList = caseCaseDataMap.get(aCase);
        }
        else {
            obsCaseDataList = FXCollections.observableArrayList();
        }

        RestListener<CaseDataList> listener = new RestListener<CaseDataList>() {
            @Override
            public void onRequestComplete(CaseDataList requestResult) {
                obsCaseDataList.addAll(requestResult);
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        };

        rest.getCaseData(listener, aCase);
        return obsCaseDataList;
    }

    @Override
    public void acceptCase(Case aCase) throws DermadocException {
        rest.postAcceptCase(acceptCaseListener, aCase);
    }

    @Override
    public void saveCaseData(Case aCase, CaseData caseData) throws DermadocException {
        rest.postCaseData(caseDataListener, aCase, caseData);
    }


    /*
     * LISTENER
     */

    private RestListener<CaseList> openCasesListener = new RestListener<CaseList>() {
        @Override
        public void onRequestComplete(CaseList requestResult) {
            obsOpenCaseList.addAll(requestResult);
        }

        @Override
        public void onError(Error error) {
            //TODO
        }
    };

    private RestListener<CaseList> allCasesListener = new RestListener<CaseList>() {
        @Override
        public void onRequestComplete(CaseList requestResult) {

            obsPatientCaseMap.putAll(requestResult);
            obsPatientCaseMap.sort();
        }

        @Override
        public void onError(Error error) {
            //TODO
        }
    };

    private RestListener<Case> caseByIdListener = new RestListener<Case>() {
        @Override
        public void onRequestComplete(Case requestResult) {
            obsPatientCaseMap.put(requestResult);
            obsPatientCaseMap.sort();
        }

        @Override
        public void onError(Error error) {
            //TODO
        }
    };

    private RestListener<Void> acceptCaseListener = new RestListener<Void>() {
        @Override
        public void onRequestComplete(Void requestResult) {
            //TODO
        }

        @Override
        public void onError(Error error) {
            //TODO
        }
    };

    private RestListener<CaseData> caseDataListener = new RestListener<CaseData>() {
        @Override
        public void onRequestComplete(CaseData requestResult) {
            //TODO
        }

        @Override
        public void onError(Error error) {
            //TODO
        }
    };




    /*
     * NOTIFICATIONS
     */

    @Override
    public ObservableList<Notification> getNotificationList() throws DermadocException {
        return obsNotificationList;
    }

    private boolean stopPollingNotifications = false;
    private void startPollingNotifications() {

        new Thread(new NotificationPoller(new DermadocNotificationHandler() {
            @Override
            public void onNewNotifications(List<Notification> notifications) {

                for(Notification n : notifications) {
                    computeNotification(n);
                }
            }

            private void computeNotification(Notification notification) {

                if(!obsNotificationList.contains(notification)) {
                    obsNotificationList.add(notification);

                    for(Patient p : obsPatientCaseMap.keySet()) {
                        ObservableList<Case> cases = obsPatientCaseMap.get(p);
                        for(Case c : cases) {
                            //case already exists - update case data
                            if(c.getId() == notification.getCaseId()) {

                                caseCaseDataMap.remove(c);
                                try {
                                    getCaseData(c);
                                } catch (DermadocException e) {
                                    e.printStackTrace();
                                }

                                return;
                            }
                        }
                    }

                    //case not yet existing - get case
                    rest.getCaseById(caseByIdListener, notification.getCaseId());
                }
            }
        })).start();
    }

    public void stopPolling() {
        stopPollingNotifications = true;
    }



    private class NotificationPoller implements Runnable {

        private DermadocNotificationHandler handler;

        public NotificationPoller(DermadocNotificationHandler handler) {

            this.handler = handler;
        }

        @Override
        public void run() {
            while(!stopPollingNotifications) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                rest.getNotifications(notificationListener);
            }
        }

        private RestListener<NotificationList> notificationListener = new RestListener<NotificationList>() {
            @Override
            public void onRequestComplete(NotificationList requestResult) {
                handler.onNewNotifications(requestResult);
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        };
    }

    @Override
    public Case getCaseById(long id) {

        for(Patient p : obsPatientCaseMap.keySet()) {
            ObservableList<Case> list = obsPatientCaseMap.get(p);

            for(Case c : list) {
                if(c.getId() == id) {
                    return c;
                }
            }
        }

        //TODO exception
        return null;
    }



    /*
     * BUSINESS LOGIC
     */

    @Override
    public PatientCaseMap searchCases(String searchText) throws DermadocException {

        //TODO
        /*
        if(searchText.length() >= 3) {

            PatientCaseMap resultMap = new PatientCaseMap();
            Set<Patient> patients = obsPatientCaseMap.keySet();
            for (Patient p : patients) {
                for (Case c : obsPatientCaseMap.get(p)) {
                    if (UtilCompare.contains(searchText, c)) {
                        resultMap.put(c);
                    }
                }
            }

            return resultMap;
        }
        */

        return obsPatientCaseMap;
    }
}
