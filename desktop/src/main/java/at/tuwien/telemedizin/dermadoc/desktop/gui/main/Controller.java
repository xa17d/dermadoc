package at.tuwien.telemedizin.dermadoc.desktop.gui.main;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCCaseTab;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCOpenCaseList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCPatientList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.GCPhysician;
import at.tuwien.telemedizin.dermadoc.desktop.gui.lock.LockStage;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.error.ErrorPane;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.OpenMainTabEventHandler;
import at.tuwien.telemedizin.dermadoc.desktop.service.*;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.GlobalVariables;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;
import org.controlsfx.control.NotificationPane;

import java.util.*;

/**
 * main controller
 * is the link between view and services
 */
public class Controller {

    private ICaseService caseService;
    private AuthenticationToken token;
    private Physician physician;

    @FXML private BorderPane bpMain;
    @FXML private TabPane tpMain;
    @FXML private Tab tabPatients;
    @FXML private Tab tabCases;

    private EventHandler<ActionEvent> openMainTabHandler;

    private ObservableList<Tab> mainTabList;
    private PatientCaseMap patientCaseMap;
    private ObservableList<Case> openCaseList;

    public Controller() {

        token = GlobalVariables.getToken();
        physician = GlobalVariables.getPhysician();
        openMainTabHandler = new OpenMainTabEventHandler(this);
    }

    @FXML
    public void initialize() {

        //initialize service layer
        caseService = new CaseService(token);

        //initialize physician view on top
        try {
            bpMain.setTop(new GCPhysician(this, physician, caseService.getNotificationList()));
        }
        catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }

        //initialize the patient list
        patientCaseMap = new PatientCaseMap();
        try {
            patientCaseMap = caseService.getAllCases();
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }
        tabPatients.setContent(new GCPatientList(this, patientCaseMap));

        //initialize the case list
        try {
            openCaseList = caseService.getOpenCases();
            tabCases.setContent(new GCOpenCaseList(this, openCaseList));
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }

        //manage open tabs in main window
        mainTabList = tpMain.getTabs();
    }


    public void openMainTab(Case aCase) {
        mainTabList.add(new GCCaseTab(this, tpMain, aCase));
    }

    public EventHandler<javafx.event.ActionEvent> getOpenMainTabHandler() {
        return openMainTabHandler;
    }

    public void showErrorMessage(String errorMessage) {

        NotificationPane errorPane = new ErrorPane(bpMain, errorMessage);
        errorPane.setShowFromTop(false);
        errorPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);

        //TODO what the fucking fuck???
        //why is the pane showing, when running in another thread with a short sleep,
        //but not in the fx thread (also not with a sleep)???

        //errorPane.show();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                errorPane.show();
            }
        });
        t1.start();
    }

    public PatientCaseMap searchPatientCaseMap(String searchText) {
        try {
            return caseService.searchCases(searchText);
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }
        return patientCaseMap;
    }

    public Case getCaseById(long id) {

        //TODO get cases from backend or from observable list???
        //caseService.getCaseById();
        return new Case(id, new Patient(), Calendar.getInstance());
    }

    public ObservableList<CaseData> getCaseData(Case aCase) {
        try {
            return caseService.getCaseData(aCase);
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }
        return FXCollections.observableArrayList();
    }

    public void saveCaseData(Case aCase, CaseData caseData) {

        try {
            caseService.saveCaseData(aCase, caseData);
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }
        //return caseData;
    }

    public void acceptCase(Case aCase) {

        try {
            //TODO fix waiting time
            caseService.acceptCase(aCase);
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }
    }

    public Physician getPhysician() {
        return physician;
    }

    public ObservableList<Case> getOpenCaseList() {
        return openCaseList;
    }

    public PatientCaseMap getPatientCaseMap() {
        return patientCaseMap;
    }

    public void lock() {

        Stage mainStage = (Stage) bpMain.getScene().getWindow();
        mainStage.close();
        showLockStage(mainStage);
    }

    public void showLockStage(Stage mainStage) {

        new LockStage(mainStage).show();
    }
}