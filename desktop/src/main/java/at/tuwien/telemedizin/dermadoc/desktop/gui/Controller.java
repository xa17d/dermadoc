package at.tuwien.telemedizin.dermadoc.desktop.gui;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCCaseList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCMainTab;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCPatientList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler.OpenMainTabEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.NotificationPane;

import java.util.*;

/**
 * main controller
 * is the link between view and services
 */
public class Controller {

    @FXML private GridPane gpTop;
    @FXML private ImageView imageViewDoctor;
    @FXML private Tab tabPatients;
    @FXML private Tab tabCases;
    @FXML private TabPane tpMain;

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

        showErrorMessage();
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

    private void showErrorMessage() {

        WebView webView = new WebView();
        NotificationPane notificationPane = new NotificationPane(webView);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        notificationPane.setText("WTF?!");
        notificationPane.show();

        GridPane.setColumnIndex(notificationPane, 0);
        GridPane.setRowIndex(notificationPane, 1);

        gpTop.getChildren().add(notificationPane);
    }
}