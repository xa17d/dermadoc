package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseDataList;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDao;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDataDao;
import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.security.Access;
import at.tuwien.telemedizin.dermadoc.server.security.AccessUser;
import at.tuwien.telemedizin.dermadoc.server.security.CurrentUser;
import at.tuwien.telemedizin.dermadoc.server.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;

/**
 * Created by daniel on 30.11.2015.
 */
@RestController
public class CaseDataController {

    @Autowired
    private CaseDataDao caseDataDao;

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private NotificationService notificationService;

    private void checkAccess(User user, Case theCase) {
        if (Access.canAccess(user, theCase)) {
            return;
        }
        else {
            throw new EntityNotFoundException("user has no access");
        }
    }

    private void prepareInsert(User user, CaseData caseData) {
        caseData.setAuthor(user);
        caseData.setCreated(GregorianCalendar.getInstance());
    }

    @RequestMapping(value = "/cases/{caseId}/data", method = RequestMethod.GET)
    @AccessUser
    public CaseDataList getCaseData(@CurrentUser User user, @PathVariable long caseId) {
        Case c = caseDao.getCaseById(caseId);
        checkAccess(user, c);
        return new CaseDataList(caseDataDao.listCaseDataByUserAndCase(caseId, user.getId()));
    }

    @RequestMapping(value = "/cases/{caseId}/data", method = RequestMethod.POST)
    @AccessUser
    public CaseData insertCaseData(@CurrentUser User user, @PathVariable long caseId, @RequestBody CaseData caseData) {
        Case c = caseDao.getCaseById(caseId);
        checkAccess(user, c);
        prepareInsert(user, caseData);
        caseDataDao.insert(caseId, caseData);

        // send notification
        notificationService.notifyCase(c, user, user.getName()+" posted in \""+c.getName()+"\"");

        return caseData;
    }

}
