package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.DesktopApplication;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.MainStage;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.PopOver;

import java.io.IOException;

/**
 * Created by Lucas on 18.11.2015.
 */
public class GCPhysician extends GridPane {

    @FXML private Label lbName;
    @FXML private Label lbAdditionalInfo;
    @FXML private ImageView ivDoctor;
    @FXML private Button btNotification;

    private Controller controller;
    private Physician physician;

    private PopOver popOverNotification;
    private GCNotification gcNotification;

    public GCPhysician(Controller controller, Physician physician, ObservableList<Notification> notificationList) {

        this.controller = controller;
        this.physician = physician;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_physician.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gcNotification = new GCNotification(controller, notificationList);
        popOverNotification = new PopOver(gcNotification);
        popOverNotification.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOverNotification.setMinWidth(250);
        gcNotification.setPopOver(popOverNotification);

        notificationList.addListener(new ListChangeListener<Notification>() {
            @Override
            public void onChanged(Change<? extends Notification> c) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        btNotification.setText(String.valueOf(notificationList.size()));
                        gcNotification.updateList();
                    }
                });
            }
        });
    }

    @FXML
    private void initialize() {

        lbName.setText(physician.getName());
        lbAdditionalInfo.setText(physician.getMail());
        ivDoctor.setImage(new Image(DesktopApplication.class.getResourceAsStream("dracula.png")));

        this.getStylesheets().add(getClass().getResource("notification.css").toExternalForm());
        btNotification.getStyleClass().add("notification-button");
    }

    @FXML
    private void openNotificationPane() {

        if(popOverNotification.isShowing()) {
            popOverNotification.hide();
        }
        else{
            popOverNotification.show(btNotification);
        }
    }

    @FXML
    private void lock() {
        controller.lock();
    }
}
