package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata;

import at.tuwien.telemedizin.dermadoc.entities.casedata.Advice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;

import java.io.IOException;

/**
 * Created by Lucas on 17.11.2015.
 */
public class GCAdvice extends GCCaseData {

    @FXML private TextArea taAdvice;

    private Advice advice;

    public GCAdvice(Advice advice) {

        this.advice = advice;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_advice.fxml"));
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

        this.getStyleClass().add(STYLE_PHYSICIAN);

        //MOCK
        taAdvice.setText("this is an Advice of your doctor!!");
    }

    @FXML
    private void save() {
        //TODO
    }

    @Override
    public boolean byPhysician() {
        return true;
    }
}
