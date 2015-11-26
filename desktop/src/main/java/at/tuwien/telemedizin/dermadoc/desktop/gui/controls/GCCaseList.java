package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.buttons.GCButtonAccept;
import at.tuwien.telemedizin.dermadoc.service.util.UtilFormat;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Gender;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

/**
 * Created by Lucas on 13.11.2015.
 */
public class GCCaseList extends ScrollPane {

    @FXML private TableView<Case> tvCases;
    @FXML private TableColumn<Case, String> nameColumn;
    @FXML private TableColumn<Case, Gender> genderColumn;
    @FXML private TableColumn<Case, String> caseNameColumn;
    @FXML private TableColumn<Case, String> dateColumn;
    @FXML private TableColumn<Case, Button> acceptColumn;

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

        tvCases.setItems(cases);
    }

    @FXML
    private void initialize() {

        //TODO refactor - this is not a very nice hack
        nameColumn.setCellValueFactory(cellData -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return cellData.getValue().getPatient().getName();
            }
        });

        genderColumn.setCellValueFactory(cellData -> new ObservableValueBase<Gender>() {
            @Override
            public Gender getValue() {
                return cellData.getValue().getPatient().getGender();
            }
        });

        caseNameColumn.setCellValueFactory(cellData -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return cellData.getValue().getStatus().toString();
            }
        });

        dateColumn.setCellValueFactory(cellData -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return UtilFormat.formatDate(cellData.getValue().getCreated());
            }
        });

        acceptColumn.setCellValueFactory(cellData -> new ObservableValueBase<Button>() {
            @Override
            public Button getValue() {
                return new GCButtonAccept(cellData.getValue(), controller.getOpenMainTabHandler());
            }
        });
    }
}
