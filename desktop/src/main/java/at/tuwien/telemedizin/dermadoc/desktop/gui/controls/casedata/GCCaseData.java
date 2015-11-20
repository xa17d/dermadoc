package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Created by Lucas on 17.11.2015.
 */
public abstract class GCCaseData extends GridPane {

    protected static final String STYLE_PATIENT = "chat-bubblepatient";
    protected static final String STYLE_PHYSICIAN = "chat-bubblephysician";

    public abstract boolean byPhysician();

    final public void initStyle() {
        this.getStyleClass().add(this.getCaseStyle());
        this.setAlignment(getPos());
    }

    final public Pos getPos() {
        return byPhysician() ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT;
    }

    final public String getCaseStyle() {
        return byPhysician() ? STYLE_PHYSICIAN : STYLE_PATIENT;
    }
}
