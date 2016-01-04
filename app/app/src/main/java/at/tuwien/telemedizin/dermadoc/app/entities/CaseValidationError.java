package at.tuwien.telemedizin.dermadoc.app.entities;

/**
 * Created by FAUser on 04.01.2016.
 */
public class CaseValidationError {

    private String message;
    private CaseValidationErrorLevel level;

    public String getMessage() {
        return message;
    }

    public CaseValidationErrorLevel getLevel() {
        return level;
    }

    public CaseValidationError(String message, CaseValidationErrorLevel level) {
        this.message = message;
        this.level = level;
    }
}
