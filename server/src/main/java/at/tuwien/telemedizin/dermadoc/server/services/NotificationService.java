package at.tuwien.telemedizin.dermadoc.server.services;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.NotificationRepository;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daniel on 01.12.2015.
 */
@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PhysicianService physicianService;

    public void notifyCase(Case theCase, User sender, String message) {

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
            //notificationDao.insert(notification);
            notificationRepository.save(notification);

            // TODO: send out Android notification?
        }
    }

    public void notifyNewCase(Case newCase) {

        if (CaseStatus.LookingForPhysician.equals(newCase.getStatus())) {
            // Do not notify if no physician is selected
            /*
            for (Physician physician : physicianService.listNearestPhysicians(newCase.getPatient().getLocation())) {

                Notification notification = new Notification();
                notification.setCaseId(newCase.getId());
                notification.setUserId(physician.getId());
                notification.setText(newCase.getPatient().getName() + " created a new case: \"" + newCase.getName()+"\"");

                // add to DAO
                notificationRepository.save(notification);
            }
            */
        }
        else {
            // if the Patient is not looking for a Physician for the case,
            // a Physician must be already assigned
            if (newCase.getPhysician() == null)
            { throw new IllegalArgumentException("case status must be LookingForPhysician or a Physician must be assigned"); }

            Notification notification = new Notification();
            notification.setCaseId(newCase.getId());
            notification.setUserId(newCase.getPhysician().getId());
            notification.setText(
                    newCase.getPatient().getName() +
                    " created a new case: \"" + newCase.getName()+"\""+
                    " and wants you to treat " + (newCase.getPatient().getGender() == Gender.Female ? "her" : "him")
            );

            // add to DAO
            notificationRepository.save(notification);
        }
    }
}
