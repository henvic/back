package modules;

/**
 * Created by leo on 28/01/2015.
 */
public class User {

    private String username;
    private String status;
    private String ip;

    public User(String username) {
        this.username = username;
        this.status = "online";
        this.ip = "";
    }

    public void setIP (String ip) {
        this.ip = ip;
    }

    public String getIP () {
        return this.ip;
    }
}
