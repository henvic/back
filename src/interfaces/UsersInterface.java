package interfaces;

import modules.User;

/**
 * Created by leo on 11/02/2015.
 * ljsa at cin.ufpe.br
 */
public interface UsersInterface {

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
	public void login ();

	/**
	 * method: connects to a server
	 * param ip: String giving the server ip
	 * param port: Int giving the connection port
	 * return: true to success, false to fail
	 * description: try to connect to a server (called by 'login')
	 */
	public boolean connectServer (String ip, String port);

	/////////////////////////// CLIENT CONNECTION ///////////////////////////

	/**
	 * method: sends an request, to server, to connect with User2
	 * param user2: user2's data
	 */
	public void connectionRequest (User user2);

	/**
	 * method: receive user1's data
	 * param user1: user1's data
	 */
	public void receiveConnectionRequest (User user1);

	/**
	 * method: open server to p2p connection
	 */
	public void openServer ();

	/**
	 * method: send server a message accepting the connection request
	 */
	public void acceptRequest ();

	/**
	 * method: open client side
	 */
	public void openClient ();
	/////////////////////////// GLOBAL ///////////////////////////

	/**
	 * method: sends message
	 * param ip: destinationIp
	 * other param to flags (ack, openServer, connectionRequest...)
	 */
	public void sendMessage (String destinationIp);

	/**
	 * method: process the ack receiving
	 * description: Update nextSeq number
	 */
	public void receiveACK ();

}
