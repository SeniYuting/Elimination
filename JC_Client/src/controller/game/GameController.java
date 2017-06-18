package controller.game;

import java.util.ArrayList;
import java.util.Calendar;

import view.game.GamePanel;
import controllerservice.game.GameControllerService;
/**
 * <code><b>GameController</b></code> 离线模式下和联网单机模式下的单机游戏控制器。
 * 
 * @author 089
 */

public class GameController implements GameControllerService {
	int SUPERMODETIME = 5;
	int TIMEDELAY = 1;// 无敌模式的计算间隔
	BombRule br;
	Board gameBoard;
	Algorithms alg;
	GamePanel panel;
	int score = 0;
	boolean supermode = false;
	int combo_count = 0;
	int max_combo_count = 0;
	Calendar last_success_swap;
	Calendar last_super_mode ;

	// int last_minute = 0;
	// int last_second = 0;

	public GameController(GamePanel panel) {
		last_success_swap = Calendar.getInstance();
		last_super_mode = Calendar.getInstance();
		gameBoard = new Board();
		br = new BombRule(gameBoard);
		alg = new Algorithms(gameBoard);
		this.panel = panel;
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

	public GameController(GamePanel panel, Board board) {
		gameBoard = board;
		alg = new Algorithms(gameBoard);
		this.panel = panel;
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

		boolean isLeftToRight = panel.isFromLeft();

		if (isLeftToRight) {
			calculateDropRight(gameBoard, bomblist);
		} else {
			calculateDropDown(gameBoard, bomblist);
		}
	}

	public boolean checkProp() {

		return false;
	}
	/**
	 * Contact the <code><b>GameControllerService</code></b> to initGame.
	 *  初始化一张棋盘的棋子列表，要求期盼稳定；
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public ArrayList<Tile> initGame() {
		ArrayList<Tile> temp_list = new ArrayList<Tile>();
		for (Tile t : gameBoard.getTilelist()) {
			temp_list.add(t.clone());
		}
		return temp_list;
	}
	/**
	 * Contact the <code><b>GameControllerService</code></b> to swap.
	 *  返回交换了这两个棋子之后的动画队列，如果不能消除，则动画队列为原先的棋盘状态
	 * @see GameControllerService
	 * @param t1, t2
	 * @return  ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> swap(Tile t1, Tile t2) {
		gameBoard.swapTile(t1, t2);
		ArrayList<ReqResult> animation_queue = new ArrayList<ReqResult>();
		ReqResult result = null;
		if (alg.isSteady(gameBoard).size() == 0) {
			// 特殊情况单独处理是为了快一点，算法优化。
			gameBoard.swapTile(t1, t2);
			result = new ReqResult(gameBoard.getTilelist(),
					new ArrayList<Tile>());
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
			for (int temprow = row + 1; temprow < GAMEDIMENSION; temprow++) {
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

	private ArrayList<Tile> creatNewTilesForRight(Board gameBoard,
			ArrayList<Tile> bomblist) {
		ArrayList<Tile> newlist = new ArrayList<Tile>();

		for (Tile t : bomblist) {
			int row = t.getRow();
			int col = t.getCol();
			int newtilecolumn = -1;
			for (int tempcol = col + 1; tempcol < GAMEDIMENSION; tempcol++) {
				Tile right_tile = gameBoard.getTile(row, tempcol);
				if (bomblist.contains(right_tile)) {
					newtilecolumn--;
				}
			}

			int fall_distance = gameBoard.getTile(row, 0).fallDistance;
			Tile newt = Tile.getRandomTile(row, newtilecolumn);
			newt.fallDistance = fall_distance;
			newt.setWillDrop(true);
			newlist.add(newt);
		}
		return newlist;
	}

	public ArrayList<Tile> createNewTiles(Board b, ArrayList<Tile> bomblist) {

		if (panel.isFromLeft()) {
			return this.creatNewTilesForRight(b, bomblist);
		} else {
			return this.creatNewTilesForDown(b, bomblist);
		}
		// 避免死局
	}

	/**
	 * Contact the <code><b>GameControllerService</code></b> to getRemind.
	 *  获取提示可以消除的棋子的位置。
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<Tile>
	 */
	public ArrayList<Tile> getRemind() {
		ArrayList<Tile> remindList = alg.hint();
		return remindList;
	}

	private ReqResult updateUnsteadyBoard(Board gameboard,
			ArrayList<ArrayList<Tile>> chains) {
		ArrayList<Tile> bomblist = br.getBombList(chains);
		bomblist = br.includePropTiles(bomblist);
		Score s = new Score(chains);
		this.recordCombo();
		this.updateScore(s);
		return this.updateBoardWithBombTiles(gameboard, bomblist);
	}

	private ReqResult updateBoardWithBombTiles(Board gameboard,
			ArrayList<Tile> b_bomblist) {
		// Solution :
		// 根据chains 新鲜的 整合一下，获得bomb list 然后计算掉落 然后创建新棋子（复制此时的tile list，bomb
		// list） 然后将棋盘复位（81个棋子）
		// ===============================================
		this.calculateDrop(gameboard, b_bomblist);
		
			for(Tile t : b_bomblist){
				if(t.getType() == Type.time){
					panel.addTime();
					System.out.println("Add time");
				}
			}
		
		ArrayList<Tile> b_newlist = this.createNewTiles(gameboard, b_bomblist);
		// ===============================================
		ArrayList<Tile> tile_list = gameboard.getTilelist();
		for (Tile t : b_bomblist) {
			tile_list.remove(t);
		}
		for (Tile t : b_newlist) {
			tile_list.add(t);
		}
		// ArrayList<Tile> b_droplist = this.getDropTileList(gameboard);

		ArrayList<Tile> cloned_list = new ArrayList<Tile>();
		for (Tile t : tile_list) {
			cloned_list.add(t.clone());
		}
		ArrayList<Tile> cloned_bomb_list = new ArrayList<Tile>();
		for (Tile t : b_bomblist) {
			cloned_bomb_list.add(t.clone());
		}

		ReqResult result = new ReqResult(cloned_list, cloned_bomb_list);
		this.resetBoard(gameboard, b_bomblist);
		return result;
	}

	private void updateScore(int score) {
		if(isSuperMode()){
			score *= 2;
		}
		panel.updateScore(score);
	}

	private void updateScore(Score s) {
		// TODO Auto-generated method stub

		int score = s.getScore();
		if(supermode){
			score *= 2 ;
		}
		
		panel.updateScore(score);

	}
	
	public void recordCombo() {
		Calendar curren_time = Calendar.getInstance();
		int detatime = TimeControl.getDetaSeconds(curren_time, last_success_swap);
		
		if (detatime > TIMEDELAY) {
			if (combo_count > max_combo_count) {
				max_combo_count = combo_count;
//				System.out.println("Max combo is  +"+max_combo_count);
			}
			combo_count = 0;
		} else {
			this.combo_count++;
		}
		
		if (combo_count >= 4) {
			this.supermode = true;
//			System.out.println("Into Super Mode");
			this.last_super_mode = curren_time;
		}else{
			if(TimeControl.getDetaSeconds(curren_time, last_super_mode)>this.SUPERMODETIME){
				//如果时间超过了超级模式的时间，并且没有形成4连击
				this.supermode = false;
//				System.out.println("Quit Super Mode");
			}
		}

		this.last_success_swap = curren_time;
	}

	/**
	 * Contact the <code><b>GameControllerService</code></b> to canSwap.
	 *  返回这两个棋子是否可以交换，即：交换了之后能否形成消除。
	 * @see GameControllerService
	 * @param t1, t2
	 * @return boolean
	 */
	public boolean canSwap(Tile t1, Tile t2) {
//		Board tempb = gameBoard.clone();
		gameBoard.swapTile(t1, t2);
		ArrayList<ArrayList<Tile>> temp1 = alg.checkRow(gameBoard);
		ArrayList<ArrayList<Tile>> temp2 = alg.checkColumn(gameBoard);
		boolean steady = false;
		steady = temp1.isEmpty() && temp2.isEmpty();
		gameBoard.swapTile(t1, t2);
		return !steady;
	}
	/**
	 * Contact the <code><b>GameControllerService</code></b> to checkTile.
	 *  点击了道具之后调用这个方法，对被点击的棋子进行检查，返回动画队列。
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
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
		Tile real_tile = gameBoard.getTile(row, col);
		bomblist.add(real_tile);
		real_tile.setWillDelete(true);
		br.addBombTilesAround(real_tile, bomblist);
		this.recordCombo();
		this.recordCombo();
		int score = 1700;
		if(supermode){
			score *= 2 ;
		}
		this.updateScore(score);
		bomblist = br.includePropTiles(bomblist);
		ReqResult temp1 = this.updateBoardWithBombTiles(gameBoard, bomblist);
		resultlist.add(temp1);

		resultlist.addAll(this.makeBoardSteady());

		return resultlist;
	}

	private ArrayList<ReqResult> clickBlinkTile(Tile t0) {
		ArrayList<ReqResult> resultlist = new ArrayList<ReqResult>();
		ArrayList<Tile> bomblist = new ArrayList<Tile>();
		int col = t0.getCol();
		int row = t0.getRow();
		Tile real_tile = gameBoard.getTile(row, col);
		bomblist.add(real_tile);
		real_tile.setWillDelete(true);
		br.addBombTilesAround(real_tile, bomblist);
		// this.updateScore(score);
	
		this.recordCombo();
		int score = 900;
		if(supermode){
			score *= 2 ;
		}
		this.updateScore(score);
		bomblist = br.includePropTiles(bomblist);
		ReqResult temp1 = this.updateBoardWithBombTiles(gameBoard, bomblist);
		resultlist.add(temp1);

		resultlist.addAll(this.makeBoardSteady());

		return resultlist;
	}
	
	/**
	 * Contact the <code><b>GameControllerService</code></b> to timeUp.
	 *  结束时，对每一个特殊道具进行使用，返回动画队列；
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
	public ArrayList<ReqResult> timeUp() {
		ArrayList<ReqResult> resultlist = new ArrayList<ReqResult>();
		ArrayList<Tile> specialTiles = new ArrayList<Tile>();
		// ===============================
		for (Tile t : this.gameBoard.getTilelist()) {
			if (t.getType() == Type.blink  || t.getType()==Type.bomb) {
				specialTiles.add(t);
			}
		}
		// ===============================
		for (Tile t : specialTiles) {
			ArrayList<ReqResult> animation_queue = new ArrayList<ReqResult>();
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

	public void resetBoard(Board b, ArrayList<Tile> droplist) {
		boolean isFromLeftToRight = panel.isFromLeft();
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
		
		createTimeProp();
		
		if(alg.isDeadEnd(gameBoard)){
			alg.solveDeadEnd(gameBoard);
		}
		
	}
	
	public void createTimeProp(){
		int Poss=5;
		boolean useTimeProp = panel.isBack();  //是否需要生成一个时光倒流的道具，需要panel的方法
		if(useTimeProp){
			double x = Math.random();
			
			int m = (int) (x*Poss);
//			System.out.println(m);
			if(m == 1){
				Tile t = Tile.getRandomPosition();
				int row = t.getRow();
				int col = t.getCol();
//				System.out.println("Making a Time Prop at " + row +" , " +col);
				 Tile tempt = gameBoard.getTile(row, col);
				 tempt.settype( Type.time);
			}	
		}
	}
	
	/**
	 * Contact the <code><b>GameControllerService</code></b> to useSharkProp.
	 *  点击了鲨鱼导弹道具之后调用这个方法，返回动画队列；
	 * @see GameControllerService
	 * @param 
	 * @return ArrayList<ReqResult>
	 */
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
		this.updateScore(1700);
		bomblist = br.includePropTiles(bomblist);
		rr = this.updateBoardWithBombTiles(gameBoard, bomblist);

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

}
