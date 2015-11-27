package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocNotImplementedException;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler.GeneralEventHandler;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.service.rest.RestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;
import at.tuwien.telemedizin.dermadoc.service.util.UtilCompare;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Lucas on 26.11.2015.
 */
public class CaseService implements ICaseService {

    private IRestCaseService rest;

    private ObservableList<Case> obsOpenCaseList = FXCollections.observableArrayList();
    private ObservableList<Notification> obsNotificationList = FXCollections.observableArrayList();
    private PatientCaseMap obsPatientCaseMap = new PatientCaseMap();

    public CaseService(AuthenticationToken token) {

        rest = new RestCaseService(token);
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
    public Physician getPhysician() throws DermadocException {

        //TODO oher solution?
        final ExecutorService service;
        final Future<Physician> task;

        service = Executors.newFixedThreadPool(1);
        task = service.submit(new Callable<Physician>() {
            Physician physician = null;
            @Override
            public Physician call() throws Exception {
                rest.getUser(new RestListener<User>() {
                    @Override
                    public void onRequestComplete(User requestResult) {
                        if(requestResult instanceof Physician)
                            physician = (Physician) requestResult;
                        else
                            onError(new Error("You are not logged in as a Physician"));
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });

                while(physician == null) { Thread.sleep(50); }
                return physician;
            }
        });

        try {
            return task.get();
        } catch(final InterruptedException ex) {
            //TODO
            ex.printStackTrace();
        } catch(final ExecutionException ex) {
            //TODO
            ex.printStackTrace();
        }

        service.shutdownNow();
        return null;
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
                        updatePatientCaseList();
                    }
                });
            }
        };

        rest.setNotificationHandler(notificationHandler);
        return obsNotificationList;
    }

    private void updatePatientCaseList() {

        //TODO not very elegant
        //obsPatientCaseMap = new PatientCaseMap();
        //rest.getAllCases(openCasesListener);
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
