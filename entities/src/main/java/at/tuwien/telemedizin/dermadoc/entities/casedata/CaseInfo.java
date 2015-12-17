package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;
import java.util.List;

/**
 * General case information.
 */
public class CaseInfo extends CaseData {

    public CaseInfo(long id, Calendar created, User author, List<BodyLocalization> localizations, PainIntensity pain, double size) {
        super(id, created, author);

        this.localizations = localizations;
        this.pain = pain;
        this.size = size;
    }

    public CaseInfo() {   }

    private List<BodyLocalization> localizations;
    public List<BodyLocalization> getLocalizations() { return localizations; }

    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }


    //TODO delete
    @Deprecated
    public CaseInfo(long id, Calendar created, User author, BodyLocalization localization, PainIntensity pain, double size) {
        super(id, created, author);

        this.localization = localization;
        this.pain = pain;
        this.size = size;
    }

    @Deprecated
    public BodyLocalization getLocalization() { return localization; }
    private BodyLocalization localization;
}
