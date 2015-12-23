package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists.GCMedicationList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Advice;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
public class GCAdviceEdit extends AGCCaseDataEdit {

    private static final int ROW_HEIGHT = 24;

    @FXML private GridPane gpCaseData;
    @FXML private TextArea taAdvice;
    @FXML private Label lbMedication;

    private Controller controller;
    private Case aCase;
    private CaseDataEventHandler saveEventHandler;

    private GCMedicationList gcMedicationList;
    private ObservableList<Medication> medicationList;

    public GCAdviceEdit(Controller controller, Case aCase) {

        this.controller = controller;
        this.aCase = aCase;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_advice_edit.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        medicationList = FXCollections.observableArrayList();

        medicationList.addListener(new ListChangeListener<Medication>() {
            @Override
            public void onChanged(Change<? extends Medication> c) {

                StringBuilder sb = new StringBuilder();
                for(Medication m : medicationList) {
                    sb.append(m.toString() + ", ");
                }

                lbMedication.setText(sb.toString());
                lbMedication.setPrefHeight((medicationList.size()+1) * ROW_HEIGHT + 2);
            }
        });

        //show medication list
        gcMedicationList = new GCMedicationList(medicationList);
        gcMedicationList.getStyleClass().add(this.getCaseStyle());

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(controller.getStage());
        Scene scene = new Scene(gcMedicationList, 500, 450);
        scene.getStylesheets().add(DesktopApplication.class.getResource("main.css").toExternalForm());
        dialog.setScene(scene);
        dialog.setMinWidth(500);
        dialog.setMinHeight(450);
        dialog.show();

        lbMedication.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        advice = new Advice(-1l, Calendar.getInstance(), controller.getPhysician(), taAdvice.getText(), medicationList);
        controller.saveCaseData(aCase, advice);

        //notify list
        if(saveEventHandler != null) {
            saveEventHandler.onEvent(advice);
        }
    }

    private Advice advice;
    public Advice getAdvice() {
        return advice;
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