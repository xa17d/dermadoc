package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.server.exceptions.InvalidCaseStatusException;
import at.tuwien.telemedizin.dermadoc.server.exceptions.InvalidUserTypeException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDao;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.security.*;
import at.tuwien.telemedizin.dermadoc.server.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;

/**
 * Created by daniel on 27.11.2015.
 */
@RestController
public class CaseController {

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/cases", method = RequestMethod.GET)
    @AccessUser
    public CaseList listCases(@CurrentUser User user) {

        if (user instanceof Patient) {
            return new CaseList(caseDao.listByPatient(user.getId()));
        }
        else if (user instanceof Physician) {
            return new CaseList(caseDao.listByPhysician(user.getId()));
        }
        else {
            throw new InvalidUserTypeException(user.getClass());
        }
    }

    @RequestMapping(value = "/cases/open", method = RequestMethod.GET)
    @AccessPhysician
    public CaseList listOpenCases()
    {
        return new CaseList(caseDao.listOpenCases());
    }

    @RequestMapping(value = "/cases/{caseId}", method = RequestMethod.GET)
    @AccessUser
    public Case listCases(@CurrentUser User user, @PathVariable long caseId) {

        Case c = caseDao.getCaseById(caseId);

        if (Access.canAccess(user, c)) {
            return c;
        }
        else {
            throw new EntityNotFoundException("user has no access");
        }
    }

    @RequestMapping(value = "/cases/{caseId}/accept", method = RequestMethod.POST)
    @AccessPhysician
    public Case acceptCase(@CurrentUser User user, @PathVariable long caseId) {

        Case c = caseDao.getCaseById(caseId);

        if (Access.canAccess(user, c)) {

            Physician physician = (Physician)user;
            if (c.getStatus() == CaseStatus.LookingForPhysician) {
                // update case
                c.setStatus(CaseStatus.Active);
                c.setPhysician(physician);
                caseDao.update(c);

                // send notification
                notificationService.notifyCase(c, user, user.getName()+" accepted your case");

                return c;
            }
            else {
                throw new InvalidCaseStatusException(c);
            }
        }
        else {
            throw new EntityNotFoundException("user has no access");
        }
    }

    @RequestMapping(value = "/cases", method = RequestMethod.POST)
    @AccessPatient
    public Case addCase(@CurrentUser User user, @RequestBody Case newCase)
    {
        Patient patient = (Patient)user;

        newCase.setPatient(patient); // just to be sure the user doesn't fake this property
        newCase.setCreated(GregorianCalendar.getInstance()); // set to now

        // set correct status
        if (newCase.getPhysician() == null) {
            newCase.setStatus(CaseStatus.LookingForPhysician);
        }
        else {
            newCase.setStatus(CaseStatus.WaitingForAccept);
        }

        // insert to db and get the id assigned
        caseDao.insert(newCase);

        // send notification
        notificationService.notifyNewCase(newCase);

        // return added case with the id
        return newCase;
    }
}
