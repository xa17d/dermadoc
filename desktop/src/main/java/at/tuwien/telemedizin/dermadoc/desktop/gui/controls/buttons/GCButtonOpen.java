package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GCButtonOpen extends GCButton {

    public GCButtonOpen(Case aCase, EventHandler<ActionEvent> listener) {
        super("Open", aCase);
        this.setOnAction(listener);
    }
}
