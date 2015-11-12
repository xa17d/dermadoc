package at.tuwien.telemedizin.dermadoc.desktop.gui;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCPatientList;
import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.GCPatientListItem;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.InputStream;

public class MainController {

    @FXML private ImageView imageViewDoctor;
    @FXML private Tab tabPatients;

    @FXML
    public void initialize() {

        imageViewDoctor.setImage(new Image(Main.class.getResourceAsStream("drlucas.jpg")));

        GCPatientList patientList = new GCPatientList(this);
        tabPatients.setContent(patientList);
        patientList.updateList();
    }
}