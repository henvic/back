package userCenter;

import modules.User;

/**
 * Created by leo on 28/01/2015.
 */
public class UserTCP extends User {
	/**
	 * needs Receiver and Sender attribute
	 */

    public UserTCP (String username, String ip) {
        super(username, ip);
    }

	@Override
	public void login() {

	}

	@Override
	public boolean connectServer(String ip, String port) {
		return false;
	}

	@Override
	public void connectionRequest(User user2) {

	}

	@Override
	public void receiveConnectionRequest(User user1) {

	}

	@Override
	public void openServer() {

	}

	@Override
	public void acceptRequest() {

	}

	@Override
	public void openClient() {

	}

	@Override
	public void sendMessage(String destinationIp) {

	}

	@Override
	public void receiveACK() {

	}
}
