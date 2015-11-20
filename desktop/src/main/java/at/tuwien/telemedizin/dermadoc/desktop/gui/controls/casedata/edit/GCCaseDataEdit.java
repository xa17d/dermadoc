package at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.controls.casedata.GCCaseData;
import at.tuwien.telemedizin.dermadoc.desktop.service.CaseDataEventHandler;

/**
 * Created by Lucas on 17.11.2015.
 */
public abstract class GCCaseDataEdit extends GCCaseData {

    public abstract void setSaveEventHandler(CaseDataEventHandler caseDataEventHandler);
}
