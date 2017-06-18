package controller.game;

import java.util.ArrayList;
/**
 * <code><b>Algorithms</b></code> contains the algorithms to find chains and hint tiles that can be chained;
 * 
 * @author 089
 */

public class Algorithms {
	private static final int GameDimension = 9;
	Board gameBoard;
	public ArrayList<ArrayList<Tile>> chains ;//= new ArrayList<ArrayList<Tile>>();

	public Algorithms(Board b) {
		this.gameBoard = b;
	}

	public void initiateGameBoard() {
		// TODO Auto-generated method stub
		while (isSteady(gameBoard).size()!=0) {
			this.removeChains();
		}
	}

	public void removeChains() {
		for (ArrayList<Tile> list : this.chains) {
			int size = list.size();
			int mid = size / 2;
			Tile midt = list.get(mid);
			Color precolor = midt.getColor();
			while (true) {
				Color newcolor = Tile.getRandColor();
				if (precolor != newcolor) {
					midt.setColor(newcolor);
					break;
				}
			}

			for (Tile t : list) {
				t.reset();
			}
		}
		chains.clear();
	}

	public ArrayList<ArrayList<Tile>> isSteady(Board b) {
		ArrayList<ArrayList<Tile>> chains = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> temp1 = checkRow(b);
		ArrayList<ArrayList<Tile>> temp2 = checkColumn(b);
		chains.addAll(temp1);
		chains.addAll(temp2);
		this.chains = chains;
	
		return chains;
	}
	
	public void markDelete(ArrayList<ArrayList<Tile>> list){
		for(ArrayList<Tile>  s: list){
			for(Tile t : s){
				t.setWillDelete(true);
			}
		}
	}

	public ArrayList<ArrayList<Tile>> checkColumn(Board b) {
		ArrayList<ArrayList<Tile>> tempchains = new ArrayList<ArrayList<Tile>>();
		int row, col, tmp;
		for (col = 0; col < Algorithms.GameDimension; col++) {
			for (row = 0; row < Algorithms.GameDimension; row++) {
				Tile start = gameBoard.getTile(row, col);
				ArrayList<Tile> chain = new ArrayList<Tile>(5);
				chain.add(start);
				for (tmp = (row + 1); tmp < Algorithms.GameDimension; tmp++) {
					Tile next = gameBoard.getTile(tmp, col);
					if (next.getColor() == start.getColor()) {
						chain.add(next);
					} else {
						break;
					}
				}
				if (chain.size() > 2) {
					tempchains.add(chain);
				}
				row = tmp-1;
			}
		}
		return tempchains;
	}

	public ArrayList<ArrayList<Tile>> checkRow(Board b) {
		ArrayList<ArrayList<Tile>> tempchains = new ArrayList<ArrayList<Tile>>();
		int row, col, tmp;
		for (row = 0; row < Algorithms.GameDimension; row++) {
			for (col = 0; col < Algorithms.GameDimension; col++) {
				Tile start = gameBoard.getTile(row, col);			
				ArrayList<Tile> chain = new ArrayList<Tile>(5);
				chain.add(start);
				for (tmp = (col + 1); tmp < Algorithms.GameDimension; tmp++) {
					Tile next = gameBoard.getTile(row, tmp);
					if (next.getColor() == (start.getColor())) {// ????
						chain.add(next);
					} else {
						break;
					}
				}
				if (chain.size() > 2) {
					tempchains.add(chain);
				}
				col = tmp-1;
			}
		}
		return tempchains;
	}
	

	// 将无序的ArrayList转换为9X9的二维数组
	public Tile[][] transform() {
		Tile[][] tiles = new Tile[9][9];
		ArrayList<Tile> tileList = gameBoard.getTilelist();
		if (tileList.size() != 81) {
			return null;
		}

		for (int i = 0; i < tileList.size(); i++) {
			Tile t = tileList.get(i);
			tiles[t.getRow()][t.getCol()] = t;
		}

		return tiles;
	}
	private Tile[][] transform(Board b) {
		//在刚做过新棋子的创建时，调用这个方法。
		//此时的Board不是正常的。Tilelist里不一定有81个棋子。
		Tile[][] tiles = new Tile[9][9];
		ArrayList<Tile> tileList = b.getTilelist();
		if (tileList.size() != 81) {
			return null;
		}

		for (int i = 0; i < tileList.size(); i++) {
			Tile t = tileList.get(i);
			tiles[t.getRow()][t.getCol()] = t;
		}

		return tiles;
	}


