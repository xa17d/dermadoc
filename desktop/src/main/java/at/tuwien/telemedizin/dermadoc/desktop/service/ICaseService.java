package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.desktop.service.dto.PatientCaseMap;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Notification;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import javafx.collections.ObservableList;

/**
 * interface for gui-model to access service layer
 * and load data from backend
 */
public interface ICaseService {

    /*
     * LOAD DATA
     */

    /**
     * load an observable list of all open cases
     *
     * @return list of all open cases
     * @throws DermadocException
     */
    ObservableList<Case> getOpenCases() throws DermadocException;

    /**
     * load all cases of the physician sorted by the patient
     *
     * @return map containing all cases of each patient
     * @throws DermadocException
     */
    PatientCaseMap getAllCases() throws DermadocException;


    /**
     * load a observable map of all cases of the physician,
     * containing the searchText (in case or patient name, sorted by the patient)
     *
     * @param searchText string that can contain patient name, birthdate or case name
     * @return map containing all cases of selected patients
     * @throws DermadocException
     */
    PatientCaseMap searchCases(String searchText) throws DermadocException;


    /**
     * load an observable list of all case data of a specific case
     *
     * @param aCase case
     * @return list of case data
     * @throws DermadocException
     */
    ObservableList<CaseData> getCaseData(Case aCase) throws DermadocException;


    /*
     * SAVE DATA (ON BACKEND)
     */

    /**
     * signalize that the physician wants to accept the case
     *
     * @param aCase case
     * @throws DermadocException
     */
    void acceptCase(Case aCase) throws DermadocException;

    /**
     * save new case data of a case to the backend
     *
     * @param aCase    case
     * @param caseData case data
     * @return the saved case (this time containing a valid id
     * @throws DermadocException
     */
    void saveCaseData(Case aCase, CaseData caseData) throws DermadocException;

}
