package at.tuwien.telemedizin.dermadoc.desktop.gui;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCCaseList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCMainTab;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCPatientList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCPatientListItem;
import at.tuwien.telemedizin.dermadoc.entities.*;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.*;

/**
 * main controller
 * is the link between view and services
 */
public class Controller {

    @FXML private ImageView imageViewDoctor;
    @FXML private Tab tabPatients;
    @FXML private Tab tabCases;
    @FXML private TabPane tpMain;

    @FXML
    public void initialize() {

        //TODO tmp
        imageViewDoctor.setImage(new Image(Main.class.getResourceAsStream("drlucas.jpg")));

        //initialize the patient list
        tabPatients.setContent(new GCPatientList(this));

        //initialize the case list

        //TODO get cases from backend
        /*
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
        */

        //manage open tabs in main window

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

        ObservableList<Tab> mainTabList = tpMain.getTabs();
        mainTabList.add(new GCMainTab(this, mockCase));
    }
}