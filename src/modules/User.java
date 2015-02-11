package modules;

/**
 * Created by leo on 11/02/2015.
 * ljsa at cin.ufpe.br
 */
public class User {
	private String username;
	private String status;
	private String ip;

	public User (String username, String ip) {
		this.username = username;
		this.status = "online";
		this.ip = ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public String getStatus() {
		return status;
	}

	public String getUsername() {
		return username;
	}
}
