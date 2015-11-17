package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.desktop.exception.DermadocException;
import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

import java.util.List;
import java.util.Map;

/**
 * interface for gui-model to access service layer
 * and load data from backend
 */
public interface ICaseService {

    /*
     * LOAD DATA FROM BACKEND
     */

    /**
     * load all open cases from backend
     * @return list of all open cases
     * @throws DermadocException
     */
    List<Case> getOpenCases() throws DermadocException;

    /**
     * load all cases of the physician sorted by the patient
     * @return map containing all cases of each patient
     * @throws DermadocException
     */
    Map<Patient,List<Case>> getAllCases() throws DermadocException;


    /**
     * load all case data of a specific case
     * @param aCase case
     * @return list of case data
     * @throws DermadocException
     */
    List<CaseData> getCaseData(Case aCase) throws DermadocException;



    /*
     * SAVE DATA ON BACKEND
     */

    /**
     * signalize that the physician wants to accept the case
     * @param aCase case
     * @throws DermadocException
     */
    void acceptCase(Case aCase) throws DermadocException;

    /**
     * save new case data of a case to the backend
     * @param aCase case
     * @throws DermadocException
     */
    void saveCase(Case aCase) throws DermadocException;



    /*
     * NOTIFICATIONS
     */

    /**
     * set a notification listerner that gets called when a new
     * notification is available
     * @param listener notification listener
     */
    void setNotificationListener(DermadocNotificationListener listener);
}
