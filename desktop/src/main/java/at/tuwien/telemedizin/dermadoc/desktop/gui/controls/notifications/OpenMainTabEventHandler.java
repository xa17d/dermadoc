package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.notifications;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons.GCButton;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
        controller.openMainTab(button.getCase());
    }
}
