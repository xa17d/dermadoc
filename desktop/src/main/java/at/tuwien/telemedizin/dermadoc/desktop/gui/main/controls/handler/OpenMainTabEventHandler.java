package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.buttons.GCButton;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.buttons.GCButtonAccept;
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
            controller.getOpenCaseList().remove(button.getCase());
            controller.getPatientCaseMap().put(button.getCase());
        }

        controller.openMainTab(button.getCase());
    }
}
