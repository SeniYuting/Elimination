package controllerservice.user;

import po.user.UserPO;

/**
 * <code><b>UpdateControllerService</b></code> contains components about handling update issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface UpdateControllerService {
	/**
	 * Contact the <code><b>UpdateControllerService</code></b> to updateLocalCoins.
	 * @see GamePanel
	 * @param po
	 * @return
	 */
	public abstract void updateLocalCoins(UserPO po);
	/**
	 * Contact the <code><b>UpdateControllerService</code></b> to updateNetCoins.
	 * @see GamePanel
	 * @param po
	 * @return
	 */
	public abstract void updateNetCoins(UserPO po);
	/**
	 * Contact the <code><b>UpdateControllerService</code></b> to updateUserPO.
	 * @see GamePanel
	 * @param userName,score,combo,coin
	 * @return
	 */
	public abstract void updateUserPO(String userName,int score,int combo,int coin);
	/**
	 * Contact the <code><b>UpdateControllerService</code></b> to changePassword.
	 * @see UserInfoPanel
	 * @param user,password
	 * @return
	 */
	public void changePassword(String user,String password);
}
