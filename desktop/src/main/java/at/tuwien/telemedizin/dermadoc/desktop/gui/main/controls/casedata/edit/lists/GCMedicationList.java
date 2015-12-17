package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import at.tuwien.telemedizin.dermadoc.service.data.MedicationList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.ListSelectionView;

import java.util.List;

/**
 * Created by Lucas on 01.12.2015.
 */
public class GCMedicationList extends ListSelectionView<Medication> {

    public GCMedicationList(ObservableList<Medication> selectedList) {

        super();

        ObservableList<Medication> filteredList = FXCollections.observableArrayList();
        GCSearchBar<Medication> searchBar = new GCSearchBar<>(MedicationList.getAll(), selectedList, filteredList);

        this.setSourceHeader(searchBar);
        this.setTargetHeader(new Pane());

        Button btClose = new Button("close");
        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage) btClose.getScene().getWindow()).close();
            }
        });
        this.setTargetFooter(btClose);

        this.setSourceItems(filteredList);
        this.setTargetItems(selectedList);
    }
}
