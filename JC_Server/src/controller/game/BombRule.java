package controller.game;

import java.util.ArrayList;

public class BombRule {
	
	Board gameBoard = null;
	public BombRule(Board gameBoard){
		this.gameBoard = gameBoard;
	}
	
	public static  ArrayList<ArrayList<Tile>> combineChains(ArrayList<ArrayList<Tile>> chains) {
		int size = chains.size();
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				ArrayList<Tile> chain1 = chains.get(i);
				ArrayList<Tile> chain2 = chains.get(j);
				for (Tile t : chain1) {
					if (chain2.contains(t)) {
						chain2.remove(t);
						chain1.addAll(chain2);
						chain2.clear();
						break;
					}
				}
			}
		}
		return chains;
	}
	
	private int addBombTilesForBlink(Tile center_tile, ArrayList<Tile> bomblist) {
		int score = 0;
		int col = center_tile.getCol();
		int row = center_tile.getRow();

		if (col + 1 <= 8) {
			Tile right = gameBoard.getTile(row, col + 1);
			score += 100;
			if(!bomblist.contains(right))
				bomblist.add(right);

		}
		if (col - 1 >= 0) {
			score += 100;
			Tile left = gameBoard.getTile(row, col - 1);
			if(!bomblist.contains(left))
				bomblist.add(left);
		}

		if (row - 1 >= 0) {
			score += 100;
			Tile above = gameBoard.getTile(row - 1, col);
			if(!bomblist.contains(above))
				bomblist.add(above);
		}

		if (row + 1 <= 8) {
			score += 100;
			Tile below = gameBoard.getTile(row + 1, col);
			if(!bomblist.contains(below))
				bomblist.add(below);
		}
		if ((col - 1 >= 0) && (row - 1 >= 0)) {
			Tile left_above = gameBoard.getTile(row - 1, col - 1);
			score += 100;
			if(!bomblist.contains(left_above))
				bomblist.add(left_above);
		}

		if ((col + 1 <= 8) && (row - 1 >= 0)) {
			Tile right_above = gameBoard.getTile(row - 1, col + 1);
			score += 100;
			if(!bomblist.contains(right_above))
				bomblist.add(right_above);
		}

		if ((col - 1 >= 0) && (row + 1 <= 8)) {
			Tile left_below = gameBoard.getTile(row + 1, col - 1);
			score += 100;
			if(!bomblist.contains(left_below))
				bomblist.add(left_below);
		}

		if ((col + 1 <= 8) && (row + 1 <= 8)) {
			Tile right_below = gameBoard.getTile(row + 1, col + 1);
			if(!bomblist.contains(right_below))
				bomblist.add(right_below);
			score += 100;
		}
		return score;
	}

	private void addBombTilesForBomb(Tile t0, ArrayList<Tile> bomblist) {
		// TODO Auto-generated method stub
		int row = t0.getRow();
		int col = t0.getCol();
		// ArrayList<Tile> bomblist = gameBoard.getBomblist();
		for (int i = 0; i < 9; i++) {
			Tile t1 = gameBoard.getTile(row, i);
			Tile t2 = gameBoard.getTile(i, col);
			if(!bomblist.contains(t1))
				bomblist.add(t1);
			if(!bomblist.contains(t2))
				bomblist.add(t2);
		}
	}
	
	public void addBombTile(Tile t ,ArrayList<Tile> container){
		if(container.contains(t)){
			
		}else{
			container.add(t);
		}
	}
	
	public void addBombTilesAround(Tile t,ArrayList<Tile> container){
		if(t.getType() == Type.normal){
			if(!container.contains(t))
				container.add(t);
			return;
		}
		if(t.getType() == Type.blink){
			if(!container.contains(t))
				container.add(t);
			this.addBombTilesForBlink(t, container);
			return;
		}
		if(t.getType() == Type.bomb){
			if(!container.contains(t)){
				container.add(t);
			}
			this.addBombTilesForBomb(t, container);
			return;
		}
		
	}
	
	@SuppressWarnings("static-access")
	public ArrayList<Tile> getBombList(ArrayList<ArrayList<Tile>> chains){
		chains = this.combineChains(chains);
		ArrayList<Tile> bomblist = new ArrayList<Tile>();
		
		for(ArrayList<Tile> a_chain : chains) {
			Prop.getProps(a_chain);
			for(Tile t : a_chain){
				if(t.isWillDelete()){
					if(!bomblist.contains(t)){
						bomblist.add(t);
					}
					this.addBombTilesAround(t, bomblist);
				}
			}
		}
		
		for(Tile t : bomblist){
			t.setWillDelete(true);
		}
		
		return bomblist;
	}
	public ArrayList<Tile> includePropTiles(ArrayList<Tile> bomblist){
		ArrayList<Tile> newbomblist = new ArrayList<Tile>();
		for(Tile t : bomblist){
			this.addBombTilesAround(t, newbomblist);
		}
		return newbomblist;
	}
}
