package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;



/**
 * Created by daniel on 24.11.2015.
 */

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