	// 判断横纵坐标是否越界，不越界则返回一个Tile对象，越界则返回null
	public Tile judgeIndex(Tile[][] tiles, int x, int y) {
		if (x < 0 || x > 8 || y < 0 || y > 8) {
			return null;
		} else {
			return tiles[x][y];
		}
	}

	// 返回三个提示tile
	public ArrayList<Tile> hint() {
		Tile[][] tiles = transform();
		ArrayList<Tile> hintList = new ArrayList<Tile>();

		if (tiles == null) {
			return hintList;
		}

		// 寻找第一种类型，两个横着的相同的棋子间隔，中间位置上方或下方可以移动，2种情况
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i][j + 2];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i - 1, j + 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i + 1, j + 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}
				}
			}
		}

		// 寻找第二种类型，两个竖着的相同的棋子间隔，中间位置左边或右边可以移动，2种情况
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 9; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i + 2][j];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i + 1, j - 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i + 1, j + 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}
				}
			}
		}

		// 寻找第三种类型，两个横着的相邻棋子相同，两边移动，共6种情况
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i][j + 1];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i - 1, j - 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i + 1, j - 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}

					Tile option3 = judgeIndex(tiles, i, j - 2);
					if (option3 != null) {
						if (t1.getColor().equals(option3.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option3);
							return hintList;
						}
					}

					Tile option4 = judgeIndex(tiles, i - 1, j + 2);
					if (option4 != null) {
						if (t1.getColor().equals(option4.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option4);
							return hintList;
						}
					}

					Tile option5 = judgeIndex(tiles, i + 1, j + 2);
					if (option5 != null) {
						if (t1.getColor().equals(option5.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option5);
							return hintList;
						}
					}

					Tile option6 = judgeIndex(tiles, i, j + 3);
					if (option6 != null) {
						if (t1.getColor().equals(option6.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option6);
							return hintList;
						}
					}

				}
			}
		}

		// 寻找第四种类型，两个竖着的相邻棋子相同，上下两边移动，共6种情况
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 9; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i + 1][j];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i - 1, j - 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i - 1, j + 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}

					Tile option3 = judgeIndex(tiles, i - 2, j);
					if (option3 != null) {
						if (t1.getColor().equals(option3.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option3);
							return hintList;
						}
					}

					Tile option4 = judgeIndex(tiles, i + 2, j - 1);
					if (option4 != null) {
						if (t1.getColor().equals(option4.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option4);
							return hintList;
						}
					}

					Tile option5 = judgeIndex(tiles, i + 2, j + 1);
					if (option5 != null) {
						if (t1.getColor().equals(option5.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option5);
							return hintList;
						}
					}

					Tile option6 = judgeIndex(tiles, i + 3, j);
					if (option6 != null) {
						if (t1.getColor().equals(option6.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option6);
							return hintList;
						}
					}

				}
			}
		}

		return hintList;
	}

	public ArrayList<Tile> hint(Board b) {
		
		Board temp = b.clone();
		
				
		Tile[][] tiles = transform(temp);
		ArrayList<Tile> hintList = new ArrayList<Tile>();

		if (tiles == null) {
			return null;
		}

		// 寻找第一种类型，两个横着的相同的棋子间隔，中间位置上方或下方可以移动，2种情况
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i][j + 2];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i - 1, j + 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i + 1, j + 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}
				}
			}
		}

		// 寻找第二种类型，两个竖着的相同的棋子间隔，中间位置左边或右边可以移动，2种情况
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 9; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i + 2][j];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i + 1, j - 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i + 1, j + 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}
				}
			}
		}

		// 寻找第三种类型，两个横着的相邻棋子相同，两边移动，共6种情况
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 8; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i][j + 1];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i - 1, j - 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i + 1, j - 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}

					Tile option3 = judgeIndex(tiles, i, j - 2);
					if (option3 != null) {
						if (t1.getColor().equals(option3.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option3);
							return hintList;
						}
					}

					Tile option4 = judgeIndex(tiles, i - 1, j + 2);
					if (option4 != null) {
						if (t1.getColor().equals(option4.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option4);
							return hintList;
						}
					}

					Tile option5 = judgeIndex(tiles, i + 1, j + 2);
					if (option5 != null) {
						if (t1.getColor().equals(option5.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option5);
							return hintList;
						}
					}

					Tile option6 = judgeIndex(tiles, i, j + 3);
					if (option6 != null) {
						if (t1.getColor().equals(option6.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option6);
							return hintList;
						}
					}

				}
			}
		}

		// 寻找第四种类型，两个竖着的相邻棋子相同，上下两边移动，共6种情况
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 9; j++) {
				Tile t1 = tiles[i][j];
				Tile t2 = tiles[i + 1][j];

				if (t1.getColor().equals(t2.getColor())) {
					Tile option1 = judgeIndex(tiles, i - 1, j - 1);
					if (option1 != null) {
						if (t1.getColor().equals(option1.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option1);
							return hintList;
						}
					}

					Tile option2 = judgeIndex(tiles, i - 1, j + 1);
					if (option2 != null) {
						if (t1.getColor().equals(option2.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option2);
							return hintList;
						}
					}

					Tile option3 = judgeIndex(tiles, i - 2, j);
					if (option3 != null) {
						if (t1.getColor().equals(option3.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option3);
							return hintList;
						}
					}

					Tile option4 = judgeIndex(tiles, i + 2, j - 1);
					if (option4 != null) {
						if (t1.getColor().equals(option4.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option4);
							return hintList;
						}
					}

					Tile option5 = judgeIndex(tiles, i + 2, j + 1);
					if (option5 != null) {
						if (t1.getColor().equals(option5.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option5);
							return hintList;
						}
					}

					Tile option6 = judgeIndex(tiles, i + 3, j);
					if (option6 != null) {
						if (t1.getColor().equals(option6.getColor())) {
							hintList.add(t1);
							hintList.add(t2);
							hintList.add(option6);
							return hintList;
						}
					}

				}
			}
		}
		if(hintList.size()==0){
			System.out.println("======= Dead End ==========" );
			
		}
		return hintList;
	}
	
	public void removeChains(ArrayList<ArrayList<Tile>>  chains) {
		for (ArrayList<Tile> list : chains) {
			int size = list.size();
			int mid = size / 2;
			Tile midt = list.get(mid);
			Color precolor = midt.getColor();
			while (true) {
				Color newcolor = Tile.getRandColor();
				if (precolor != newcolor) {
					midt.setColor(newcolor);
					break;
				}
			}

			for (Tile t : list) {
				t.reset();
			}
		}
		chains.clear();
	}

	public boolean canSwap(Tile t1, Tile t2) {
		gameBoard.swapTile(t1, t2);

		boolean steady = this.isSteady(gameBoard).isEmpty();

		gameBoard.swapTile(t1, t2);

		this.chains.clear();
		for(Tile t : gameBoard.getTilelist()){
			t.reset();
		}
		return !steady;
	}
	public boolean isDeadEnd(Board b){
		boolean isDead =false;
		ArrayList<Tile> hintlist = this.hint(b);
		isDead = hintlist.isEmpty();
		return isDead;
	}

	public void solveDeadEnd(Board b) {
		// TODO Auto-generated method stub
		Tile t = Tile.getRandomPosition();
		int row = t.getRow();
		int col = t.getCol();
		Tile lucky_tile = b.getTile(row, col);
		lucky_tile.settype(Type.blink);
	}

}
