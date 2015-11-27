package at.tuwien.telemedizin.dermadoc.server.exceptions;

import at.tuwien.telemedizin.dermadoc.entities.Case;

/**
 * Created by daniel on 27.11.2015.
 */
public class InvalidCaseStatusException extends RuntimeException {
    public InvalidCaseStatusException(Case invalidCase) {
        super("The action can not be performed in the current status of the Case. [case.id="+invalidCase.getId()+"; status="+invalidCase.getStatus()+"]");
    }
}
