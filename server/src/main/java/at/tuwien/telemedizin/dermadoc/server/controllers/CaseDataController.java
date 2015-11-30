package at.tuwien.telemedizin.dermadoc.server.controllers;

import at.tuwien.telemedizin.dermadoc.entities.*;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseDataList;
import at.tuwien.telemedizin.dermadoc.entities.rest.CaseList;
import at.tuwien.telemedizin.dermadoc.server.exceptions.InvalidCaseStatusException;
import at.tuwien.telemedizin.dermadoc.server.exceptions.InvalidUserTypeException;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDao;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDataDao;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.EntityNotFoundException;
import at.tuwien.telemedizin.dermadoc.server.security.Access;
import at.tuwien.telemedizin.dermadoc.server.security.AccessPhysician;
import at.tuwien.telemedizin.dermadoc.server.security.AccessUser;
import at.tuwien.telemedizin.dermadoc.server.security.CurrentUser;
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

    private void checkAccess(User user, long caseId) {
        Case c = caseDao.getCaseById(caseId);
        if (Access.canAccess(user, c)) {
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
        checkAccess(user, caseId);
        return new CaseDataList(caseDataDao.listCaseDataByUserAndCase(caseId, user.getId()));
    }

    @RequestMapping(value = "/cases/{caseId}/data/text", method = RequestMethod.POST)
    @AccessUser
    public TextMessage insertCaseDataText(@CurrentUser User user, @PathVariable long caseId, @RequestBody TextMessage textMessage) {
        checkAccess(user, caseId);
        prepareInsert(user, textMessage);
        caseDataDao.insert(caseId, textMessage);
        return textMessage;
    }

}
