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

    public CaseInfo(long id, Calendar created, User author, List<BodyLocalization> localizations, PainIntensity pain, double size) {
        super(id, created, author);

        this.localizations = localizations;
        this.pain = pain;
        this.size = size;
    }

    public CaseInfo() {   }


    private List<BodyLocalization> localizations;
    public List<BodyLocalization> getLocalizations() { return localizations; }

    //TODO 'localization' is deprecated, 'localizationS' should be used at backend, for more than one localization

    private BodyLocalization localization;
    @Deprecated
    public BodyLocalization getLocalization() { return localization; }

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
}
