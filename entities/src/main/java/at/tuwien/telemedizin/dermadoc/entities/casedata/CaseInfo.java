package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;

/**
 * General case information.
 */
public class CaseInfo extends CaseData {

    public CaseInfo(long id, Calendar created, User author, BodyLocalization localization, PainIntensity pain, double size) {
        super(id, created, author);

        this.localization = localization;
        this.pain = pain;
        this.size = size;
    }

    private BodyLocalization localization;
    public BodyLocalization getLocalization() { return localization; }

    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }
}
