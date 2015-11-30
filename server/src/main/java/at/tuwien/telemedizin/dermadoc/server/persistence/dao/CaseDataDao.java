package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.casedata.*;

import java.util.List;

/**
 * Created by daniel on 30.11.2015.
 */
public interface CaseDataDao {

    List<CaseData> listCaseDataByUserAndCase(long caseId, long userId);

    void insert(long caseId, Advice advice);
    void insert(long caseId, Anamnesis anamnesis);
    void insert(long caseId, CaseInfo caseInfo);
    void insert(long caseId, Diagnosis diagnosis);
    void insert(long caseId, PhotoMessage photoMessage);
    void insert(long caseId, TextMessage textMessage);
}
