package at.tuwien.telemedizin.dermadoc.desktop.service;

/**
 * interface to access service layer
 * and login and logout the physician
 */
public interface ILoginService {

    /**
     * login a physician via email and password and get
     * the token for identification
     * @param email email
     * @param password password
     * @return token for identification on backend
     */
    Token login(String email, String password);

    /**
     * logout physician and destroy token
     * @param token token
     */
    void logout(Token token);
}
