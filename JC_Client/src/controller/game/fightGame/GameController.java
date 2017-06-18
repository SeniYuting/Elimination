package controller.game.fightGame;
import java.util.ArrayList;
import java.util.Calendar;

import controller.game.Algorithms;
import controller.game.Board;
import controller.game.BombRule;
import controller.game.GameSituationException;
import controller.game.ReqResult;
import controller.game.Score;
import controller.game.Tile;
import controller.game.TimeControl;
import controller.game.Type;
import controllerservice.game.GameControllerService;
/**
 * <code><b>GameController</b></code> contains Logic Process for PvP game,mainly about creating controller with a board
 * provided by View layer;
 *  
 * @author  089
 */
public class GameController  implements GameControllerService{
	int SUPERMODETIME = 5;
	int GameDimension = 9 ;
	int TIMEDELAY = 1;// 无敌模式的计算时间
	BombRule br;
	Board gameBoard;
	Algorithms alg;
	int score = 0;
	boolean supermode = false;
	int combo_count = 0;
	int max_combo_count = 0;
	Calendar last_success_swap;
	Calendar last_super_mode ;
	Score s;

	// int last_minute = 0;
	// int last_second = 0;

	public GameController(Board b) {
		last_success_swap = Calendar.getInstance();
		last_super_mode = Calendar.getInstance();
		gameBoard = b;
		br = new BombRule(gameBoard);
		alg = new Algorithms(gameBoard);
		while (true) {
			ArrayList<ArrayList<Tile>> temp = alg.isSteady(gameBoard);
			if (!temp.isEmpty()) {
				alg.removeChains(temp);
			} else {
				break;
			}
		}
	}
	
	public boolean isSuperMode  (){
		return this.supermode;
	}


