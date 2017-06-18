package modelservice.rank;

import java.util.ArrayList;

import po.rank.RankPO;

/**
 * <code><b>RankModelService</b></code> contains components about handling rank model issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface RankModelService {
	/**
	 * Contact the <code><b>RankModelService</code></b> to insert.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String insert(RankPO po);
	/**
	 * Contact the <code><b>RankModelService</code></b> to delete.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract String delete(RankPO po);
	/**
	 * Contact the <code><b>RankModelService</code></b> to modify.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract void modify(RankPO po);
	/**
	 * Contact the <code><b>RankModelService</code></b> to find.
	 * @see Controller
	 * @param po
	 * @return
	 */
	public abstract RankPO find(RankPO po);
	/**
	 * Contact the <code><b>RankModelService</code></b> to find.
	 * @see Controller
	 * @param
	 * @return
	 */
	public abstract ArrayList<RankPO> find();
	

}
