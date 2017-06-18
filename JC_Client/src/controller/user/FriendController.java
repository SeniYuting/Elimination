package controller.user;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.SocketClient;
import netservice.SocketClientService;
import po.message.Message;
import po.user.UserPO;
import controllerservice.user.FriendControllerService;

public class FriendController implements FriendControllerService{

	SocketClientService s;
	
	public FriendController(SocketClientService s){
		this.s=s;
	}
	
	@Override
	public ArrayList<String> getOnlineFriends(String userName) {
		// TODO Auto-generated method stub
		ArrayList<String> onlineFriends = new ArrayList<String>();
//		SocketClientService s;
		Message m = null;
		
		try {
			s = new SocketClient();
			m = s.getfriendol(new UserPO(userName));
			onlineFriends = m.getolfriend();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return onlineFriends;
	}

	@Override
	public void setFriendRequest(String userName, String friendName) {
		// TODO Auto-generated method stub
		s.addFriends(userName, friendName);
	}


}
