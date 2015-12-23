package at.tuwien.telemedizin.dermadoc.service.exception;

/**
 * abstract exception for dermadoc project
 */
public abstract class DermadocException extends Exception{

    public DermadocException() {
        super("Unknown Error");
    }

    public DermadocException(String message) {
        super(message);
    }
}
