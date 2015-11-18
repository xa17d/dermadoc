package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created by Lucas on 18.11.2015.
 */
public class GCTextMessageView extends GCCaseDataView {

    @FXML private Label lbMessage;

    private TextMessage message;

    public GCTextMessageView(TextMessage message) {

        this.message = message;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_textmessage.fxml"));
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

        String msg = message.getMessage();
        for(int i = 0; i < 3; i++) {
            msg += msg;
        }
        lbMessage.setText(msg);
    }

    @Override
    public boolean byPhysician() {
        return (message.getAuthor() instanceof Physician);
    }
}
