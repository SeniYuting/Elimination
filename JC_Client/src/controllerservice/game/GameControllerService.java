package controllerservice.game;

import java.util.ArrayList;

import controller.game.GameSituationException;
import controller.game.ReqResult;
import controller.game.Tile;

public interface GameControllerService {

	public static final int GAMEDIMENSION = 9;
	/**
	 * Contact the <code><b>GameControllerService</code></b> to swap.
	 *  返回交换了这两个棋子之后的动画队列，如果不能消除，则动画队列为原先的棋盘状态
	 * @see GameControllerService
	 * @param t1, t2
	 * @return  ArrayList<ReqResult>
	 */
	public abstract ArrayList<ReqResult> swap(Tile t1, Tile t2);

	/**
	 * Contact the <code><b>GameControllerService</code></b> to canSwap.
	 *  返回这两个棋子是否可以交换，即：交换了之后能否形成消除。
	 * @see GameControllerService
	 * @param t1, t2
	 * @return boolean
	 */
	public boolean canSwap(Tile t1, Tile t2);
	/**
	 * Contact the <code><b>GameControllerService</code></b> to useSharkProp.
	 *  点击了鲨鱼导弹道具之后调用这个方法，返回动画队列；
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> useSharkProp();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to initGame.
	 *  初始化一张棋盘的棋子列表，要求期盼稳定；
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public ArrayList<Tile> initGame();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to getRemind.
	 *  获取提示可以消除的棋子的位置。
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public abstract ArrayList<Tile> getRemind();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to checkTile.
	 *  点击了道具之后调用这个方法，对被点击的棋子进行检查，返回动画队列。
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> checkTile(Tile t) throws GameSituationException;
	/**
	 * Contact the <code><b>GameControllerService</code></b> to timeUp.
	 *  结束时，对每一个特殊道具进行使用，返回动画队列；
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> timeUp();
	
}