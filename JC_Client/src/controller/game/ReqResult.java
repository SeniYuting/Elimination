package controller.game;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * <code><b>ReqResult</b></code> this is the return result for animation , usually when a action is performed.
 * It can be in the form of a list , which is an animation_queue;
 * 
 * @author 
 */

public class ReqResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Tile> tileList;// tileList中并不是正确的位置;
	ArrayList<Tile> bombList;
	int score;
	
	
	
	public ReqResult(ArrayList<Tile> tileList, ArrayList<Tile> bombList) {
		this.tileList = tileList;
		this.bombList = bombList;
	}
	
	public ReqResult(ArrayList<Tile> tileList, ArrayList<Tile> bombList,int s) {
		this.tileList = tileList;
		this.bombList = bombList;
		this.score = s;
	}

	public ArrayList<Tile> getTileList(){
		return tileList;
	}
	
	public ArrayList<Tile> getBombList(){
		return bombList;
	}

	
	public int getScore() {
		return score;
	}

	
}
