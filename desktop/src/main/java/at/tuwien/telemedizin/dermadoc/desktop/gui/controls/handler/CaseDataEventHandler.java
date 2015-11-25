package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.handler;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

/**
 * event
 * runs onEvent when case data has changed
 */
public interface CaseDataEventHandler {

    void onEvent(CaseData caseData);
}
