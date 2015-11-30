package at.tuwien.telemedizin.dermadoc.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by daniel on 23.11.2015.
 */
public class EntityNotFoundException extends RestException {
    public EntityNotFoundException(String description) {
        super(HttpStatus.NOT_FOUND, "Entity Not Found: "+description);
    }
}
