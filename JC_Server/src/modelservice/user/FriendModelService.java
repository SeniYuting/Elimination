package modelservice.user;

import po.user.UserPO;

/**
 * <code><b>FriendModelService</b></code> contains components about handling friends' issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface FriendModelService {
	/**
	 * Contact the <code><b>FriendModelService</code></b> to insert.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String insert(UserPO po);
	/**
	 * Contact the <code><b>FriendModelService</code></b> to delete.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String delete(UserPO po);
	/**
	 * Contact the <code><b>FriendModelService</code></b> to find.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract UserPO find(UserPO po);
	/**
	 * Contact the <code><b>FriendModelService</code></b> to modify.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String modify(UserPO po);
}
