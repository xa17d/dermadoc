package at.tuwien.telemedizin.dermadoc.entities.casedata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private String question;
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

}
