package at.tuwien.telemedizin.dermadoc.app.server_interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.CaseParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.PhysicianParc;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.CaseStatus;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Notification;
import at.tuwien.telemedizin.dermadoc.app.general_entities.Physician;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;
import at.tuwien.telemedizin.dermadoc.app.general_entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.app.general_entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.app.helper.ParcelableHelper;
import at.tuwien.telemedizin.dermadoc.app.persistence.TestContentProvider;

/**
 * Created by FAUser on 08.01.2016.
 */
public class TestServerInterface implements ServerInterface {

    private TestContentProvider cP = new TestContentProvider();

    private List<Case> caseList;
    private Map<Long, List<CaseData>> caseDataMap; // case Id To List<CaseData>
    private List<Notification> notificationList; // case To List<Notification>
    private List<Physician> physicianList;

    private long caseDataHighestId = 10;

    public TestServerInterface() {
        initializeData();

    }

    private void initializeData() {
        List<CaseParc> caseParcs = cP.getCurrentCasesOfUser();
        List<Case> caseList = new ArrayList<>();
        for (CaseParc c : caseParcs) {
            caseList.add(ParcelableHelper.mapToCase(c));
        }
        this.caseList = caseList;

        this.physicianList = createPhysicianList();

        caseDataMap = new HashMap<Long, List<CaseData>>();
        fillCaseDataMap();

        notificationList = new ArrayList<>();
        addToNotificationList();


    }

    private void fillCaseDataMap() {
        List<CaseParc> cList = cP.getCurrentCasesOfUser();
        for (CaseParc caseParc : cList) {
            List<CaseData> caseCD = ParcelableHelper.mapToCaseDataList(caseParc.getDataElements());
            caseDataMap.put(caseParc.getId(), caseCD);
        }

    }

    private synchronized long getNotificationId() {
        if (notificationList.size() == 0) {
            return 0;
        } else {
            long highestIdInNotificationList = notificationList.get(notificationList.size()-1).getId();
            return highestIdInNotificationList+1;
        }

    }

    private synchronized long getCaseId() {
        if (caseList.size() == 0) {
            return 0;
        } else {
            long highestId = caseList.get(caseList.size()-1).getId();
            return highestId+1;
        }

    }

    private synchronized long getCaseDataId() {
        return caseDataHighestId++;

    }

    private void addToNotificationList() {

        for (Case c : caseList) {

//            // add notifications?
//            Random randomN = new Random();
//            int randomNbN = randomN.nextInt(10);
//            if (randomNbN <= 6) {
//                continue;
//            }
            // how many should be added
//            Random random = new Random();
//            int randomNb = random.nextInt(3);

//            for (int i=0; i < randomNb; i++) {
            Notification n2 = new Notification();

            n2.setId(getNotificationId());
            n2.setCaseId(c.getId());
            n2.setText("Dr. Albert Hofstätter hat Ihren Fall angenommen.");
            notificationList.add(n2);


                Notification n1 = new Notification();

                n1.setId(getNotificationId());
                n1.setCaseId(c.getId());
                n1.setText("Dr. Albert Hofstätter hat Ihnen eine Nachricht geschickt.");
                notificationList.add(n1);
//            }
        }

    }

    private List<Physician> createPhysicianList() {
        Physician a = new Physician(); // TODO remove
        a.setId(0L);
        a.setName("Dr. Albert Hofstätter");

        Physician b = new Physician();
        b.setId(1L);
        b.setName("Dr. Berta Müller");

        Physician c = new Physician();
        c.setId(2L);
        c.setName("Dr. Charlie Sieber");

        List<Physician> list = new ArrayList<Physician>();
        list.add(a);
        list.add(b);
        list.add(c);
        return list;
    }

    @Override
    public boolean login(AuthenticationData authenticationData) {
        return true;
    }

    @Override
    public User getUser() {
        return ParcelableHelper.mapToUser(cP.getCurrentUser());
    }

    @Override
    public List<Case> getCases() {

        return caseList;
    }

    @Override
    public Case getCase(long id) {
        List<CaseParc> caseParcs = cP.getCurrentCasesOfUser();
        for (CaseParc c : caseParcs) {
            if (c.getId() == id) {
                return ParcelableHelper.mapToCase(c);
            }
        }
        return null;
    }

    @Override
    public List<CaseData> getCaseData(long caseId) {
        List<Case> cases = caseList;
        for (Case c : cases) {
            if (c.getId() == caseId) {
                return caseDataMap.get(c.getId());
            }
        }
        return null;
    }

    @Override
    public Case createCase(Case caseItem) {
        caseItem.setId(getCaseId());
        if (caseItem.getPhysician() == null) {
            caseItem.setStatus(CaseStatus.LookingForPhysician);
        } else{
            caseItem.setStatus(CaseStatus.WaitingForAccept);
        }

        caseList.add(caseItem);
        return caseItem;
    }

    @Override
    public CaseData addCaseData(CaseData caseData, long caseId) {
        caseData.setId(getCaseDataId());
        List<CaseData> cDList = caseDataMap.get(caseId);
        if (cDList == null) {
            cDList = new ArrayList<>();
        }
        cDList.add(caseData);
        caseDataMap.put(caseId, cDList);
        return caseData;
    }

    @Override
    public List<Physician> getPhysicians() {

        return physicianList;
//        return ParcelableHelper.mapPhysicianParcList(cP.getNearbyPhysicians(null));
    }

    @Override
    public List<Notification> getNotifications() {

        // randomly more notifications
//        Random random = new Random();
//        int randomNb = random.nextInt(10);
//        if (randomNb > 7) {
//            addToNotificationList();
//        }

        return notificationList;
    }

    @Override
    public Boolean deleteNotification(long notificationId) {

        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getId() == notificationId) {
                notificationList.remove(i);
                return true;
            }
        }
        return false;
    }
}
