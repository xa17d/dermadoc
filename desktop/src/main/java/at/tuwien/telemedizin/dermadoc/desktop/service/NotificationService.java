package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.DermadocNotificationHandler;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.IRestCaseService;
import at.tuwien.telemedizin.dermadoc.service.rest.RestCaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by Lucas on 03.12.2015.
 */
public class NotificationService implements INotificationService {

    private IRestCaseService rest;
    private PatientCaseMap patientCaseMap;

    private ObservableList<Notification> notificationList = FXCollections.observableArrayList();

    public NotificationService(AuthenticationToken token, PatientCaseMap patientCaseMap) {

        rest = new RestCaseService(token);
        this.patientCaseMap = patientCaseMap;

        startPollingNotifications();
    }

    @Override
    public ObservableList<Notification> getNotificationList() throws DermadocException {
        return notificationList;
    }


    private void startPollingNotifications() {

        new Thread(new NotificationPoller(notificationList, new DermadocNotificationHandler() {
            @Override
            public void onNewNotifications(List<Notification> notifications) {

            }
        })).start();
    }

    private class NotificationPoller implements Runnable {

        private ObservableList<Notification> notificationList;
        private DermadocNotificationHandler handler;

        public NotificationPoller(ObservableList<Notification> notificationList, DermadocNotificationHandler handler) {

            this.notificationList = notificationList;
            this.handler = handler;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                rest.getNotifications();
            }
        }
    }
}
