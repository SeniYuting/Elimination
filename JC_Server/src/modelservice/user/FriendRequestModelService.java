package modelservice.user;

import java.util.ArrayList;

import po.user.UserPO;

/**
 * <code><b>FriendRequestModelService</b></code> contains components about handling friend request' issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface FriendRequestModelService {
	/**
	 * Contact the <code><b>FriendRequestModelService</code></b> to insert.
	 * @see Controller
	 * @param userName,friendName
	 * @return
	 */
	public abstract String insert(String userName,String friendName);
	/**
	 * Contact the <code><b>FriendRequestService</code></b> to delete.
	 * @see Controller
	 * @param userName
	 * @return
	 */
	public abstract String delete(String userName);
	/**
	 * Contact the <code><b>FriendRequestModelService</code></b> to find.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract ArrayList<String> find(UserPO po);
}
