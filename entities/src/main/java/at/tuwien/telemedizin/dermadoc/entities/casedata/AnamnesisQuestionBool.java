package at.tuwien.telemedizin.dermadoc.entities.casedata;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Created by daniel on 24.11.2015.
 */
@Entity
@PrimaryKeyJoinColumn(name = "question")
public class AnamnesisQuestionBool extends AnamnesisQuestion {
    private Boolean answer;
    public Boolean getAnswer() { return answer; }
    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "# " + getQuestion() + "\n   * " + (getAnswer() == null ? "-"  : (getAnswer() ? "YES" : "NO"));
    }
}
