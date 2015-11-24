package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.AGCCaseData;
import at.tuwien.telemedizin.dermadoc.desktop.service.CaseDataEventHandler;

/**
 * Created by Lucas on 17.11.2015.
 */
public abstract class AGCCaseDataEdit extends AGCCaseData {

    public abstract void setSaveEventHandler(CaseDataEventHandler caseDataEventHandler);
}
