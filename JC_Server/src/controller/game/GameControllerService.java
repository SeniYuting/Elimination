package controller.game;

import java.util.ArrayList;



public interface GameControllerService {
	/**
	 * Contact the <code><b>GameControllerService</code></b> to getRemind.
	 *  获取提示可以消除的棋子的位置。
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public abstract ArrayList<Tile> initGame();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to canSwap.
	 *  返回这两个棋子是否可以交换，即：交换了之后能否形成消除。
	 * @see GameControllerService
	 * @param t1, t2
	 * @return boolean
	 */
	public abstract ArrayList<ReqResult> swap(Tile t1, Tile t2);

}