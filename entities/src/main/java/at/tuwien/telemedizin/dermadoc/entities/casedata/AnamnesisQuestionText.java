package at.tuwien.telemedizin.dermadoc.entities.casedata;

/**
 * Created by daniel on 24.11.2015.
 */
public class AnamnesisQuestionText extends AnamnesisQuestion {
    private String answer;
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "# " + getQuestion() + (getAnswer() == null ? "" : ("\n   * " + getAnswer()));
    }
}
