package HospitalApplication.email.model;

/**
 * Created by GUCIA on 2014-11-30.
 */
public class Email {

    private String login;
    private String password;
    private String host;

    public Email(){

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
