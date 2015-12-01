package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Lucas on 17.11.2015.
 */
public class GCTextMessageEdit extends AGCCaseDataEdit {

    @FXML private GridPane gpCaseData;
    @FXML private TextArea taTextMessage;

    private Controller controller;
    private Case aCase;
    private CaseDataEventHandler saveEventHandler;

    public GCTextMessageEdit(Controller controller, Case aCase) {

        this.controller = controller;
        this.aCase = aCase;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_textmessage_edit.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

        this.initStyle(gpCaseData);
    }

    @FXML
    private void save() {

        //send to backend
        textMessage = new TextMessage(-1l, Calendar.getInstance(), controller.getPhysician(), taTextMessage.getText());
        controller.saveCaseData(aCase, textMessage);

        //notify list
        if(saveEventHandler != null) {
            saveEventHandler.onEvent(textMessage);
        }
    }

    private TextMessage textMessage;
    public TextMessage getTextMessage() {
        return textMessage;
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