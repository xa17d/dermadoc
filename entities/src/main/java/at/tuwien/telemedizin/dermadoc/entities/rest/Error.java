package at.tuwien.telemedizin.dermadoc.entities.rest;

/**
 * Created by daniel on 24.11.2015.
 */
public class Error {

    public Error(Exception exception) {
        setMessage(exception.getMessage());
    }

    public Error(String message) {
        setMessage(message);
    }

    public Error() { }


    private String message;
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
