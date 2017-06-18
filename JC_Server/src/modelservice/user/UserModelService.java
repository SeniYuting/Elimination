package modelservice.user;

import java.util.ArrayList;

import po.user.UserPO;

/**
 * <code><b>UserModelService</b></code> contains components about handling users' issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface UserModelService {
	/**
	 * Contact the <code><b>UserModelService</code></b> to insert.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String insert(UserPO po);
	/**
	 * Contact the <code><b>UserModelService</code></b> to delete.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String delete(UserPO po);
	/**
	 * Contact the <code><b>UserModelService</code></b> to modify.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String modify(UserPO po);
	/**
	 * Contact the <code><b>UserModelService</code></b> to find.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract UserPO find(UserPO po);
	/**
	 * Contact the <code><b>UserModelService</code></b> to find.
	 * @see Controller
	 * @param
	 * @return
	 */
	public abstract ArrayList<UserPO> find();
	/**
	 * Contact the <code><b>UserModelService</code></b> to find.
	 * @see Controller
	 * @param userName
	 * @return
	 */
	public abstract int find(String userName);
}