package controller.game;

import java.util.ArrayList;



public interface GameControllerService {
	/**
	 * Contact the <code><b>GameControllerService</code></b> to getRemind.
	 *  ��ȡ��ʾ�������������ӵ�λ�á�
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public abstract ArrayList<Tile> initGame();
	/**
	 * Contact the <code><b>GameControllerService</code></b> to canSwap.
	 *  ���������������Ƿ���Խ���������������֮���ܷ��γ�������
	 * @see GameControllerService
	 * @param t1, t2
	 * @return boolean
	 */
	public abstract ArrayList<ReqResult> swap(Tile t1, Tile t2);

}