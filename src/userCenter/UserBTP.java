package userCenter;

import messageCenter.ReceiverBTP;
import modules.User;

/**
 * Created by leo on 11/02/2015.
 * ljsa at cin.ufpe.br
 */
public class UserBTP extends User {

	public UserBTP (String username, String ip) {
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