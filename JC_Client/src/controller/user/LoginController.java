package controller.user;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.SocketClient;
import netservice.SocketClientService;
import po.message.Message;
import po.user.UserPO;
import controllerservice.user.LoginControllerService;

public class LoginController implements LoginControllerService {

	SocketClientService lgs;//登录用到的socket
	SocketClientService s;//查询统计数据用到的socket

	public LoginController(SocketClientService lgs) {
		this.lgs = lgs;
		try {
			s = new SocketClient();
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}

	public Message login(String userName, String password) {
		Message m = null;
		UserPO po = new UserPO(userName, password);
		m = lgs.login(po);
		return m;
	}

	public UserPO getUserPO(String userName) {
		Message m = null;
		UserPO po = new UserPO(userName);
		UserPO returnPO = new UserPO();
		m = s.getUserPO(po);
		returnPO = m.getUser();
		return returnPO;
	}

	@Override
	public ArrayList<UserPO> getAllUsers() {
		Message m = null;
		ArrayList<UserPO> allUsers = new ArrayList<UserPO>();

		m = s.getAllUsers();
		allUsers = m.getAllUsers();

		return allUsers;

	}

}
