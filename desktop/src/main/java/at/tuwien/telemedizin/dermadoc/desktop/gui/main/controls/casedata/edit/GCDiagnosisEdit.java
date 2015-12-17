package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists.GCDiagnosisList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Diagnosis;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Lucas on 01.12.2015.
 */
public class GCDiagnosisEdit extends AGCCaseDataEdit {

    private static final int ROW_HEIGHT = 24;

    @FXML private GridPane gpCaseData;
    @FXML private TextArea taDiagnosis;
    @FXML private Label lbDiagnosis;

    private Controller controller;
    private Case aCase;
    private CaseDataEventHandler saveEventHandler;

    private GCDiagnosisList gcDiagnosisList;
    private ObservableList<Icd10Diagnosis> diagnosisList;

    public GCDiagnosisEdit(Controller controller, Case aCase) {

        this.controller = controller;
        this.aCase = aCase;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_diagnosis_edit.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        diagnosisList = FXCollections.observableArrayList();

        diagnosisList.addListener(new ListChangeListener<Icd10Diagnosis>() {
            @Override
            public void onChanged(Change<? extends Icd10Diagnosis> c) {

                StringBuilder sb = new StringBuilder();
                for(Icd10Diagnosis m : diagnosisList) {
                    sb.append(m.toString() + ", ");
                }

                lbDiagnosis.setText(sb.toString());
                lbDiagnosis.setPrefHeight((diagnosisList.size()+1) * ROW_HEIGHT + 2);
            }
        });

        //show medication list
        gcDiagnosisList = new GCDiagnosisList(diagnosisList);
        gcDiagnosisList.getStyleClass().add(this.getCaseStyle());

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(controller.getStage());
        Scene scene = new Scene(gcDiagnosisList, 300, 400);
        scene.getStylesheets().add(DesktopApplication.class.getResource("main.css").toExternalForm());
        dialog.setScene(scene);
        dialog.show();

        lbDiagnosis.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.show();
            }
        });
    }

    @FXML
    private void initialize() {
        this.initStyle(gpCaseData);
    }

    @FXML
    private void save() {

        //send to backend
        diagnosis = new Diagnosis(-1l, Calendar.getInstance(), controller.getPhysician(), taDiagnosis.getText(), diagnosisList);
        controller.saveCaseData(aCase, diagnosis);

        //notify list
        if(saveEventHandler != null) {
            saveEventHandler.onEvent(diagnosis);
        }
    }

    private Diagnosis diagnosis;
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    @Override
    public boolean byPhysician() {
        return true;
    }

    @Override
    public void setSaveEventHandler(CaseDataEventHandler caseDataEventHandler) {
        saveEventHandler = caseDataEventHandler;
    }
}
