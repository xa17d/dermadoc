package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.AGCCaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

/**
 * Created by Lucas on 18.11.2015.
 */
public abstract class AGCCaseDataView extends AGCCaseData {

    abstract public void expand(boolean expand);

    abstract public CaseData getCaseData();
}
