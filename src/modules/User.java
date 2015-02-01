package modules;

/**
 * Created by leo on 28/01/2015.
 */
public class User {

    private String username;
    private String status;
    private String ip;

    public User(String username, String password) {
        this.username = username;
        this.status = "online";
        this.ip = "";
    }

    public void setCurrentIP (String ip) {
        this.ip = ip;
    }

    public String getCurrentIP () {
        return this.ip;
    }
}
