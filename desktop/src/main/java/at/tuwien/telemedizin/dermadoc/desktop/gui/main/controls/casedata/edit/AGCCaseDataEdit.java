package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.AGCCaseData;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.handler.CaseDataEventHandler;

/**
 * Created by Lucas on 17.11.2015.
 */
public abstract class AGCCaseDataEdit extends AGCCaseData {

    public abstract void setSaveEventHandler(CaseDataEventHandler caseDataEventHandler);

    @Override
    public boolean isObsolete() {
        return false;
    }
}
