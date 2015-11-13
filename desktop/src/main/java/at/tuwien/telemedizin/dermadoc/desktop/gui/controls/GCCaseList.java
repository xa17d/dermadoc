package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

/**
 * Created by Lucas on 13.11.2015.
 */
public class GCCaseList extends ScrollPane {

    @FXML private ListView<Case> lvCases;

    private Controller controller;
    private ObservableList<Case> cases;

    public GCCaseList(Controller controller, ObservableList<Case> cases) {

        this.controller = controller;
        this.cases = cases;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_caselist.fxml"));
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

        lvCases.setItems(cases);
    }
}
