package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons.GCButton;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons.GCButtonAccept;
import javafx.event.EventHandler;

public class OpenMainTabEventHandler implements EventHandler<javafx.event.ActionEvent> {

    private Controller controller;

    public OpenMainTabEventHandler(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(javafx.event.ActionEvent event) {

        Object source = event.getSource();
        if(!(source instanceof GCButton)) {
            throw new IllegalArgumentException("OpenMainTabActionListeners are only allowed on GC Buttons");
        }

        GCButton button = (GCButton) source;

        if(button instanceof GCButtonAccept) {
            controller.acceptCase(button.getCase());
        }

        controller.openMainTab(button.getCase());
    }
}
