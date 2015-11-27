package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

import at.tuwien.telemedizin.dermadoc.entities.Case;

import java.util.Collection;
import java.util.List;

/**
 * Created by daniel on 27.11.2015.
 */
public interface CaseDao {

    List<Case> listByPatient(long patientId);
    List<Case> listByPhysician(long patientId);
    List<Case> listOpenCases();
    void insert(Case newCase);
    Case getCaseById(long caseId);
    void update(Case updatedCase);
}
