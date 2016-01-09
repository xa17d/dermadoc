package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Calendar;


import at.tuwien.telemedizin.dermadoc.app.general_entities.Case;
import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Abstract CaseData.
 * A case can have multiple CaseData elements.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Advice.class),
        @JsonSubTypes.Type(value = Anamnesis.class),
        @JsonSubTypes.Type(value = CaseInfo.class),
        @JsonSubTypes.Type(value = Diagnosis.class),
        @JsonSubTypes.Type(value = PhotoMessage.class),
        @JsonSubTypes.Type(value = TextMessage.class)
})

public abstract class CaseData {


    public CaseData(Long id, Calendar created, User author) {
        this.id = id;
        this.created = created;
        this.author = author;
    }

    public CaseData() { }


    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    private Case caseId;

    public Case getCase() {
        return caseId;
    }

    public void setCase(Case caseId) {
        this.caseId = caseId;
    }




    private Calendar created;
    public Calendar getCreated() { return created; }
    public void setCreated(Calendar created) { this.created = created; }


    private User author;
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    private boolean isPrivate;
    public boolean getPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }


    private boolean obsolete;
    public boolean isObsolete() { return obsolete; }
    public void setObsolete(boolean obsolete) { this.obsolete = obsolete; }

    //TODO 'nextVersion' is deprecated, 'obsolete' should be used at backend
//    @OneToOne
//    @Deprecated
//    private CaseData nextVersion;
//    @Deprecated
//    public CaseData getNextVersion() { return nextVersion; }
//    @Deprecated
//    public void setNextVersion(CaseData nextVersion) { this.nextVersion = nextVersion; }

}
