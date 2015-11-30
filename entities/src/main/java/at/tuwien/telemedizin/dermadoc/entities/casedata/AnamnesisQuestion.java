package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Medication;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * Created by daniel on 24.11.2015.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnamnesisQuestionBool.class),
        @JsonSubTypes.Type(value = AnamnesisQuestionText.class)
})
public abstract class AnamnesisQuestion {

    public AnamnesisQuestion() { }

    private String question;
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

}
