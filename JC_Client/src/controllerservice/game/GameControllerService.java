package controllerservice.game;

import java.util.ArrayList;

import controller.game.GameSituationException;
import controller.game.ReqResult;
import controller.game.Tile;

public interface GameControllerService {

	public static final int GAMEDIMENSION = 9;
	/**
	 * Contact the <code><b>GameControllerService</code></b> to swap.
	 *  ���ؽ���������������֮��Ķ������У���������������򶯻�����Ϊԭ�ȵ�����״̬
	 * @see GameControllerService
	 * @param t1, t2
	 * @return  ArrayList<ReqResult>
	 */
	public abstract ArrayList<ReqResult> swap(Tile t1, Tile t2);

	/**
	 * Contact the <code><b>GameControllerService</code></b> to canSwap.
	 *  ���������������Ƿ���Խ���������������֮���ܷ��γ�������
	 * @see GameControllerService
	 * @param t1, t2
	 * @return boolean
	 */
	public boolean canSwap(Tile t1, Tile t2);
	/**
	 * Contact the <code><b>GameControllerService</code></b> to useSharkProp.
	 *  ��������㵼������֮�����������������ض������У�
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> useSharkProp();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to initGame.
	 *  ��ʼ��һ�����̵������б�Ҫ�������ȶ���
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public ArrayList<Tile> initGame();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to getRemind.
	 *  ��ȡ��ʾ�������������ӵ�λ�á�
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public abstract ArrayList<Tile> getRemind();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to checkTile.
	 *  ����˵���֮���������������Ա���������ӽ��м�飬���ض������С�
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> checkTile(Tile t) throws GameSituationException;
	/**
	 * Contact the <code><b>GameControllerService</code></b> to timeUp.
	 *  ����ʱ����ÿһ��������߽���ʹ�ã����ض������У�
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> timeUp();
	
}