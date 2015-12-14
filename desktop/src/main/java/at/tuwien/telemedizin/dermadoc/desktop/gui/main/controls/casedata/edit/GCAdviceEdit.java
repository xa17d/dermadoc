package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists.GCMedicationList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Advice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Lucas on 01.12.2015.
 */
public class GCAdviceEdit  extends AGCCaseDataEdit {

    @FXML private GridPane gpCaseData;
    @FXML private TextArea taAdvice;

    private Controller controller;
    private Case aCase;
    private CaseDataEventHandler saveEventHandler;

    private GCMedicationList gcMedicationList;

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

        //add medication list
        gcMedicationList = new GCMedicationList();
        gpCaseData.add(gcMedicationList, 0, 1);

        gcMedicationList.getStyleClass().add(this.getCaseStyle());
    }

    @FXML
    private void initialize() {

        this.initStyle(gpCaseData);
    }

    @FXML
    private void save() {

        //send to backend
        advice = new Advice(-1l, Calendar.getInstance(), controller.getPhysician(), taAdvice.getText(), gcMedicationList.getMedication());
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