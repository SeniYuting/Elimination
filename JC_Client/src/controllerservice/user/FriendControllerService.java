package controllerservice.user;

import java.util.ArrayList;

/**
 * <code><b>FriendControllerService</b></code> contains components about handling friends' issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface FriendControllerService {
	/**
	 * Contact the <code><b>FriendControllerService</code></b> to getOnlineFriends.
	 * @see InviteFriendPanel
	 * @param userName
	 * @return
	 */
	public abstract ArrayList<String> getOnlineFriends(String userName);

	/**
	 * Contact the <code><b>FriendControllerService</code></b> to setFriendRequest.
	 * @see InviteFriendPanel
	 * @param userName,friendName
	 * @return
	 */
	public abstract void setFriendRequest(String userName, String friendName);

}