	public boolean isBoardSteady() {
		return alg.isSteady(gameBoard).isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.game.GameControllerService#calculateDrop()
	 */
	private void calculateDropDown(Board localboard, ArrayList<Tile> bomblist) {
		int row, col, temp;
		for (Tile bottom : bomblist) {
			row = bottom.getRow();
			col = bottom.getCol();
			for (temp = row; temp >= 0; temp--) {
				Tile above = localboard.getTile(temp, col);
				above.setWillDrop(true);
				above.fallDistance++;
			}
		}
	}

	private void calculateDropRight(Board localboard, ArrayList<Tile> bomblist) {
		int row, col, temp;
		for (Tile bottom : bomblist) {
			row = bottom.getRow();
			col = bottom.getCol();
			for (temp = col; temp >= 0; temp--) {
				Tile left = localboard.getTile(row, temp);
				left.setWillDrop(true);
				left.fallDistance++;
			}
		}
	}

	public void calculateDrop(Board gameBoard, ArrayList<Tile> bomblist) {

		boolean isLeftToRight = false;

		if (isLeftToRight) {
			calculateDropRight(gameBoard, bomblist);
		} else {
			calculateDropDown(gameBoard, bomblist);
		}
	}

	
	public boolean checkProp() {

		return false;
	}

	public ArrayList<Tile> initGame() {
		ArrayList<Tile> temp_list = new ArrayList<Tile>();
		for (Tile t : gameBoard.getTilelist()) {
			temp_list.add(t.clone());
		}
		return temp_list;
	}

	public ArrayList<ReqResult> swap(Tile t1, Tile t2) {
		gameBoard.swapTile(t1, t2);
		ArrayList<ReqResult> animation_queue = new ArrayList<ReqResult>();
		ReqResult result = null;
		if (alg.isSteady(gameBoard).size() == 0) {
			// 特殊情况单独处理是为了快一点，算法优化。
			gameBoard.swapTile(t1, t2);
			result = new ReqResult(gameBoard.getTilelist(),new ArrayList<Tile>());
			animation_queue.add(result);
			return animation_queue;
		}

		animation_queue.addAll(this.makeBoardSteady());

		return animation_queue;

	}

	
	private ArrayList<Tile> creatNewTilesForDown(Board gameBoard,
			ArrayList<Tile> bomblist) {

		ArrayList<Tile> newlist = new ArrayList<Tile>();
		for (Tile t : bomblist) {
			int row = t.getRow();
			int col = t.getCol();
			int newTileRow = -1;
			for (int temprow = row + 1; temprow < GameDimension; temprow++) {
				Tile below_tile = gameBoard.getTile(temprow, col);
				if (below_tile.isWillDelete() || bomblist.contains(below_tile)) {
					newTileRow--;
				}
			}
			int fall_distance = gameBoard.getTile(0, col).fallDistance;
			Tile newt = Tile.getRandomTile(newTileRow, col);
			newt.fallDistance = fall_distance;
			newt.setWillDrop(true);
			newlist.add(newt);
		}
		return newlist;
	}

	public ArrayList<Tile> createNewTiles(Board b, ArrayList<Tile> bomblist) {

			return this.creatNewTilesForDown(b, bomblist);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.game.GameControllerService#getRemind(controller.game.Board)
	 */

	public ArrayList<Tile> getRemind() {
		ArrayList<Tile> remindList = alg.hint();
		return remindList;
	}

	private ReqResult updateUnsteadyBoard(Board gameboard,ArrayList<ArrayList<Tile>> chains) {
		ArrayList<Tile> bomblist = br.getBombList(chains);
		Score s = new Score(chains);
		return this.updateBoardWithBombTiles(gameboard, bomblist, s.getScore());
	}

	private ReqResult updateBoardWithBombTiles(Board gameboard,
			ArrayList<Tile> b_bomblist,int s) {
		// Solution :
		// 根据chains 新鲜的 整合一下，获得bomb list 然后计算掉落 然后创建新棋子（复制此时的tile list，bomb
		// list） 然后将棋盘复位（81个棋子）
		// ===============================================
		this.recordCombo();
		this.calculateDrop(gameboard, b_bomblist);

		ArrayList<Tile> b_newlist = this.createNewTiles(gameboard, b_bomblist);
		// ===============================================
		ArrayList<Tile> tile_list = gameboard.getTilelist();
		for (Tile t : b_bomblist) {
			tile_list.remove(t);
		}
		for (Tile t : b_newlist) {
			tile_list.add(t);
		}
		ArrayList<Tile> cloned_list = new ArrayList<Tile>();
		for (Tile t : tile_list) {
			cloned_list.add(t.clone());
		}
		ArrayList<Tile> cloned_bomb_list = new ArrayList<Tile>();
		for (Tile t : b_bomblist) {
			cloned_bomb_list.add(t.clone());
		}
		if(supermode){
			s = s*2;
		}
		ReqResult result = new ReqResult(cloned_list, cloned_bomb_list,s);
		this.resetBoard(gameboard, b_bomblist);
		return result;
	}


//	

	public boolean canSwap(Tile t1, Tile t2) {
		gameBoard.swapTile(t1, t2);
		ArrayList<ArrayList<Tile>> temp1 = alg.checkRow(this.gameBoard);
		ArrayList<ArrayList<Tile>> temp2 = alg.checkColumn(this.gameBoard);
		boolean steady = false;
		steady = temp1.isEmpty() && temp2.isEmpty();
		gameBoard.swapTile(t1, t2);
		return !steady;
	}

	public ArrayList<ReqResult> checkTile(Tile t0)
			throws GameSituationException {
		// 笑脸道具的处理
		if (t0.getType() == Type.blink) {
			return this.clickBlinkTile(t0);
		}
		// 爆炸猪的处理
		if (t0.getType() == Type.bomb) {
			return this.clickBombTile(t0);
		}
		return null;
	}

	private ArrayList<ReqResult> clickBombTile(Tile t0) {
		BombRule br = new BombRule(gameBoard);
		ArrayList<ReqResult> resultlist = new ArrayList<ReqResult>();
		ArrayList<Tile> bomblist = new ArrayList<Tile>();
		int col = t0.getCol();
		int row = t0.getRow();
		this.recordCombo();
		Tile real_tile = gameBoard.getTile(row, col);
		bomblist.add(real_tile);
		real_tile.setWillDelete(true);
		br.addBombTilesAround(real_tile, bomblist);
		int score = 1700;
		if(supermode){
			score *=2;
		}
		ReqResult temp1 = this.updateBoardWithBombTiles(gameBoard, bomblist,score);
		resultlist.add(temp1);

		resultlist.addAll(this.makeBoardSteady());

		return resultlist;
	}

	private ArrayList<ReqResult> clickBlinkTile(Tile t0) {
		ArrayList<ReqResult> resultlist = new ArrayList<ReqResult>();
		ArrayList<Tile> bomblist = new ArrayList<Tile>();
		int col = t0.getCol();
		int row = t0.getRow();
		this.recordCombo();
		Tile real_tile = gameBoard.getTile(row, col);
		bomblist.add(real_tile);
		real_tile.setWillDelete(true);
		br.addBombTilesAround(real_tile, bomblist);
		int score = 900;
		if(supermode){
			score *=2;
		}
		ReqResult temp1 = this.updateBoardWithBombTiles(gameBoard, bomblist,score);
		resultlist.add(temp1);
		
		resultlist.addAll(this.makeBoardSteady());

		return resultlist;
	}

	public void resetBoard(Board b, ArrayList<Tile> droplist) {
		boolean isFromLeftToRight = false;
		for (Tile t : b.getTilelist()) {
			int fall = t.fallDistance;
			if (fall != 0) {
				int old_row = t.getRow();
				int old_col = t.getCol();
				if (!isFromLeftToRight)
					t.setRow(old_row + fall);
				else
					t.setCol(old_col + fall);
			}
			t.reset();
		}
		if(alg.isDeadEnd(gameBoard)){
			alg.solveDeadEnd(gameBoard);
		}
	}

	public ArrayList<ReqResult> useSharkProp() {
		// 鲨鱼导弹，动画队列的计算
		ArrayList<ReqResult> animation_queue = new ArrayList<ReqResult>();
		ArrayList<Tile> bomblist = new ArrayList<Tile>();
		for (int col = 0; col < 9; col++) {
			int row = 8;
			Tile t = gameBoard.getTile(row, col);
			br.addBombTile(t, bomblist);
		}
		for (int row = 0; row < 9; row++) {
			int col = 4;
			Tile t = gameBoard.getTile(row, col);
			br.addBombTile(t, bomblist);
		}

		ReqResult rr;
		rr = this.updateBoardWithBombTiles(gameBoard, bomblist,1700);

		animation_queue.add(rr);
		animation_queue.addAll(this.makeBoardSteady());

		return animation_queue;
	}
	
	public ArrayList<ReqResult> makeBoardSteady() {
		ArrayList<ReqResult> animation_queue = new ArrayList<ReqResult>();
		while (alg.isSteady(gameBoard).size() != 0) {
			ArrayList<ArrayList<Tile>> chains = alg.chains;
			ReqResult temp = this.updateUnsteadyBoard(gameBoard, chains);
			animation_queue.add(temp);
		}
		return animation_queue;
	}
	public void recordCombo() {
		Calendar curren_time = Calendar.getInstance();
		int detatime = TimeControl.getDetaSeconds(curren_time, last_success_swap);
		
		if (detatime > TIMEDELAY) {
			if (combo_count > max_combo_count) {
				max_combo_count = combo_count;
			}
			combo_count = 0;
		} else {
			this.combo_count++;
		}
		
		if (combo_count >= 4) {
			this.supermode = true;
			System.out.println("Into Super Mode");
			this.last_super_mode = curren_time;
		}else{
			if(TimeControl.getDetaSeconds(curren_time, last_super_mode)>this.SUPERMODETIME){
				//如果时间超过了超级模式的时间，并且没有形成4连击
				this.supermode = false;
				System.out.println("Quit Super Mode");
			}
		}

		this.last_success_swap = curren_time;
	}

	public ArrayList<ReqResult> timeUp() {
		ArrayList<ReqResult> resultlist = new ArrayList<ReqResult>();
		ArrayList<Tile> specialTiles = new ArrayList<Tile>();
		// ===============================
		for (Tile t : this.gameBoard.getTilelist()) {
			if (t.getType() != Type.normal) {
				specialTiles.add(t);
			}
		}
		// ===============================
		for (Tile t : specialTiles) {
			ArrayList<ReqResult> animation_queue = null;
			try {
				animation_queue = this.checkTile(t);
			} catch (GameSituationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultlist.addAll(animation_queue);
		}

		return resultlist;
	}

}
