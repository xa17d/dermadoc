package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.view.GCTextMessageView;
import at.tuwien.telemedizin.dermadoc.desktop.service.CaseDataEventHandler;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Lucas on 17.11.2015.
 */
public class GCTextMessageEdit extends GCCaseDataEdit {

    @FXML private TextArea taTextMessage;

    private Controller controller;
    private CaseDataEventHandler saveEventHandler;

    public GCTextMessageEdit(Controller controller, VBox list) {

        this.controller = controller;

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

        this.initStyle();

        //MOCK
        taTextMessage.setText("here the doctor can write his new Text Message");
    }

    @FXML
    private void save() {

        //send to backend
        //TODO send to backend
        //TODO get id and physician
        textMessage = new TextMessage(-1l, Calendar.getInstance(), new Physician(), taTextMessage.getText());

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
