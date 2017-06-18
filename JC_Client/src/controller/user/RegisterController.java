package controller.user;

import netservice.SocketClientService;
import po.message.Message;
import po.user.UserPO;
import controllerservice.user.RegisterControllerService;

public class RegisterController implements RegisterControllerService {

	SocketClientService s;

	public RegisterController(SocketClientService s) {
		this.s = s;
	}

	public String register(String userName, String password) {
		UserPO po = new UserPO(userName, password);
		po.setCoin(1000);

		Message m = null;
		String registerInfo = "";
		m = s.register(po);
		registerInfo = m.isSuccess();

		return registerInfo;
	}

	public static void main(String[] args) {
		RegisterController rc = new RegisterController(null);

		System.out.println(rc.register("", ""));
	}
}
