package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.Controller;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.PopOver;

import java.io.IOException;

/**
 * Created by Lucas on 18.11.2015.
 */
public class GCNotification extends AnchorPane {

    @FXML private ListView<Notification> lvNotifications;

    private Controller controller;
    private ObservableList<Notification> notificationList;

    public GCNotification(Controller controller, ObservableList<Notification> notificationList) {

        this.controller = controller;
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

        lvNotifications.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Notification>() {
            @Override
            public void changed(ObservableValue<? extends Notification> observable, Notification oldValue, Notification newValue) {

                controller.openMainTab(controller.getCaseById(observable.getValue().getCaseId()));
                popOver.hide();
            }
        });

        updateList();
    }

    public void updateList() {
        lvNotifications.setItems(notificationList);
    }

    private PopOver popOver;
    public void setPopOver(PopOver popOver) {
        this.popOver = popOver;
    }
}
