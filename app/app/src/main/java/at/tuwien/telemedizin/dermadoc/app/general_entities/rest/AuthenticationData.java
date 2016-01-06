package at.tuwien.telemedizin.dermadoc.app.general_entities.rest;

/**
 * Created by daniel on 23.11.2015.
 */
public class AuthenticationData {
    public AuthenticationData() {}
    public AuthenticationData(String mail, String password) {
        setMail(mail);
        setPassword(password);
    }

    private String mail;
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
