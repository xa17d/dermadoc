package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.error;

import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.NotificationPane;

/**
 * Created by Lucas on 17.11.2015.
 */
public class ErrorPane extends NotificationPane {

    private BorderPane borderPane;

    public ErrorPane(BorderPane borderPane, String message) {

        super(new WebView());

        borderPane.setBottom(this);

        this.setText(message);
        this.setMinHeight(0);

        this.setShowFromTop(false);
        this.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
    }

    @Override
    public void show() {
        super.show();

        this.setPrefHeight(40);
    }

    @Override
    public void hide() {
        super.hide();

        this.setPrefHeight(0);
    }
}
