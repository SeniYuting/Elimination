package modelservice.user;

import po.user.UserPO;

/**
 * <code><b>RecentScoreModelService</b></code> contains components about handling recent score issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface RecentScoreModelService {
	/**
	 * Contact the <code><b>RecentScoreModelService</code></b> to insert.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String insert(UserPO po);
	/**
	 * Contact the <code><b>RecentScoreModelService</code></b> to delete.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String delete(UserPO po);
	/**
	 * Contact the <code><b>RecentScoreModelService</code></b> to modify.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String modify(UserPO po);
	/**
	 * Contact the <code><b>RecentScoreModelService</code></b> to find.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract UserPO find(UserPO po);
}
