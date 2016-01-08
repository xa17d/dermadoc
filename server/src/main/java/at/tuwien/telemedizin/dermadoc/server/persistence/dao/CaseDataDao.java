package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

/**
 * Created by daniel on 30.11.2015.
 */
public interface CaseDataDao {

    //List<CaseData> listCaseDataByUserAndCase(long caseId, long userId);

    void insert(CaseData caseData);
}
