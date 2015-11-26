package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.RestCaseServiceMock;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import at.tuwien.telemedizin.dermadoc.service.util.UtilCompare;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;

/**
 * Created by Lucas on 26.11.2015.
 */
public class CaseService implements ICaseService {

    private IRestCaseService rest;

    private ObservableList<Case> obsOpenCaseList = FXCollections.observableArrayList();
    private ObservableList<Notification> obsNotificationList = FXCollections.observableArrayList();
    private PatientCaseMap obsPatientCaseMap = new PatientCaseMap();

    public CaseService(AuthenticationToken token) {

        rest = new RestCaseServiceMock(token);
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

        ObservableList<CaseData> obsCaseDataList = FXCollections.observableArrayList();

        RestListener<List<CaseData>> listener = new RestListener<List<CaseData>>() {
            @Override
            public void onRequestComplete(List<CaseData> requestResult) {
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

        //TODO bugfix
        rest.postAcceptCase(acceptCaseListener, aCase);
    }

    @Override
    public void saveCaseData(Case aCase, CaseData caseData) throws DermadocException {

        rest.postCaseData(caseDataListener, aCase, caseData);
    }

    @Override
    public ObservableList<Notification> getNotificationList() throws DermadocException {

        DermadocNotificationHandler notificationHandler = new DermadocNotificationHandler() {
            @Override
            public void onNewNotifications(List<Notification> notifications) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        obsNotificationList.addAll(notifications);
                    }
                });
            }
        };

        rest.setNotificationHandler(notificationHandler);
        return obsNotificationList;
    }

    /*
     * LISTENER
     */

    private RestListener<List<Case>> openCasesListener = new RestListener<List<Case>>() {
        @Override
        public void onRequestComplete(List<Case> requestResult) {
            obsOpenCaseList = FXCollections.observableArrayList(requestResult);
        }

        @Override
        public void onError(Error error) {
            //TODO
        }
    };

    private RestListener<List<Case>> allCasesListener = new RestListener<List<Case>>() {
        @Override
        public void onRequestComplete(List<Case> requestResult) {

            for(Case c : requestResult) {
                obsPatientCaseMap.put(c);
            }
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
     * BUSINESS LOGIC
     */

    @Override
    public PatientCaseMap searchCases(String searchText) throws DermadocException {

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

        return obsPatientCaseMap;
    }
}
