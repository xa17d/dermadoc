package at.tuwien.telemedizin.dermadoc.entities.rest;

/**
 * Created by daniel on 23.11.2015.
 */
public class AuthenticationToken {
    public AuthenticationToken() {}
    public AuthenticationToken(String type, String token) {
        setType(type);
        setToken(token);
    }

    private String type;
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    private String token;
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() { return type + " " + token; }
}
