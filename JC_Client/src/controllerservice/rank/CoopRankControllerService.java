package controllerservice.rank;

import java.util.ArrayList;
import po.rank.RankPO;

/**
 * <code><b>CoopRankControllerService</b></code> contains components about handling coop rank issues
 * 
 * @author ≈Ù∑…
 * 
 */
public interface CoopRankControllerService {
	/**
	 * Contact the <code><b>CoopRankControllerService</code></b> to getCoopRank.
	 * @see RankPanel
	 * @param
	 * @return
	 */
	public abstract ArrayList<RankPO> getCoopRank();
	/**
	 * Contact the <code><b>CoopRankControllerService</code></b> to setCoopRank.
	 * @see RankPanel
	 * @param po
	 * @return
	 */
	public abstract void setCoopRank(RankPO po);
}
