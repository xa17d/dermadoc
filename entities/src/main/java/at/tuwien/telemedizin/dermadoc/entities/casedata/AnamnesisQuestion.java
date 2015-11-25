package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.Medication;

import java.util.List;

/**
 * Created by daniel on 24.11.2015.
 */
public abstract class AnamnesisQuestion {

    public AnamnesisQuestion() { }

    private String question;
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

}
