package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.event.EventHandler;

import java.awt.event.ActionEvent;

public class GCButtonAccept extends GCButton {

    public GCButtonAccept(Case aCase, EventHandler<javafx.event.ActionEvent> listener) {
        super("Accept", aCase);
        this.setOnAction(listener);
    }
}
