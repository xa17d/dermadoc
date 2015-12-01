package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.GCTextMessageEdit;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;
import at.tuwien.telemedizin.dermadoc.entities.casedata.Diagnosis;
import at.tuwien.telemedizin.dermadoc.entities.casedata.PhotoMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * view of a diagnosis
 */
public class GCPhotoMessageView extends AGCCaseDataView {

    @FXML private GridPane gpCaseData;

    private PhotoMessage data;

    public GCPhotoMessageView(CaseData caseData) {

        if(caseData instanceof PhotoMessage) {
            this.data = (PhotoMessage) caseData;
        }
        else {
            throw new IllegalArgumentException("case data must be a case info!");
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

    public GCPhotoMessageView(GCTextMessageEdit gcTextMessage) {

        /*TODO
        this.data = gcTextMessage.getTextMessage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_textmessage_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @FXML
    private void initialize() {

        this.initStyle(gpCaseData);
        //TODO
    }

    @Override
    public boolean byPhysician() {
        return (data.getAuthor() instanceof Physician);
    }

    @Override
    public void expand(boolean expand) {
        //TODO
    }
}