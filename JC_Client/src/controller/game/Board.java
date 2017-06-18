package controller.game;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * <code><b>Board</b></code> 持有81个棋子，负责对于棋盘的操作，比如交换棋子，获取棋盘上的某个棋子
 * 
 * @author 
 */

public class Board implements Serializable ,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int size = 9;
	// Tile board[][] = new Tile[size][size];
	private ArrayList<Tile> tilelist = new ArrayList<Tile>();
	//private ArrayList<Tile> bomblist = new ArrayList<Tile>();
	//private ArrayList<Tile> falllist = new ArrayList<Tile>();
	//private ArrayList<Tile> newlist = new ArrayList<Tile>();

//	public void addCommonBombTiles(Tile t) {
//		if (!bomblist.contains(t)) {
//			t.setWillDelete(true);
//			bomblist.add(t);
//		}
//	}

	public Board() {
		for (int i = 0; i < Board.size; i++) {
			for (int j = 0; j < Board.size; j++) {
				Tile t = Tile.getRandomTile(i, j);
				tilelist.add(t);
			}
		}
	}
	
	public Board clone(){
		
		Board cloned_board = new Board();
		cloned_board.getTilelist().clear();
		for(Tile t : this.tilelist){
			cloned_board.getTilelist().add(t.clone());
		}	
		return cloned_board;
	}

	public Board(ArrayList<Tile> tilelist) {
		this.tilelist = tilelist;
	}


	public void updateTile(int row, int col, Tile newtile) {
		Tile t = this.getTile(row, col);
		this.tilelist.remove(t);
		this.tilelist.add(newtile);
	}

	public void deleteTile(int row, int col) {
		Tile t = this.getTile(row, col);
		this.tilelist.remove(t);
	}

	public Tile getTile(int row, int col) {
		for (Tile t : tilelist) {
			if ((t.getRow() == row) && (t.getCol() == col)) {
				return t;
			}
		}
		return null;
	}

public void swapTile(Tile t1, Tile t2) {
		
		int r1 = t1.getRow();
		int c1 = t1.getCol();
		int r2 = t2.getRow();
		int c2 = t2.getCol();
		Tile local1 = this.getTile(r1, c1);
		Tile local2 = this.getTile(r2, c2);
		local1.setRow(r2);
		local1.setCol(c2);
		local2.setRow(r1);
		local2.setCol(c1);

	}

	public void setTileSelected(int row, int col) {
		this.getTile(row, col).setSelected(true);
	}

	public boolean selectedExist() {
		for (Tile t : tilelist) {
			if (t.isSelected()) {
				return true;
			}
		}
		return false;
	}

	public void clearSelected() {
		for (Tile t : tilelist) {
			if (t.isSelected()) {
				t.setSelected(false);
				break;
			}
		}
	}

	public Tile getSelectedTile() {
		for (Tile t : tilelist) {
			if (t.isSelected()) {
				return t;
			}
		}
		return new Tile(-1, -1, Color.picture1);
	}

	// public boolean Tile

	public void setTileNotSelected(int row, int col) {
		this.getTile(row, col).setSelected(false);
	}

//	public void addNew(Tile t) {
//		this.newlist.add(t);
//	}

	public void fall(Tile t) {
		if (!t.isWillDelete()) {
			int falldis = t.fallDistance;
			int oldrow = t.getRow();
			int newrow = oldrow + falldis;
			int col = t.getCol();
			t.setCol(col);
			t.setRow(newrow);
		}
	}

	public ArrayList<Tile> getTilelist() {
		return tilelist;
	}

//	public ArrayList<Tile> getBomblist() {
//		return bomblist;
//	}
//
//	public ArrayList<Tile> getFalllist() {
//		return falllist;
//	}
//
//	public ArrayList<Tile> getNewlist() {
//		return newlist;
//	}

}
