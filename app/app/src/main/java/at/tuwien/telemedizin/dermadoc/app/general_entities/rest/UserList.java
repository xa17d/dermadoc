package at.tuwien.telemedizin.dermadoc.app.general_entities.rest;

import java.util.ArrayList;
import java.util.Collection;

import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Created by daniel on 01.12.2015.
 */
public class UserList extends ArrayList<User> {

    public UserList() {}
    public UserList(Collection<? extends User> original) {
        super(original);
    }

}