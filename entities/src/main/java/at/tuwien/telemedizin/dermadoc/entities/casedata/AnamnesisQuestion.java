package at.tuwien.telemedizin.dermadoc.entities.casedata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created by daniel on 24.11.2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnamnesisQuestionBool.class),
        @JsonSubTypes.Type(value = AnamnesisQuestionText.class)
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AnamnesisQuestion {

    public AnamnesisQuestion() { }

    @Id
    private Long anamnesisQuestionId;

    public Long getanamnesisQuestionId() {
        return anamnesisQuestionId;
    }

    public void setanamnesisQuestionId(Long id) {
        this.anamnesisQuestionId = id;
    }


    public void setAnamnesisQuestionId(Long anamnesisQuestionId) {
        this.anamnesisQuestionId = anamnesisQuestionId;
    }

    private String question;
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

}
