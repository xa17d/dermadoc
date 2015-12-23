package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists;

import at.tuwien.telemedizin.dermadoc.entities.Icd10Diagnosis;
import at.tuwien.telemedizin.dermadoc.service.data.Icd10List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckTreeView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lucas on 17.12.2015.
 */
public class GCDiagnosisList extends BorderPane {

    @FXML private AnchorPane apTree;
    @FXML private Button btClose;

    public GCDiagnosisList(ObservableList<Icd10Diagnosis> selectedList) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_diagnosislist.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Icd10Diagnosis> list = Icd10List.getAll();
        int[] level = Icd10List.getLevels();

        CheckBoxTreeItem<Icd10Diagnosis> root = new CheckBoxTreeItem<>(list.get(0));
        root.setExpanded(true);
        root.setIndependent(true);

        CheckBoxTreeItem<Icd10Diagnosis> lvl1 = null;
        CheckBoxTreeItem<Icd10Diagnosis> lvl2 = null;
        CheckBoxTreeItem<Icd10Diagnosis> lvl3 = null;
        for(int i = 1; i < level.length; i++) {

            if(level[i] == 1) {
                lvl1 = new CheckBoxTreeItem<>(list.get(i));
                lvl1.setExpanded(false);
                lvl1.setIndependent(true);
                root.getChildren().add(lvl1);
            }

            if(level[i] == 2) {
                lvl2 = new CheckBoxTreeItem<>(list.get(i));
                lvl2.setExpanded(false);
                lvl2.setIndependent(true);
                lvl1.getChildren().add(lvl2);
            }

            if(level[i] == 3) {
                lvl3 = new CheckBoxTreeItem<>(list.get(i));
                lvl3.setExpanded(false);
                lvl3.setIndependent(true);
                lvl2.getChildren().add(lvl3);
            }
        }

        final CheckTreeView<Icd10Diagnosis> checkTreeView = new CheckTreeView<>(root);

        checkTreeView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<TreeItem<Icd10Diagnosis>>() {
            public void onChanged(ListChangeListener.Change<? extends TreeItem<Icd10Diagnosis>> c) {

                while(c.next()) {
                    for (TreeItem<Icd10Diagnosis> item : c.getAddedSubList()) {
                        selectedList.add(item.getValue());
                    }

                    for (TreeItem<Icd10Diagnosis> item : c.getRemoved()) {
                        selectedList.remove(item.getValue());
                    }
                }
            }
        });

        AnchorPane.setBottomAnchor(checkTreeView, 0.0);
        AnchorPane.setLeftAnchor(checkTreeView, 0.0);
        AnchorPane.setRightAnchor(checkTreeView, 0.0);
        AnchorPane.setTopAnchor(checkTreeView, 0.0);
        apTree.getChildren().add(checkTreeView);
    }

    @FXML
    private void close() {
        ((Stage) btClose.getScene().getWindow()).close();
    }
}
