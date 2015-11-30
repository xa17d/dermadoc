package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.buttons;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.event.EventHandler;

public class GCButtonAccept extends GCButton {

    public GCButtonAccept(Case aCase, EventHandler<javafx.event.ActionEvent> listener) {
        super("Accept", aCase);

        if(listener != null) {
            this.setOnAction(listener);
        }
    }
}
