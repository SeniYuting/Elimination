package modelservice.user;

import po.user.UserPO;

/**
 * <code><b>DailyPlayModelService</b></code> contains components about handling daily play issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface DailyPlayModelService {
	/**
	 * Contact the <code><b>DailyPlayModelService</code></b> to insert.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String insert(UserPO po);
	/**
	 * Contact the <code><b>DailyPlayModelService</code></b> to delete.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String delete(UserPO po);
	/**
	 * Contact the <code><b>DailyPlayModelService</code></b> to find.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract UserPO find(UserPO po);
	/**
	 * Contact the <code><b>DailyPlayModelService</code></b> to modify.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String modify(UserPO po);
}
