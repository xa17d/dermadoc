package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocNotImplementedException;
import at.tuwien.telemedizin.dermadoc.desktop.service.handler.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * MOCK!!!
 */
public class CaseServiceMock implements ICaseService {

    private ObservableList<Case> obsOpenCaseList = FXCollections.observableArrayList();
    private ObservableList<Patient> obsPatientList = FXCollections.observableArrayList();
    private ObservableList<Notification> obsNotificationList = FXCollections.observableArrayList();
    private ObservableMap<Patient, ObservableList<Case>> obsPatientCaseMap = FXCollections.observableHashMap();


    public CaseServiceMock(AuthenticationToken token) {

        Patient p1 = new Patient();
        p1.setName("Daniel Gehrer");
        p1.setSvnr("1234");
        p1.setGender(Gender.Male);
        p1.setBirthTime(Calendar.getInstance());

        Patient p2 = new Patient();
        p2.setName("Lilly Treml");
        p2.setSvnr("2345");
        p2.setGender(Gender.Female);
        p2.setBirthTime(Calendar.getInstance());

        Patient p3 = new Patient();
        p3.setName("Fabian Filip");
        p3.setSvnr("3456");
        p3.setGender(Gender.Male);
        p3.setBirthTime(Calendar.getInstance());

        Case c11 = new Case(-1l, p1, Calendar.getInstance());
        c11.setName("HELP!!");
        Case c12 = new Case(-1l, p1, Calendar.getInstance());
        c12.setName("OMG!!");
        Case c13 = new Case(-1l, p1, Calendar.getInstance());
        c13.setName("I'm a girl!!");
        ObservableList<Case> p1Cases = FXCollections.observableArrayList();
        p1Cases.add(c11);
        p1Cases.add(c12);
        p1Cases.add(c13);

        Case c21 = new Case(-1l, p2, Calendar.getInstance());
        c21.setName("Rash on Hand!!");
        Case c22 = new Case(-1l, p2, Calendar.getInstance());
        c22.setName("What shall i do??!!");
        Case c23 = new Case(-1l, p2, Calendar.getInstance());
        c23.setName("I have a question!!");
        ObservableList<Case> p2Cases = FXCollections.observableArrayList();
        p2Cases.add(c21);
        p2Cases.add(c22);
        p2Cases.add(c23);

        Case c31 = new Case(-1l, p3, Calendar.getInstance());
        c31.setName("rash on upper arm");
        ObservableList<Case> p3Cases = FXCollections.observableArrayList();
        p3Cases.add(c31);

        obsPatientCaseMap.put(p1, p1Cases);
        obsPatientCaseMap.put(p2, p2Cases);
        obsPatientCaseMap.put(p3, p3Cases);
    }

    @Override
    public ObservableList<Case> getOpenCases() throws DermadocException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int j = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            Patient p1 = new Patient();
                            p1.setName("Patient " + j);
                            p1.setGender(Gender.Male);
                            p1.setBirthTime(new GregorianCalendar(1990, 9, 18));
                            Case mockCase = new Case(-1l, p1, new GregorianCalendar(2015, 11, 10));
                            mockCase.setName("case " + j);
                            mockCase.setStatus(CaseStatus.WaitingForAccept);

                            //exception thrown here doesn't matter at the moment, this is just a mock for testing ui components
                            obsOpenCaseList.add(mockCase);
                        }
                    });
                }
            }
        });
        t.start();

        return obsOpenCaseList;
    }

    @Override
    public ObservableMap<Patient, ObservableList<Case>> getAllCases() throws DermadocException {
        return obsPatientCaseMap;
    }

    @Override
    public ObservableMap<Patient, ObservableList<Case>> getCasesOfPatient(String searchText) throws DermadocException {
        throw new DermadocNotImplementedException();
    }

    @Override
    public ObservableList<CaseData> getCaseData(Case aCase) throws DermadocException {
        return null;
    }

    @Override
    public void acceptCase(Case aCase) throws DermadocException {

    }

    @Override
    public Case saveCase(Case aCase) throws DermadocException {
        return null;
    }

    @Override
    public ObservableList<Notification> getNotificationList() throws DermadocException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Notification notification = new Notification();
                            notification.setText("Daniel Gehrer sent you a new message!");

                            //exception thrown here doesn't matter at the moment, this is just a mock for testing ui components
                            obsNotificationList.add(notification);
                        }
                    });
                }
            }
        });
        t.start();

        return obsNotificationList;
    }
}
