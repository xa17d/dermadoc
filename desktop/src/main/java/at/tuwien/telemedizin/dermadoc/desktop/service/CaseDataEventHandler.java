package at.tuwien.telemedizin.dermadoc.desktop.service;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

/**
 * event
 * runs onEvent when case data has changed
 */
public interface CaseDataEventHandler {

    void onEvent(CaseData caseData);
}
