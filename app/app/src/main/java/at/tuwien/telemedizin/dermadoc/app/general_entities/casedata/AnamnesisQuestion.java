package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



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

    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
