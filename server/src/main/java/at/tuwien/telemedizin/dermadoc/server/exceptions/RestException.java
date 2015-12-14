package at.tuwien.telemedizin.dermadoc.server.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by daniel on 30.11.2015.
 */
public class RestException extends RuntimeException {
    public RestException(HttpStatus statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    private HttpStatus statusCode;
    public HttpStatus getStatusCode() { return statusCode; }
}
