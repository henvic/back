package modules;

/**
 * Created by leo on 11/02/2015.
 * ljsa at cin.ufpe.br
 */
public abstract class User {

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

	/**
	 * flow:
	 * 		- server connection:
	 * 			- User send connection request to the server (log in the server), sending the ip, username and status
	 * 			- Server answer with an ack
	 *	 			- if ack fail, User will send it again
	 *
	 *	 	- client connection:
	 *	 		- User1 sends to server an request to connect with User2
	 *	 		- the server answer with an ACK
	 *	 		- the server sends to User2 an request connect from User1, sending the ip, username and status
	 *	 		- User2 answer with an ACK
	 *	 		- if User2 accept the connection request
	 *	 			- User2 opens an server (only TCP) to p2p connection
	 *	 			- User2 sends to server a message accepting the connection request
	 *	 		- the server allow User1 to start client (TCP and BTP)
	 */

	/////////////////////////// SERVER CONNECTION ///////////////////////////

	/**
	 * method: login the server
	 * description: runs the server's ip array and try to connect. Updates serverIp, serverPort
	 */
	public abstract void login();

	/**
	 * method: connects to a server
	 * param ip: String giving the server ip
	 * param port: Int giving the connection port
	 * return: true to success, false to fail
	 * description: try to connect to a server (called by 'login')
	 */
	public abstract boolean connectServer(String ip, String port);

	/////////////////////////// CLIENT CONNECTION ///////////////////////////

	/**
	 * method: sends an request, to server, to connect with User2
	 * param user2: user2's data
	 */
	public abstract void connectionRequest(User user2);

	/**
	 * method: receive user1's data
	 * param user1: user1's data
	 */
	public abstract void receiveConnectionRequest(User user1);

	/**
	 * method: open server to p2p connection
	 */
	public
	abstract void openServer();

	/**
	 * method: send server a message accepting the connection request
	 */
	public abstract void acceptRequest();

	/**
	 * method: open client side
	 */
	public abstract void openClient();

	public abstract void sendMessage(String destinationIp);

	public abstract void receiveACK();
	
	
}
