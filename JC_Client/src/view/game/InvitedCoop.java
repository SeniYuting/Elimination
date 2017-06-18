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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

import po.message.Message;
import po.user.UserPO;
import view.component.MyJButton;
import view.component.PanelController;
import view.user.InviteFriendPanel;
import controller.game.ReqResult;
import controller.game.Tile;
import controller.game.Type;

public class InvitedCoop extends JPanel implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	UserPO user;
	UserPO to_user;
	boolean gameStart;
	Timer progressTimer;
	JProgressBar progressBar;
	int fieldtime;

	CoopAnim coopAnim;
	ArrayList<Tile> tileList;
	ArrayList<Tile> bombList;
	ArrayList<ReqResult> reqList;
	boolean islocked;

	GameListener gameListener;
	JLabel scoreLabel;
	int score;
	Toolkit kit;
	Image tlgrd, bacgrd, selected, bombImage, propsA, propsB;
	HashMap<controller.game.Color, Image> tileImage;

	JLabel overScoreLabel, coinLabel, comboLabel;
	JLabel labelG;
	SharkListener shark;
	MyJButton overImg;

	public InvitedCoop(UserPO user, UserPO to_user) {
		super();
		try {
			String result = readFile("file/url.txt");
			String ipAddr = result.split("%")[0];
			int port = Integer.parseInt(result.split("%")[1]);

			socket = new Socket(ipAddr, port);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			Message m = new Message(user, to_user);
			m.setCommand(Message.cmd_acceptplgame);
			sendMessage(m);
			gameStart = false;
			gameListener = new GameListener();
			coopAnim = new CoopAnim();
			reqList = new ArrayList<ReqResult>();
			bombList = new ArrayList<Tile>();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.user = user;
		this.to_user = to_user;

		setLayout(null);
		setBounds(0, 0, 1016, 678);
		initImg();
		Thread t = new Thread(this);
		t.start();
	}

	public String readFile(String fileName) {
		File file = new File(fileName);
		String result = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			result = bufr.readLine();
			bufr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void sendMessage(Message message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(bacgrd, 0, 0, this);
		g.drawImage(tlgrd, 210, 55, 540, 540, this);
		if (gameStart) {
			for (int i = 0; i < tileList.size(); i++) {
				Image tileImg = tileImage.get(tileList.get(i).getColor());
				if (tileList.get(i).getType().equals(Type.bomb)) {
					g.drawImage(propsB, tileList.get(i).getX() - 5, tileList
							.get(i).getY() - 5, 55, 55, this);
				} else {
					g.drawImage(tileImg, tileList.get(i).getX(), tileList
							.get(i).getY(), this);
					if (tileList.get(i).isSelected()) {
						g.drawImage(selected, tileList.get(i).getX(), tileList
								.get(i).getY(), 55, 55, this);
					}
					if (tileList.get(i).getType().equals(Type.blink)) {
						g.drawImage(propsA, tileList.get(i).getX() + 30,
								tileList.get(i).getY() + 30, 25, 25, this);
					}
				}
			}
			for (Tile t : bombList) {
				g.drawImage(bombImage, t.getX(), t.getY(), 55, 55, this);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		int value = progressBar.getValue();
		fieldtime++;
		if (fieldtime > 60) {
			this.islocked = true;
			progressTimer.stop();
		}
		progressBar.setString(value + "");
		progressBar.setValue(--value);
	}

	public void setProps(boolean isUp, boolean isChain, boolean isShark) {
		ImageIcon imageA = new ImageIcon("img/game/得分UP！small.png");
		JLabel labelA = new JLabel(imageA);
		ImageIcon imageC = new ImageIcon("img/game/轻松连锁small.png");
		JLabel labelC = new JLabel(imageC);
		ImageIcon imageG = new ImageIcon("img/game/鲨鱼导弹_game.png");
		labelG = new JLabel(imageG);

		JLabel labelAA = new JLabel("得分Up！");
		JLabel labelCC = new JLabel("轻松连锁");
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
		if (isChain) {
			propStr.add(labelCC);
			propImage.add(labelC);
		}

		for (int i = 0; i < propStr.size(); i++) {
			JLabel image = propImage.get(i);
			image.setBounds(220 + 72 * i, 600, 72, 66);
			this.add(image);

			JLabel str = propStr.get(i);
			str.setBounds(240 + 72 * i, 630, 72, 66);
			this.add(str);
		}
	}

	public void setComp() {
		// 返回按钮
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());

		progressBar = new JProgressBar(SwingConstants.VERTICAL, 0, 60);
		progressBar.setValue(60);
		progressBar.setBounds(100, 200, 20, 380);
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("宋体", Font.BOLD, 15));
		this.add(progressBar);

		scoreLabel = new JLabel("得分 : 0");
		scoreLabel.setBounds(800, 500, 180, 40);
		scoreLabel.setFont(new Font("楷体", Font.BOLD, 24));
		scoreLabel.setForeground(Color.blue);
		this.add(scoreLabel);
	}

	public void run() {
		while (true) {
			try {
				Message mes = (Message) in.readObject();
				if (mes.getCommand().equals(Message.cmd_coopstart)) {
					// 游戏开始
					tileList = mes.getTileList();
					setProps(mes.isUp,mes.isChain,mes.isShark);
					gameStart = true;
					// 设置道具
					this.addMouseListener(gameListener);
					this.addMouseMotionListener(gameListener);
					Thread t = new Thread(new RepaintThread());
					t.start();
					setComp();
					progressTimer = new Timer(1000, this);
					progressTimer.start();
				} else if (mes.getCommand().equals(Message.cmd_coopinvalid)) {
					JOptionPane.showMessageDialog(null, "由于房主已经开始游戏,您的邀请已失效");
					InviteFriendPanel coopInviteFriendPanel = new InviteFriendPanel(
							user.getUserName(), 2, PanelController.s, false,
							false, false, false, false);
					PanelController.moveToStage(coopInviteFriendPanel,
							InvitedCoop.this, 2);
				} else if (mes.getCommand().equals(Message.cmd_updategame)) {
					reqList.addAll(mes.getReqList());
					coopAnim.fall();
				} else if (mes.getCommand().equals(Message.cmd_coopfinish)) {
					setComponent(mes.getScore(), mes.getScore() / 100,
							mes.getMaxCombo());
					System.out.println(mes.getScore());
				} else if (mes.getCommand().equals(Message.cmd_coopbreak)) {
					JOptionPane.showMessageDialog(null, "由于房主退出,您的游戏已失效");
					progressTimer.stop();
					InviteFriendPanel coopInviteFriendPanel = new InviteFriendPanel(
							user.getUserName(), 2, PanelController.s, false,
							false, false, false, false);
					PanelController.moveToStage(coopInviteFriendPanel,
							InvitedCoop.this, 2);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "网络连接失败,您的游戏已失效");
				progressTimer.stop();
				InviteFriendPanel coopInviteFriendPanel = new InviteFriendPanel(
						user.getUserName(), 2, PanelController.s, false, false,
						false, false, false);
				PanelController.moveToStage(coopInviteFriendPanel,
						InvitedCoop.this, 2);
				e.printStackTrace();
				break;
			}

		}
	}

	public void initImg() {

		kit = Toolkit.getDefaultToolkit();
		tlgrd = kit.getImage("img/game/bg2.jpg");
		bacgrd = kit.getImage("img/BackGround.jpg");
		selected = kit.getImage("img/game/focus.png");
		bombImage = kit.getImage("img/game/bomb.png");
		propsA = kit.getImage("img/game/propsA.png");
		propsB = kit.getImage("img/game/propsB.png");

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
	}

	public void clickProps(Tile t) {
		Message message = new Message(to_user, t, null);
		message.setCommand(Message.cmd_clickprops);
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendSwapInfo(Tile t1, Tile t2) {
		Message message = new Message(to_user, t1, t2);
		message.setCommand(Message.cmd_swap);
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class GameListener extends MouseAdapter implements MouseMotionListener {

		int x, xr;
		int y, yr;
		int row, rowr;
		int col, colr;

		public void mouseClicked(MouseEvent e) {
			if (!islocked) {
				x = e.getX();
				y = e.getY();
				calcr(x, y);
				Tile clickedTile = getTile(row, col);
				if (clickedTile.getType().equals(Type.bomb)) {
					// 道具B
					islocked = true;
					clickProps(clickedTile);
				} else {
					if (!selectedExist()) {// 未有被选中
						if (clickedTile.getType().equals(Type.blink)) {
							// 道具A
							islocked = true;
							clickProps(clickedTile);
						} else {
							setTileSelected(row, col);
						}
					} else if (getTile(row, col).isSelected()) {// 再次点击被选中项
						setTileNotSelected(row, col);
					} else if (!getSelectedTile().isNeighbor(clickedTile)) {// 点击与选中项不相邻的项
						if (clickedTile.getType().equals(Type.blink)) {
							// 道具A
							islocked = true;
							clickProps(clickedTile);
						} else {
							this.clearSelected();
							setTileSelected(row, col);
						}

					} else if (getSelectedTile().isNeighbor(clickedTile)) {// 点击相邻棋子,消除
						islocked = true;
						// remindList.clear();
						sendSwapInfo(this.getSelectedTile(), clickedTile);
					}
				}

			}
		}

		public void mouseDragged(MouseEvent e) {
			if (!islocked) {
				calcr(e.getPoint());
				Tile t1 = getTile(row, col);
				Tile t2 = getTile(rowr, colr);
				if (t1.isNeighbor(t2)) {
					islocked = true;
					sendSwapInfo(t1, t2);
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			if (!islocked) {
				x = e.getX();
				y = e.getY();
				calcr(x, y);
			}
		}

		@Override
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
			for (int i = 0; i < tileList.size(); i++) {
				if (tileList.get(i).getRow() == r
						&& tileList.get(i).getCol() == c) {
					return tileList.get(i);
				}
			}
			return null;
		}

		public void setTileSelected(int row, int col) {
			for (Tile t : tileList) {
				if ((t.getRow() == row) && (t.getCol() == col)) {
					t.setSelected(true);
				}
			}
		}

		public void setTileNotSelected(int row, int col) {
			for (Tile t : tileList) {
				if ((t.getRow() == row) && (t.getCol() == col)) {
					t.setSelected(false);
				}
			}
		}

		public boolean selectedExist() {
			for (Tile t : tileList) {
				if (t.isSelected()) {
					return true;
				}
			}
			return false;
		}

		public Tile getSelectedTile() {
			for (Tile t : tileList) {
				if (t.isSelected()) {
					return t;
				}
			}
			return null;
		}

		public void clearSelected() {
			for (Tile t : tileList) {
				t.setSelected(false);
			}
		}

	}

	class RepaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				InvitedCoop.this.repaint();
			}
		}

	}

	public class CoopAnim implements ActionListener {

		Timer timer;
		int stepNum;
		AnimType type;

		public CoopAnim() {
			timer = new Timer(10, this);
			stepNum = 0;
		}

		public void fall() {
			type = AnimType.FALL;
			tileList = reqList.get(0).getTileList();
			bombList = reqList.get(0).getBombList();
			score = reqList.get(0).getScore();
			scoreLabel.setText("得分 ：" + score);
			timer.start();
		}

		public void endFall() {
			stepNum = 0;
			timer.stop();

			reqList.remove(0);
			if (reqList.size() != 0) {
				fall();
			} else {
				for (Tile t : tileList) {
					t.setRow(t.getRow() + t.fallDistance);
				}
				islocked = false;
			}
		}

		public void swap() {

		}

		public void actionPerformed(ActionEvent e) {
			if (type == AnimType.FALL) {// 下落
				fallAnim();
			}
		}

		public void fallAnim() {
			if (stepNum >= 20) {
				endFall();
			} else {
				if (stepNum == 5) {
					bombList.clear();
				}
				for (int i = 0; i < tileList.size(); i++) {
					tileList.get(i).moveY(1, 3 * tileList.get(i).fallDistance);
				}
				stepNum++;
			}
		}
	}

	class returnButtonListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			progressTimer.stop();
			InviteFriendPanel coopInviteFriendPanel = new InviteFriendPanel(
					user.getUserName(), 2, PanelController.s, false, false,
					false, false, false);
			PanelController.moveToStage(coopInviteFriendPanel,
					InvitedCoop.this, 2);
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

	public void setComponent(int score, int coin, int maxComb) {

		ImageIcon over = new ImageIcon("img/game/over.png");
		overImg = new MyJButton(over);
		overImg.setBounds(165, 0, over.getIconWidth(), over.getIconHeight());
		this.add(overImg);

		JLabel again = new JLabel("<HTML><U>Again</U></HTML>");
		again.setBounds(579, 498, 120, 40);
		again.setFont(new Font("楷体", Font.BOLD, 30));
		again.setForeground(Color.blue);
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
	
	class SharkListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			Message message = new Message(to_user);
			message.setCommand(Message.cmd_useShark);
			try {
				out.writeObject(message);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			labelG.removeMouseListener(this);
		}
	}

}
