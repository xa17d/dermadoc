package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by Lucas on 18.11.2015.
 */
public class GCTextMessageView extends AGCCaseDataView {

    @FXML private GridPane gpCaseData;
    @FXML private Label lbMessage;

    private TextMessage message;

    public GCTextMessageView(CaseData caseData) {

        if(caseData instanceof TextMessage) {
            this.message = (TextMessage) caseData;
        }
        else {
            throw new IllegalArgumentException("case data must be a text message!");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_textmessage_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GCTextMessageView(GCTextMessageEdit gcTextMessage) {

        this.message = gcTextMessage.getTextMessage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_textmessage_view.fxml"));
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
        lbMessage.setText(message.getMessage());
    }

    @Override
    public boolean byPhysician() {
        return (message.getAuthor() instanceof Physician);
    }
}
