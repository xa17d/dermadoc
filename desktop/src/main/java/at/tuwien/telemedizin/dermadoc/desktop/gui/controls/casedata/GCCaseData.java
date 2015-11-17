package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata;

import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;

/**
 * Created by Lucas on 17.11.2015.
 */
public abstract class GCCaseData extends GridPane {

    protected static final String STYLE_PATIENT = "chat-bubblepatient";
    protected static final String STYLE_PHYSICIAN = "chat-bubblephysician";

    public HPos getHPos() {
        return byPhysician() ? HPos.RIGHT : HPos.LEFT;
    }

    public abstract boolean byPhysician();
}
