package at.tuwien.telemedizin.dermadoc.desktop.gui;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.*;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.error.ErrorPane;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler.GeneralEventHandler;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler.OpenMainTabEventHandler;
import at.tuwien.telemedizin.dermadoc.desktop.service.*;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.desktop.service.mock.LoginServiceMock;
import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import org.controlsfx.control.NotificationPane;

import java.util.*;

/**
 * main controller
 * is the link between view and services
 */
public class Controller {

    private ICaseService caseService;
    private ILoginService loginService;
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
        openMainTabHandler = new OpenMainTabEventHandler(this);
    }

    @FXML
    public void initialize() {

        //initialize service layer
        //TODO remove mock
        loginService = new LoginServiceMock();
        token = loginService.login(new AuthenticationData("email", "password"));
        caseService = new CaseService(token);
        try {
            physician = caseService.getPhysician();
        } catch (DermadocException e) {
            showErrorMessage(e.getMessage());
        }

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

    public void logout() {

        loginService.logout(token);
        Stage mainStage = (Stage) bpMain.getScene().getWindow();
        mainStage.close();
        login(mainStage);
    }

    public void login(Stage mainStage) {

        Stage loginStage = new Stage();
        loginStage.setAlwaysOnTop(true);
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.getIcons().add(new Image(Main.class.getResourceAsStream("dermadoc_icon_c_96.png")));
        loginStage.initOwner(mainStage);

        //TODO create nice fxml login dialog

        GridPane dialog = new GridPane();
        dialog.setAlignment(Pos.CENTER);
        Button btLogin = new Button("LOGIN");
        btLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.show();
                loginStage.hide();
            }
        });
        GridPane.setHalignment(btLogin, HPos.CENTER);
        GridPane.setValignment(btLogin, VPos.CENTER);
        dialog.getChildren().add(btLogin);
        Scene dialogScene = new Scene(dialog, 300, 200);
        loginStage.setScene(dialogScene);
        loginStage.show();
    }
}