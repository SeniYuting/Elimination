package controller.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int size = 9;
	private ArrayList<Tile> tilelist = new ArrayList<Tile>();

		public Board(){
			for (int i = 0; i < Board.size; i++) {
				for (int j = 0; j < Board.size; j++) {
					Tile t = Tile.getRandomTile(i, j);
					tilelist.add(t);
				}
			}
		}
	public void addBombTiles(Tile t){
		t.setWillDelete(true);
//		if(!bomblist.contains(t)){
//			bomblist.add(t);
//		}
	}
	public Board clone(){
		//部分克隆，暂时只用到了 tilelist  所以只clone这部分。
		//发现还需要newlist的克隆
		//还需要bomblist
		
		Board cloned_board = new Board();
		cloned_board.getTilelist().clear();
		for(Tile t : this.tilelist){
			cloned_board.getTilelist().add(t.clone());
		}	

		return cloned_board;
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
		for(Tile t : tilelist){
			if((t.getRow() == row )&& (t.getCol() == col)){
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
		//System.out.println("Exchange  ( "+r1+","+c1+" ) and ( "+r2+","+c2+" )");
		t1.setRow(r2);
		t1.setCol(c2);
		t2.setRow(r1);
		t2.setCol(c1);

		Tile local1 = this.getTile(r1, c1);
		Tile local2 = this.getTile(r2, c2);
		local1.setRow(r2);
		local1.setCol(c2);
		local2.setRow(r1);
		local2.setCol(c1);

	}

	public void setTileSelected(int row,int col){
		this.getTile(row, col).setSelected(true);
	}
	
	public boolean selectedExist() {
		for(Tile t : tilelist){
			if(t.isSelected()){
				return true;
			}
		}
		return false;
	}
	
	public void clearSelected(){
		for(Tile t : tilelist){
			if(t.isSelected()){
				t.setSelected(false);
				break;
			}
		}
	}
	
	public Tile getSelectedTile(){
		for(Tile t : tilelist){
			if(t.isSelected()){
				return t;
			}
		}
		return new Tile(-1, -1, Color.picture1);
	}

	public void setTileNotSelected(int row,int col){
		this.getTile(row, col).setSelected(false); 
	}
	

	public void fall(Tile t){
		if(!t.isWillDelete()){
			int falldis = t.fallDistance ;
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
}
