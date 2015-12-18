package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCTextMessageEdit;
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

    private TextMessage data;

    public GCTextMessageView(CaseData caseData) {

        if(caseData instanceof TextMessage) {
            this.data = (TextMessage) caseData;
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

        this.data = gcTextMessage.getTextMessage();

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
        lbMessage.setText(data.getMessage());
    }

    @Override
    public boolean byPhysician() {
        return (data.getAuthor() instanceof Physician);
    }

    @Override
    public boolean isObsolete() {
        return false;
    }

    @Override
    public void expand(boolean expand) {
        //do nothing
    }

    @Override
    public CaseData getCaseData() {
        return data;
    }
}
