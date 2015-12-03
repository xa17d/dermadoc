package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import javafx.collections.FXCollections;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ListSelectionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 01.12.2015.
 */
public class GCMedicationList extends ListSelectionView<Medication> {

    public GCMedicationList() {
        super();

        //MOCK
        List<Medication> mediList = new ArrayList<>();
        mediList.add(new Medication("Medi 1"));
        mediList.add(new Medication("Medi 2"));
        mediList.add(new Medication("Medi 3"));
        mediList.add(new Medication("Medi 4"));
        mediList.add(new Medication("Medi 5"));
        //----

        this.setSourceItems(FXCollections.observableArrayList(mediList));
    }

    public List<Medication> getMedication() {
        return this.getTargetItems();
    }
}
