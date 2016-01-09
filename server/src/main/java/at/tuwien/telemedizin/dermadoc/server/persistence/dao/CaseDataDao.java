package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

import java.util.List;

/**
 * Created by daniel on 30.11.2015.
 */
public interface CaseDataDao {

    Iterable<CaseData> listCaseDataByUserAndCase(long caseId, long userId);

    CaseData insert(CaseData caseData);
}
