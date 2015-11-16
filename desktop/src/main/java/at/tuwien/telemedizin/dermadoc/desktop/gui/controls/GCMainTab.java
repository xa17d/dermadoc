package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Lucas on 14.11.2015.
 */
public class GCMainTab extends Tab {

    @FXML private Pane pnInput;
    @FXML private TitledPane tpPatientOverview;

    private Controller controller;
    private Case aCase;

    public GCMainTab(Controller controller, Case aCase) {

        this.controller = controller;
        this.aCase = aCase;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_maintab.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {

        this.setText(aCase.getPatient().getName());
        tpPatientOverview.setContent(new GCMainTabOverview(aCase.getPatient()));

        //Chat window
        GridPane chat = new GridPane();
        chat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        chat.getColumnConstraints().add(c1);

        for (int i = 0; i < 20; i++) {
            Label chatMessage = new Label("Hi " + i);
            String style = (i % 2 == 0 ? "chat-bubblepatient" : "chat-bubblephysician");
            chatMessage.getStyleClass().add(style);
            GridPane.setHalignment(chatMessage, i % 2 == 0 ? HPos.LEFT : HPos.RIGHT);
            chat.addRow(i, chatMessage);
        }

        ScrollPane scroll = new ScrollPane(chat);
        scroll.setFitToWidth(true);
        pnInput.getChildren().add(scroll);

        scroll.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());
    }

    @FXML
    private void newFreetext() {
        //pnInput.getChildren().add(new Label("new freetext"));
    }
}
