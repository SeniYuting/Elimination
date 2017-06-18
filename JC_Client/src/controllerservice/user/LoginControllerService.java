package controllerservice.user;

import java.util.ArrayList;

import po.message.Message;
import po.user.UserPO;

/**
 * <code><b>LoginControllerService</b></code> contains components about handling login issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface LoginControllerService {
	/**
	 * Contact the <code><b>LoginControllerService</code></b> to login.
	 * @see LoginFrame
	 * @param userName,password
	 * @return
	 */
	public abstract Message login(String userName,String password);
	/**
	 * Contact the <code><b>LoginControllerService</code></b> to getUserPO.
	 * @see LoginFrame
	 * @param userName
	 * @return
	 */
	public abstract UserPO getUserPO(String userName);
	/**
	 * Contact the <code><b>LoginControllerService</code></b> to getAllUsers.
	 * @see UserInfoPanel
	 * @param
	 * @return
	 */
	public abstract ArrayList<UserPO> getAllUsers();
}
