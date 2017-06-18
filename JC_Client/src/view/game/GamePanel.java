package view.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import localdata.LocalDataHelper;
import net.SocketClient;
import netservice.SocketClientService;
import po.user.UserPO;
import view.component.MyJButton;
import view.component.PanelController;
import view.component.RButton;
import view.props.ChoosePropsPanel;
import controller.game.GameController;
import controller.game.GameSituationException;
import controller.game.ReqResult;
import controller.game.Tile;
import controller.game.Type;
import controller.game.fallDirection;
import controller.props.PropsController;
import controller.user.UpdateController;
import controllerservice.game.GameControllerService;
import controllerservice.props.PropsControllerService;
import controllerservice.user.UpdateControllerService;

public class GamePanel extends JPanel implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L;

	RButton pause, exit;
	JLabel scoreLabel;
	public JProgressBar progressBar;
	ArrayList<ReqResult> reqList;
	ArrayList<Tile> tileList;
	ArrayList<Tile> bombList;
	GameControllerService service;
	Toolkit kit;
	Animation animation;
	Music music;
	double remindTimer;// 这是判断是否提醒的timer
	double remindTime;// 2或3,根据道具判断
	double combTimer;// 判断是否进入超级模式(得分up);记录上次消除的时间
	double playTimer;// 每局时间
	int comb;// 连击次数
	ArrayList<Tile> remindList;
	boolean islocked;// 下落过程中不可消除
	boolean isPaused;
	boolean scoreup;
	boolean superMode;// 是否超级模式
	int maxComb;// 最大连击次数
	int score;// 总得分
	Timer progressTimer;
	int fieldtime;// 每局时间

	boolean isLeftToRight; // 掉落方向，true表示从左往右
	int systemMusic; // 音乐音量
	int systemSound; // 音效音量

	boolean isUp;
	boolean isPrompt;
	boolean isChain;
	private boolean isBack;
	boolean isShark;

	Image tlgrd, bacgrd, selected, bombImage, propsA, propsB, superimg, stop,
			icon, clock;
	HashMap<controller.game.Color, Image> tileImage;

	String userName;
	int mode;

	JLabel overScoreLabel, coinLabel, comboLabel;
	JLabel labelG;
	SharkListener shark;
	MyJButton overImg;

	public GamePanel(String userName, int mode, boolean isLeftToright,
			boolean isUp, boolean isPrompt, boolean isChain, boolean isBack,
			boolean isShark) {
		super();
		this.userName = userName;
		this.mode = mode;

		this.isUp = isUp;
		this.isPrompt = isPrompt;
		this.isChain = isChain;
		this.setBack(isBack);
		this.isShark = isShark;

		if (isPrompt) {
			remindTime = 2;
		}
		scoreup = isUp;

		maxComb = 0;
		comb = 0;
		combTimer = 0;
		superMode = false;

		setLayout(null);
		setComp();
		setBounds(0, 0, 1016, 678);
		this.isLeftToRight = isLeftToright;
		service = new GameController(this);
		InitGamePanel();
		setVisible(true);
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setMusicAndSound(int systemMusic, int systemSound) {
		this.systemMusic = systemMusic;
		this.systemSound = systemSound;
	}

	public boolean isSuperMode() {
		return superMode;
	}

	public void InitGamePanel() {
		Thread t = new Thread(this);
		t.start();
		kit = Toolkit.getDefaultToolkit();
		initImg();
		tileList = service.initGame();
		bombList = new ArrayList<Tile>();
		GameListener listener = new GameListener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		animation = new Animation();
		remindTimer = 0;
		playTimer = 0;
		fieldtime = 0;
		remindTime = 3;
		remindList = new ArrayList<Tile>();
		islocked = false;
		isPaused = false;
		score = 0;
		progressTimer = new Timer(1000, this);
		progressTimer.start();
	}

	public void initImg() {
		tileImage = new HashMap<>();
		tileImage.put(controller.game.Color.picture1,
				kit.getImage("img/game/picture1.png"));
		tileImage.put(controller.game.Color.picture2,
				kit.getImage("img/game/picture2.png"));
		tileImage.put(controller.game.Color.picture3,
				kit.getImage("img/game/picture3.png"));
		tileImage.put(controller.game.Color.picture4,
				kit.getImage("img/game/picture4.png"));
		tileImage.put(controller.game.Color.picture5,
				kit.getImage("img/game/picture5.png"));
		tileImage.put(controller.game.Color.picture6,
				kit.getImage("img/game/picture6.png"));
		tileImage.put(controller.game.Color.picture7,
				kit.getImage("img/game/picture7.png"));
		tlgrd = kit.getImage("img/game/bg2.jpg");
		bacgrd = kit.getImage("img/BackGround.jpg");
		selected = kit.getImage("img/game/focus.png");
		bombImage = kit.getImage("img/game/bomb.png");
		propsA = kit.getImage("img/game/propsA.png");
		propsB = kit.getImage("img/game/propsB.png");
		superimg = kit.getImage("img/game/super.png");
		stop = kit.getImage("img/game/stop.png");
		clock = kit.getImage("img/game/clock.png");
		icon = kit.getImage("img/icon.gif");
	}

	public void setComp() {
		// 返回按钮
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());

		// 得分
		scoreLabel = new JLabel("得分 : " + score);
		scoreLabel.setBounds(800, 500, 180, 40);
		scoreLabel.setFont(new Font("楷体", Font.BOLD, 24));
		scoreLabel.setForeground(Color.blue);
		this.add(scoreLabel);

		ImageIcon imageA = new ImageIcon("img/game/得分UP！small.png");
		JLabel labelA = new JLabel(imageA);
		ImageIcon imageB = new ImageIcon("img/game/快速提示small.png");
		JLabel labelB = new JLabel(imageB);
		ImageIcon imageC = new ImageIcon("img/game/轻松连锁small.png");
		JLabel labelC = new JLabel(imageC);
		ImageIcon imageF = new ImageIcon("img/game/时光倒流small.png");
		JLabel labelF = new JLabel(imageF);
		ImageIcon imageG = new ImageIcon("img/game/鲨鱼导弹_game.png");
		labelG = new JLabel(imageG);

		JLabel labelAA = new JLabel("得分Up！");
		JLabel labelBB = new JLabel("快速提示");
		JLabel labelCC = new JLabel("轻松连锁");
		JLabel labelFF = new JLabel("时光倒流");
		JLabel labelGG = new JLabel("鲨鱼导弹");

		shark = new SharkListener();
		labelG.addMouseListener(shark);

		if (isShark) {
			labelG.setBounds(790, 530, imageG.getIconWidth(),
					imageG.getIconHeight());
			labelGG.setBounds(830, 620, 120, 40);
			add(labelGG, 0);
			add(labelG, 0);
		}

		ArrayList<JLabel> propStr = new ArrayList<JLabel>();
		ArrayList<JLabel> propImage = new ArrayList<JLabel>();

		if (isUp) {
			propStr.add(labelAA);
			propImage.add(labelA);
		}
		if (isPrompt) {
			propStr.add(labelBB);
			propImage.add(labelB);
		}
		if (isChain) {
			propStr.add(labelCC);
			propImage.add(labelC);
		}
		if (isBack()) {
			propStr.add(labelFF);
			propImage.add(labelF);
		}

		for (int i = 0; i < propStr.size(); i++) {
			JLabel image = propImage.get(i);
			image.setBounds(220 + 72 * i, 600, 72, 66);
			this.add(image);

			JLabel str = propStr.get(i);
			str.setBounds(240 + 72 * i, 630, 72, 66);
			this.add(str);
		}

		pause = new RButton("暂停");// 锁定鼠标监听
		pause.setBounds(800, 400, 120, 40);
		pause.setFont(new Font("楷体", Font.BOLD, 22));
		pause.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (fieldtime < 60) {
					if (pause.getText().equals("暂停")) {
						progressTimer.stop();
						pause.setText("继续");
						isPaused = true;
					} else {
						progressTimer.start();
						pause.setText("暂停");
						isPaused = false;
					}
				}
			}
		});
		this.add(pause);

		progressBar = new JProgressBar(SwingConstants.VERTICAL, 0, 60);
		progressBar.setValue(60);
		progressBar.setBounds(100, 200, 20, 380);
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("宋体", Font.BOLD, 15));
		this.add(progressBar);

	}

	public void updateScore(int s) {
		score = score + s;
		scoreLabel.setText("得分:" + score);
	}

	public boolean isFromLeft() {
		return isLeftToRight;
	}

	public void setLocked(boolean b) {
		islocked = b;
	}

	public void setRemindTimer(double remindTimer) {
		this.remindTimer = remindTimer;
	}

	public void paintComponent(Graphics g) {

		g.drawImage(bacgrd, 0, 0, this);
		g.drawImage(tlgrd, 210, 55, 540, 540, this);

		for (Tile t : tileList) {
			Image tileImg = tileImage.get(t.getColor());
			if (t.getType().equals(Type.bomb)) {
				g.drawImage(propsB, t.getX() - 5, t.getY() - 5, 55, 55, this);
			} else {
				g.drawImage(tileImg, t.getX(), t.getY(), this);
				if (t.isSelected()) {
					g.drawImage(selected, t.getX(), t.getY(), 55, 55, this);
				}
				if (t.getType().equals(Type.blink)) {
					g.drawImage(propsA, t.getX() + 30, t.getY() + 30, 25, 25,
							this);
				} else if (t.getType().equals(Type.time)) {
					g.drawImage(clock, t.getX() + 25, t.getY() + 30, 30, 25,
							this);
				}
			}
		}

		for (Tile t : bombList) {
			g.drawImage(bombImage, t.getX(), t.getY(), 55, 55, this);
		}

		if (remindTimer >= remindTime) {// 如果超过一定时间没有消除,提醒,先暂定是静态
			if (remindTimer < remindTime + 0.01) {
				remindList = service.getRemind();
			}
			Image remindImg = kit.getImage("img/game/remind.gif");
			if (!(remindList == null)) {
				for (int i = 0; i < remindList.size(); i++) {
					g.drawImage(remindImg, remindList.get(i).getX() - 95,
							remindList.get(i).getY() - 80, 250, 200, this);
				}
			}
		}

		if (superMode) {
			g.drawImage(superimg, 190, 10, 580, 600, this);
		}
		if (isPaused) {
			g.drawImage(stop, 193, 40, 575, 575, this);
			g.drawImage(icon, 430, 360, 200, 200, this);
		}

	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(10);// 每10ms刷新一次
				this.repaint();
				if (!islocked && !isPaused) {
					remindTimer += 0.01;
					playTimer += 0.01;
				}
				if (remindTimer < remindTime) {
					remindList.clear();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class GameListener extends MouseAdapter implements MouseMotionListener {

		int x, xr;
		int y, yr;
		int row, rowr;
		int col, colr;

		public void mouseClicked(MouseEvent e) {
			if (!islocked && !isPaused) {
				x = e.getX();
				y = e.getY();
				calcr(x, y);
				Tile clickedTile = getTile(row, col);
				if (clickedTile.getType().equals(Type.bomb)) {// 道具B
					islocked = true;
					remindList.clear();
					animation.propsClick(clickedTile);
					checkComb();
				} else {
					if (getSelectedTile() == null) {// 未有被选中
						if (clickedTile.getType().equals(Type.blink)) {// 道具A
							islocked = true;
							remindList.clear();
							animation.propsClick(clickedTile);
							checkComb();
						} else {// 将该位置的棋子设为被选中状态
							getTile(row, col).setSelected(true);
						}
					} else if (getTile(row, col).isSelected()) {// 再次点击被选中项
						getTile(row, col).setSelected(false);
					} else if (!getSelectedTile().isNeighbor(clickedTile)) {// 点击与选中项不相邻的项
						if (clickedTile.getType().equals(Type.blink)) {// 道具A
							islocked = true;
							remindList.clear();
							animation.propsClick(clickedTile);
							checkComb();
						} else {
							getSelectedTile().setSelected(false);
							getTile(row, col).setSelected(true);
						}

					} else if (getSelectedTile().isNeighbor(clickedTile)) {// 点击相邻棋子,消除;
						islocked = true;
						remindList.clear();
						animation.swap(getSelectedTile(), clickedTile);
						new Music("select");
						if (service.canSwap(getSelectedTile(), clickedTile)) {
							checkComb();
						}
					}
				}

			}
		}

		public void mouseDragged(MouseEvent e) {
			if (!islocked && !isPaused) {
				calcr(e.getPoint());
				Tile t1 = getTile(row, col);
				Tile t2 = getTile(rowr, colr);
				if (t1.isNeighbor(t2)) {// 如果可以交换//&if checkswap()
					islocked = true;
					remindList.clear();
					animation.swap(t1, t2);
					new Music("select");
					if (service.canSwap(t1, t2)) {
						checkComb();
					}
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			if (!islocked && !isPaused) {
				x = e.getX();
				y = e.getY();
				calcr(x, y);
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (e.getX() > 215 && e.getX() < 540 + 215 && e.getY() < 540 + 60
					&& e.getY() > 60) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else {
				setCursor(Cursor.getDefaultCursor());
			}
		}

		public void calcr(int x, int y) {
			col = (x - 215) / 60;// 这里x还应该减去上边缘
			if (col > 8)
				col = 8;
			if (col < 0)
				col = 0;
			row = (y - 60) / 60;
			if (row > 8)
				row = 8;
			if (row < 0)
				row = 0;
		}

		public void calcr(Point p) {
			int x = p.x;
			int y = p.y;
			colr = (x - 215) / 60;// 这里x还应该减去上边缘
			if (colr > 8)
				colr = 8;
			if (colr < 0)
				colr = 0;
			rowr = (y - 60) / 60;
			if (rowr > 8)
				rowr = 8;
			if (rowr < 0)
				rowr = 0;
		}

		public Tile getTile(int r, int c) {// 返回在棋盘某个位置的棋子
			for (Tile t : tileList) {
				if (t.getRow() == r && t.getCol() == c) {
					return t;
				}
			}
			return null;
		}

		public void checkComb() {
			if ((playTimer - combTimer) <= 1) {
				comb++;
				if (comb > maxComb) {
					maxComb = comb;
				}
				if (comb >= 4) {
					superMode = true;
					System.out.println("gg");
				}
			} else {
				comb = 0;
				superMode = false;
			}
			combTimer = playTimer;
		}

	}

	public Tile getSelectedTile() {
		for (Tile t : tileList) {
			if (t.isSelected()) {
				return t;
			}
		}
		return null;
	}

	public void actionPerformed(ActionEvent e) {
		int value = progressBar.getValue();
		fieldtime++;
		if (fieldtime > 60) {
			this.islocked = true;
			if (shark != null && labelG != null) {
				labelG.removeMouseListener(shark);
			}
			reqList.addAll(service.timeUp());
			if (!reqList.isEmpty()) {
				animation.fall();
				islocked = true;
			} else {
				if (scoreup) {
					score = (int) (score * 1.2);
				}

				UpdateControllerService ucs = null;
				if (mode == 1) {
					SocketClientService s = null;
					try {
						s = new SocketClient();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ucs = new UpdateController(s);
				} else if (mode == 0) {
					ucs = new UpdateController();
				}
				int coin = score / 100;
				ucs.updateUserPO(userName, score, maxComb, coin);
				setComponent(score, score / 100, maxComb);

				progressTimer.stop();
			}

		}
		progressBar.setString(value + "");
		progressBar.setValue(--value);
	}

	public void setComponent(int score, int coin, int maxComb) {

		ImageIcon over = new ImageIcon("img/game/over.png");
		overImg = new MyJButton(over);
		overImg.setBounds(165, 0, over.getIconWidth(), over.getIconHeight());
		this.add(overImg);

		JLabel again = new JLabel("<HTML><U>Again</U></HTML>");
		again.setBounds(579, 498, 120, 40);
		again.setFont(new Font("楷体", Font.BOLD, 30));
		again.setForeground(Color.blue);
		again.addMouseListener(new AgainListener());
		add(again, 0);

		overScoreLabel = new JLabel("" + score); // 得分
		overScoreLabel.setBounds(478, 305, 120, 40);
		overScoreLabel.setFont(new Font("楷体", Font.BOLD, 40));
		overScoreLabel.setForeground(Color.orange);
		add(overScoreLabel, 0);

		coinLabel = new JLabel("" + coin); // 金币
		coinLabel.setBounds(478, 405, 120, 40);
		coinLabel.setFont(new Font("楷体", Font.BOLD, 40));
		coinLabel.setForeground(Color.orange);
		add(coinLabel, 0);

		comboLabel = new JLabel("" + maxComb); // 连击
		comboLabel.setBounds(478, 505, 120, 40);
		comboLabel.setFont(new Font("楷体", Font.BOLD, 40));
		comboLabel.setForeground(Color.orange);
		add(comboLabel, 0);
	}

	public boolean isBack() {
		return isBack;
	}

	public void setBack(boolean isBack) {
		this.isBack = isBack;
	}

	class AgainListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {

			GamePanel gamePanel = new GamePanel(userName, mode, isLeftToRight,
					isUp, isPrompt, isChain, isBack(), isShark);
			gamePanel.setMusicAndSound(systemMusic, systemSound);

			PropsControllerService propsControllerService = new PropsController(
					PanelController.s);
			int isPlay;

			if (mode == 0) {
				isPlay = propsControllerService.updateLocalCoin(isUp, isPrompt,
						isChain, isBack(), isShark);
			} else {
				isPlay = propsControllerService.updateCoin(isUp, isPrompt,
						isChain, isBack(), isShark, userName);
			}

			if (isPlay == -1) {
				JOptionPane.showMessageDialog(null, "金币数不够", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				PanelController.moveToStage(gamePanel, GamePanel.this, 1);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}
	}

	class returnButtonListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {

			progressTimer.stop();
			if (mode == 0) {
				LocalDataHelper ldh = new LocalDataHelper();
				UserPO po = ldh.getFullUserPO();
				int coin = po.getCoin();

				ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(
						userName, mode, PanelController.s);
				choosePropsPanel.setCoinLabel(coin);
				PanelController.moveToStage(choosePropsPanel, GamePanel.this,
						mode);
			} else {

				PropsControllerService propsService = new PropsController(
						PanelController.s);
				int coin = propsService.getCoin(userName);

				ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(
						userName, mode, PanelController.s);
				choosePropsPanel.setCoinLabel(coin);
				PanelController.moveToStage(choosePropsPanel, GamePanel.this,
						mode);
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}

	}

	public class Animation implements ActionListener {

		public Timer timer;
		public AnimType type;
		public fallDirection downOrRight;
		Tile tile1;// 需要交换的两个
		Tile tile2;
		int stepnum;
		boolean swapSuccess;

		public Animation() {
			timer = new Timer(10, this);
			stepnum = 0;
			swapSuccess = false;
			if (isFromLeft()) {
				downOrRight = fallDirection.RIGHTWARD;
			} else {
				downOrRight = fallDirection.DOWNWARD;
			}
		}

		public void swap(Tile t1, Tile t2) {
			tile1 = t1;
			tile2 = t2;
			setLocked(true);
			type = AnimType.SWAP;
			timer.start();
		}

		public void back(Tile t1, Tile t2) {
			tile1 = t1;
			tile2 = t2;
			setLocked(true);
			type = AnimType.BACK;
			timer.start();
		}

		public void propsClick(Tile t) {
			try {
				reqList = service.checkTile(t);
			} catch (GameSituationException e) {
				e.printStackTrace();
			}
			fall();
		}

		public void endswap() {
			stepnum = 0;
			timer.stop();
			if (service.canSwap(tile1, tile2)) {// 如果有能消除的
				reqList = service.swap(tile1, tile2);
				fall();
			} else {// 现在bomblist中已经有了tile,应该先等一会然后再下落;下落之前就应该清空bomblist
				back(tile1, tile2);
			}
		}

		public void fall() {
			if (!reqList.isEmpty()) {
				tileList = reqList.get(0).getTileList();
				bombList = reqList.get(0).getBombList();
				type = AnimType.FALL;
				setLocked(true);
				timer.start();
			}
		}

		public void endfall() {
			stepnum = 0;
			timer.stop();
			reqList.remove(0);

			if (reqList.size() > 0) {
				fall();
			} else {
				for (Tile t : tileList) {// 改变下落的tile的row值或col值
					if (downOrRight == fallDirection.DOWNWARD) {
						t.setRow(t.getRow() + t.fallDistance);

					} else {
						t.setCol(t.getCol() + t.fallDistance);
					}
					t.fallDistance = 0;
				}
				setLocked(false);
				setRemindTimer(0);// 将计时器重置为0
			}
			if (reqList.isEmpty() && fieldtime > 60) {
				if (scoreup) {
					score = (int) (score * 1.2);
				}

				UpdateControllerService ucs = null;
				if (mode == 1) {
					SocketClientService s = null;
					try {
						s = new SocketClient();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ucs = new UpdateController(s);
				} else if (mode == 0) {
					ucs = new UpdateController();
				}
				int coin = score / 100;
				ucs.updateUserPO(userName, score, maxComb, coin);
				setComponent(score, score / 100, maxComb);

				progressTimer.stop();
			}
		}

		public void actionPerformed(ActionEvent e) {
			if (type == AnimType.SWAP) {// 交换
				swapAction();
			} else if (type == AnimType.BACK) {// 返回
				backAction();
			} else if (type == AnimType.FALL) {// 下落
				fallAnim();// 在下落过程中把bomblist清空
			}
		}

		public void swapAction() {
			if (stepnum >= 20) {
				endswap();
			} else {
				swapAnim();
			}
		}

		public void backAction() {
			if (stepnum >= 20) {
				setLocked(false);
				timer.stop();
				stepnum = 0;
			} else {
				backAnim();
			}
		}

		public void backAnim() {
			int direction = 0;
			if (tile1.getRow() == tile2.getRow()) {
				direction = tile2.getCol() - tile1.getCol();
				tile1.moveX(-direction, 3);
				tile2.moveX(direction, 3);
			} else {
				direction = tile2.getRow() - tile1.getRow();
				tile1.moveY(-direction, 3);
				tile2.moveY(direction, 3);
			}
			stepnum++;
		}

		public void swapAnim() {// 改变tile的x,y值
			int direction = 0;
			if (tile1.getRow() == tile2.getRow()) {
				direction = tile2.getCol() - tile1.getCol();
				tile1.moveX(direction, 3);
				tile2.moveX(-direction, 3);
			} else {
				direction = tile2.getRow() - tile1.getRow();
				tile1.moveY(direction, 3);
				tile2.moveY(-direction, 3);
			}
			stepnum++;
		}

		public void fallAnim() {
			setLocked(true);
			if (stepnum >= 20) {
				endfall();
			} else {
				if (stepnum == 5) {
					bombList.clear();
				}
				for (Tile t : tileList) {
					if (t.isWillDrop()) {
						if (downOrRight == fallDirection.DOWNWARD) {
							t.moveY(1, 3 * t.fallDistance);
						} else {
							t.moveX(1, 3 * t.fallDistance);
						}
					}
				}
				stepnum++;
			}

		}

	}

	class SharkListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			reqList = service.useSharkProp();
			animation.fall();
			labelG.removeMouseListener(this);
		}
	}

	public void addTime() {
		int value = progressBar.getValue() + 2;
		progressBar.setString(value + "");
		progressBar.setValue(value);
		fieldtime = fieldtime - 2;
	}

}
