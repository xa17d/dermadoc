package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.User;

import javax.persistence.*;
import java.util.Calendar;

/**
 * General case information.
 */
@Entity
@Embeddable
@Table(name = "case_info")
@PrimaryKeyJoinColumn(name = "id")
public class CaseInfo extends CaseData {

    public CaseInfo(long id, Calendar created, User author, BodyLocalization localization, PainIntensity pain, double size) {
        super(id, created, author);

        this.localization = localization;
        this.pain = pain;
        this.size = size;
    }

    public CaseInfo() {

    }

    @Column(name = "body_location")
    private BodyLocalization localization;
    public BodyLocalization getLocalization() { return localization; }

    @Column(name = "pain_intensity")
    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }
}
