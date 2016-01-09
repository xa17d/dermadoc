package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Case;
import at.tuwien.telemedizin.dermadoc.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.util.Calendar;

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
/*
 ---> this does not work propertly, because only with the table case_data,
      hibernate can not determine which subtype of CaseData the item is
      and throws an cannot be cast exception.
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "CaseData.listCaseDataByUserAndCase",
                query = "select * from case_data cd where cd.case_id_case_id = ?1  AND (cd.is_private = FALSE or cd.author_id=?2)"
        )
})
*/
@Entity
@Table(name = "case_data")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CaseData {


    public CaseData(long id, Calendar created, User author) {
        this.id = id;
        this.created = created;
        this.author = author;
    }

    public CaseData() { }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "casedata_id")
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonIgnore
    @OneToOne
    private Case caseId;

    @JsonIgnore
    public Case getCase() {
        return caseId;
    }

    public void setCase(Case caseId) {
        this.caseId = caseId;
    }




    private Calendar created;
    public Calendar getCreated() { return created; }
    public void setCreated(Calendar created) { this.created = created; }


    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    private boolean isPrivate;
    public boolean getPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }

    @Column(name = "is_obsolete")
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
