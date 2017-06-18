package controller.game;
/**
 * <code><b>GameSituationException</b></code> 用于报告棋盘异常时的异常类，
 * 如果棋子的数量不正确或者是出现了别的问题，就会报搞这个异常。
 * 
 * @author 
 */

public class GameSituationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GameSituationException(){
		System.out.println("Error");
	}
}
