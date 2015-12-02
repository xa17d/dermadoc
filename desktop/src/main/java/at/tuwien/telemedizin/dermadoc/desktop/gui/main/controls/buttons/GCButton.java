package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.buttons;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.scene.control.Button;

public class GCButton extends Button {

    private Case aCase;

    public GCButton(String text, Case aCase) {
        super(text);

        this.aCase = aCase;
    }

    public Case getCase() {
        return aCase;
    }
}
