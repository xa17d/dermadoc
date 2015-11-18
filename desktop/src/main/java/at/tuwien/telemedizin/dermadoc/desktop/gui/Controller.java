package at.tuwien.telemedizin.dermadoc.desktop.gui;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCCaseList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCMainTab;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCPatientList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.error.ErrorPane;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler.OpenMainTabEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;

import javax.swing.*;
import java.util.*;

/**
 * main controller
 * is the link between view and services
 */
public class Controller {

    @FXML BorderPane bpMain;
    @FXML private ImageView imageViewDoctor;
    @FXML private Tab tabPatients;
    @FXML private Tab tabCases;
    @FXML private TabPane tpMain;
    @FXML private Button btLogout;

    private EventHandler<javafx.event.ActionEvent> openMainTabHandler;

    private ObservableList<Tab> mainTabList;

    public Controller() {
        openMainTabHandler = new OpenMainTabEventHandler(this);
    }

    @FXML
    public void initialize() {

        //TODO tmp
        imageViewDoctor.setImage(new Image(Main.class.getResourceAsStream("drlucas.jpg")));

        //initialize the patient list
        tabPatients.setContent(new GCPatientList(this));

        //initialize the case list

        //TODO get cases from backend
        ///*
        //MOCK
        ObservableList<Case> mockCases = FXCollections.observableArrayList();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                int i = 0;
                while (i < 20) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Patient p1 = new Patient();
                    p1.setName("Daniel Gehrer");
                    p1.setGender(Gender.Male);
                    p1.setBirthTime(new GregorianCalendar(1990, 9, 18));
                    Case mockCase = new Case(i, p1, new GregorianCalendar(2015, 11, 10));
                    mockCase.setPhysician(new Physician());
                    mockCase.setStatus(CaseStatus.WaitingForAccept);

                    //exception thrown here doesn't matter at the moment, this is just a mock for testing ui components
                    mockCases.add(mockCase);
                    i++;
                }
            }
        });
        t.start();
        //-----

        tabCases.setContent(new GCCaseList(this, mockCases));
        //*/

        //manage open tabs in main window
        mainTabList = tpMain.getTabs();

        //MOCK
        openMainTab(null);
    }


    public void openMainTab(Case aCase) {
        //MOCK
        Patient p1 = new Patient();
        p1.setName("Daniel Gehrer");
        p1.setGender(Gender.Male);
        p1.setBirthTime(new GregorianCalendar(1990, 9, 18));
        p1.setSvnr("3023");
        Case mockCase = new Case(0l, p1, new GregorianCalendar(2015, 11, 10));
        mockCase.setPhysician(new Physician());
        mockCase.setStatus(CaseStatus.WaitingForAccept);
        //-----

        mainTabList.add(new GCMainTab(this, mockCase));
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

    @FXML
    private void logout() {

        //TODO logout on backend
        Stage mainStage = (Stage) btLogout.getScene().getWindow();
        mainStage.close();
        login(mainStage);

        /*
        //for testing
        PopOver popOver = new PopOver(new Label("Sorry, there are no notifications"));
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
        popOver.show(btLogout);
        */
    }

    protected void login(Stage mainStage) {

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