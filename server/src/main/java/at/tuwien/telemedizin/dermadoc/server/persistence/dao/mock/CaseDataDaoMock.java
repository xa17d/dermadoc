package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.casedata.*;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDataDao;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by daniel on 30.11.2015.
 */
@Repository
public class CaseDataDaoMock implements CaseDataDao {

    public CaseDataDaoMock() {
        this.caseData = MockData.caseData;
    }

    private HashMap<Long, ArrayList<CaseData>> caseData;

    @Override
    public List<CaseData> listCaseDataByUserAndCase(long caseId, long userId) {
        // get List
        ArrayList<CaseData> caseDataList = caseData.getOrDefault(caseId, null);

        if (caseDataList == null) {
            return null;
        }
        else {
            ArrayList<CaseData> result = new ArrayList<>();

            // filter out private data from other users
            for (CaseData d : caseDataList) {
                if (d.getAuthor().getId() != userId && d.getPrivate()) {
                    // this CaseData belongs to another user and is set to private.
                    // So don't add it to the result list
                }
                else {
                    // CaseData belongs to the User or is not set to private
                    // So add it to the result list
                    result.add(d);
                }
            }

            return result;

        }
    }

    @Override
    public void insert(long caseId, CaseData caseData) {
        generalInsert(caseId, caseData);
    }

    private void generalInsert(long caseId, CaseData data) {
        // get List
        ArrayList<CaseData> caseDataList = caseData.getOrDefault(caseId, null);

        // if no List found, create one and add
        if (caseDataList == null) {
            caseDataList = new ArrayList<>();
            caseData.put(caseId, caseDataList);
        }

        // find highest id
        long id = 0;
        for (ArrayList<CaseData> iterateCaseDataList : caseData.values()) {
            for (CaseData d : iterateCaseDataList) {
                id = Math.max(d.getId(), id);
            }
        }

        // generate new id
        id++;
        data.setId(id);

        // add item to list
        caseDataList.add(data);
    }

}
