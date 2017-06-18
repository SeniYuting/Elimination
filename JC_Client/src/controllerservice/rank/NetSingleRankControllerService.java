package controllerservice.rank;

import java.util.ArrayList;
import po.rank.NetSingleRankPO;

/**
 * <code><b>NetSingleRankControllerService</b></code> contains components about handling rank issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface NetSingleRankControllerService {
	/**
	 * Contact the <code><b>NetSingleRankControllerService</code></b> to getRankList.
	 * @see RankPanel
	 * @param
	 * @return
	 */
	public ArrayList<NetSingleRankPO> getRankList();
}
