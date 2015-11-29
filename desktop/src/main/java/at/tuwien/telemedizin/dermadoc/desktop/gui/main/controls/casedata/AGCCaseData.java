package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by Lucas on 17.11.2015.
 */
public abstract class AGCCaseData extends HBox {

    protected static final String STYLE_PATIENT = "chat-bubblepatient";
    protected static final String STYLE_PHYSICIAN = "chat-bubblephysician";

    public abstract boolean byPhysician();

    final public void initStyle(GridPane gpCaseData) {
        gpCaseData.getStyleClass().add(this.getCaseStyle());
        this.setAlignment(getPos());
        HBox.setMargin(gpCaseData, new Insets(5, 5, 15, 5));
    }

    final public Pos getPos() {
        return byPhysician() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT;
    }

    final public String getCaseStyle() {
        return byPhysician() ? STYLE_PHYSICIAN : STYLE_PATIENT;
    }
}
