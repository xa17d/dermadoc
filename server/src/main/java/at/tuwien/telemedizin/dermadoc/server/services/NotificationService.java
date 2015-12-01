package at.tuwien.telemedizin.dermadoc.server.services;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.NotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daniel on 01.12.2015.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public void notifyUser(Case theCase, User sender, String message) {

        Notification notification = new Notification();
        notification.setCaseId(theCase.getId());
        notification.setText(message);

        User receiver;
        if (sender.equals(theCase.getPatient())) {
            receiver = theCase.getPhysician();
        }
        else if (sender.equals(theCase.getPhysician())) {
            receiver = theCase.getPatient();
        }
        else {
            // must not happen
            throw new IllegalArgumentException("sender must be either the Physician or the Patient of the Case");
        }

        if (receiver == null) {
            // happens if e.g. the user sends new CaseData and
            // there is no Physician assigned to the Case yet
            // -> do nothing
        }
        else {
            // set receiver
            notification.setUserId(receiver.getId());

            // add to DAO
            notificationDao.insert(notification);

            // TODO: send out Android notification?
        }
    }
}
