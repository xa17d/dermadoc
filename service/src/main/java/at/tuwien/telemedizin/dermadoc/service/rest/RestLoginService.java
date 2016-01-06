package at.tuwien.telemedizin.dermadoc.service.rest;

import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationData;
import at.tuwien.telemedizin.dermadoc.entities.rest.AuthenticationToken;
import at.tuwien.telemedizin.dermadoc.service.rest.listener.RestListener;

/**
 * Created by Lucas on 27.11.2015.
 */
public class RestLoginService implements IRestLoginService {

    //private static final String URL_ = "http://dermadoc.xa1.at:82/";
    private static final String URL_ = "http://localhost:8080/";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String USER = "user";

    @Override
    public void postLogin(RestListener<AuthenticationToken> listener, AuthenticationData data) {
        new Thread(new PostLogin(listener, data)).start();
    }

    @Override
    public void postLogout(RestListener<Void> listener, AuthenticationToken token) {
        //TODO
    }

    @Override
    public void getUser(RestListener<? extends User> listener, AuthenticationToken token) {
        new Thread(new GetUser(token, (RestListener<User>) listener)).start();
    }



    /*
     * RUNNABLES
     */

    private class PostLogin implements Runnable {

        private RestListener<AuthenticationToken> listener;
        private AuthenticationData authenticationData;
        public PostLogin(RestListener<AuthenticationToken> listener, AuthenticationData authenticationData) {
            this.listener = listener;
            this.authenticationData = authenticationData;
        }

        @Override
        public void run() {
            PostRequest<AuthenticationData, AuthenticationToken> rest = new PostRequest<>(null, AuthenticationToken.class);
            rest.post(URL_ + LOGIN, listener, authenticationData);
        }
    }

    private class GetUser implements Runnable {

        private AuthenticationToken token;
        private RestListener<User> listener;
        public GetUser(AuthenticationToken token, RestListener<User> listener) {
            this.token = token;
            this.listener = listener;
        }

        @Override
        public void run() {
            GetRequest<User> rest = new GetRequest<>(token, User.class);
            rest.get(URL_ + USER, listener);
        }
    }
}
