package controller.game;

import java.io.Serializable;
import java.util.ArrayList;

public class ReqResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Tile> tileList;// tileList中并不是正确的位置;
	ArrayList<Tile> bombList;  //如果是返回不是正确位置的Tilelist的话  bomblist也可以不用专门返回
	int score;
	
//	public ReqResult(ArrayList<Tile> tileList, ArrayList<Tile> bombList) {
//		this.tileList = tileList;
//		this.bombList = bombList;
//	}
	
	public ReqResult(ArrayList<Tile> tileList, ArrayList<Tile> bombList ,int score) {
		this.tileList = tileList;
		this.bombList = bombList;
		this.score = score;
	}

	public ArrayList<Tile> getTileList(){
		return tileList;
	}
	
	public ArrayList<Tile> getBombList(){
		return bombList;
	}
	
	public int getScore(){
		return score;
	}
	
}
