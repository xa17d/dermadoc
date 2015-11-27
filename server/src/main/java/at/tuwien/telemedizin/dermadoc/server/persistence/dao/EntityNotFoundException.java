package at.tuwien.telemedizin.dermadoc.server.persistence.dao;

/**
 * Created by daniel on 23.11.2015.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String description) {
        super("Entity Not Found: "+description);
    }
}
