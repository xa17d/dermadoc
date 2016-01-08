package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.BodyLocalization;
import at.tuwien.telemedizin.dermadoc.entities.PainIntensity;
import at.tuwien.telemedizin.dermadoc.entities.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * General case information.
 */
@Entity
@Embeddable
@Table(name = "case_info")
@PrimaryKeyJoinColumn(name = "id")
public class CaseInfo extends CaseData {

    public CaseInfo(Long id, Calendar created, User author, List<BodyLocalization> localizations, PainIntensity pain, double size) {
        super(id, created, author);

        this.localizations = localizations;
        this.pain = pain;
        this.size = size;
    }

    public CaseInfo() {   }


    @ElementCollection
    private List<BodyLocalization> localizations;
    public List<BodyLocalization> getLocalizations() { return localizations; }

    //TODO 'localization' is deprecated, 'localizationS' should be used at backend, for more than one localization
    @Column(name = "body_location")
    @Deprecated
    private BodyLocalization localization;
    @Deprecated
    public BodyLocalization getLocalization() { return localization; }

    @Column(name = "pain_intensity")
    private PainIntensity pain;
    public PainIntensity getPain() { return pain; }

    private double size;
    public double getSize() { return size; }


    //TODO delete
    @Deprecated
    public CaseInfo(Long id, Calendar created, User author, BodyLocalization localization, PainIntensity pain, double size) {
        super(id, created, author);

        this.localization = localization;
        this.pain = pain;
        this.size = size;
    }
}
