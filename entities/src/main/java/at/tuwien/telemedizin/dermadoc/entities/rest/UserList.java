package at.tuwien.telemedizin.dermadoc.entities.rest;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by daniel on 01.12.2015.
 */
public class UserList extends ArrayList<User> {

    public UserList() {}
    public UserList(Collection<? extends User> original) {
        super(original);
    }

}