package at.tuwien.telemedizin.dermadoc.desktop.gui.controls;

import at.tuwien.telemedizin.dermadoc.entities.Notification;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Lucas on 18.11.2015.
 */
public class GCNotification extends AnchorPane {

    @FXML private ListView<Notification> lvNotifications;

    private ObservableList<Notification> notificationList;

    public GCNotification(ObservableList<Notification> notificationList) {

        this.notificationList = notificationList;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gc_notification.fxml"));
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
        lvNotifications.setItems(notificationList);
    }
}
