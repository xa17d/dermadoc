//package at.tuwien.telemedizin.dermadoc.server.persistence.dao.mock;
//
//import at.tuwien.telemedizin.dermadoc.entities.Case;
//import at.tuwien.telemedizin.dermadoc.entities.CaseStatus;
//import at.tuwien.telemedizin.dermadoc.server.persistence.dao.CaseDao;
//import at.tuwien.telemedizin.dermadoc.server.exceptions.EntityNotFoundException;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by daniel on 27.11.2015.
// */
//@Repository
//public class CaseDaoMock implements CaseDao {
//
//    public CaseDaoMock() {
//        this.cases = MockData.cases;
//    }
//
//    private ArrayList<Case> cases;
//
//    @Override
//    public List<Case> listByPatient(long patientId) {
//        ArrayList<Case> result = new ArrayList<>();
//
//        for (Case c : cases) {
//            if (c.getPatient().getId() == patientId) {
//                result.add(c);
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    public List<Case> listByPhysician(long physicianId) {
//        ArrayList<Case> result = new ArrayList<>();
//
//        for (Case c : cases) {
//            if (c.getPhysician() != null) {
//                if (c.getPhysician().getId() == physicianId) {
//                    result.add(c);
//                }
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    public List<Case> listOpenCases() {
//        ArrayList<Case> result = new ArrayList<>();
//
//        for (Case c : cases) {
//            if (c.getPhysician() == null && c.getStatus() == CaseStatus.LookingForPhysician) {
//                result.add(c);
//            }
//        }
//
//        return result;
//    }
//
//    @Override
//    public void insert(Case newCase) {
//        // find highest id
//        long id = 0;
//        for (Case c : cases) {
//            id = Math.max(c.getId(), id);
//        }
//
//        // generate new id
//        id++;
//        newCase.setId(id);
//
//        // add
//        cases.add(newCase);
//    }
//
//    @Override
//    public Case getCaseById(long caseId) {
//        for (Case c : cases) {
//            if (c.getId() == caseId) {
//                return  c;
//            }
//        }
//        throw new EntityNotFoundException("id="+caseId);
//    }
//
//    @Override
//    public void update(Case updatedCase) {
//        // not necessary in mock
//    }
//}
