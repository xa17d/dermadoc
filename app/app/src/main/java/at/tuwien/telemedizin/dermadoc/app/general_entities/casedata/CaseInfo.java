package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import java.util.Calendar;
import java.util.List;


import at.tuwien.telemedizin.dermadoc.app.general_entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.app.general_entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * General case information.
 */

public class CaseInfo extends CaseData {

    public CaseInfo(Long id, Calendar created, User author, List<BodyLocalization> localizations, PainIntensity pain, double size) {
        super(id, created, author);

        this.localizations = localizations;
        this.pain = pain;
        this.size = size;
    }

    public CaseInfo() {   }


    public void setLocalizations(List<BodyLocalization> localizations) {
        this.localizations = localizations;
    }

    public void setPain(PainIntensity pain) {
        this.pain = pain;
    }

    public void setSize(double size) {
        this.size = size;
    }

    private List<BodyLocalization> localizations;
    public List<BodyLocalization> getLocalizations() { return localizations; }

    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }


}