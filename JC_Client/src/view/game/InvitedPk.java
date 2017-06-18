package view.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import localdata.LocalDataHelper;
import po.message.Message;
import po.user.UserPO;
import view.component.MyJButton;
import view.component.PanelController;
import view.component.RButton;
import view.props.ChoosePropsPanel;
import controller.game.Board;
import controller.game.GameSituationException;
import controller.game.ReqResult;
import controller.game.Tile;
import controller.game.Type;
import controller.game.fallDirection;
import controller.game.fightGame.GameController;
import controller.props.PropsController;
import controllerservice.game.GameControllerService;
import controllerservice.props.PropsControllerService;

public class InvitedPk extends JPanel implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L;

	RButton pause, exit;
	JLabel scoreLabel;
	JProgressBar progressBar;
	ArrayList<ReqResult> reqList;
	ArrayList<Tile> tileList;
	ArrayList<Tile> bombList;
	GameControllerService service;
	Toolkit kit;
	Animation animation;
	Music music;
	double remindTimer;
	double remindTime;
	double combTimer;
	double playTimer;
	int comb;// ��������
	ArrayList<Tile> remindList;
	boolean islocked;
	boolean isPaused;
	boolean scoreup;
	boolean superMode;// �Ƿ񳬼�ģʽ
	int maxComb;// �����������
	int score;// �ܵ÷�
	Timer progressTimer;
	int fieldtime;// ÿ��ʱ��

	boolean isLeftToRight; // ���䷽��true��ʾ��������
	int systemMusic; // ��������
	int systemSound; // ��Ч����

	boolean isUp;
	boolean isPrompt;
	boolean isChain;
	boolean isBack;
	boolean isShark;
	boolean gameStart;

	Image tlgrd, bacgrd, selected, bombImage, propsA, propsB;
	HashMap<controller.game.Color, Image> tileImage;

	String userName;
	String to_user;
	int mode;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;

	JLabel overScoreLabel, enemyScoreLabel, comboLabel;
	MyJButton overImg;

	public InvitedPk(String userName, int mode, boolean isUp, boolean isPrompt,
			boolean isChain, boolean isBack, boolean isShark, String to_user) {
		super();

		this.userName = userName;
		this.to_user = to_user;
		String result = readFile("file/url.txt");
		String ipAddr = result.split("%")[0];
		int port = Integer.parseInt(result.split("%")[1]);

		try {
			socket = new Socket(ipAddr, port);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Message m = new Message(new UserPO(userName), new UserPO(to_user));
		m.setCommand(Message.cmd_acceptpk);
		sendMessage(m);
		this.mode = mode;

		this.isUp = isUp;
		this.isPrompt = isPrompt;
		this.isChain = isChain;
		this.isBack = isBack;
		this.isShark = isShark;
		gameStart = false;

		if (isPrompt) {
			remindTime = 2;
		}
		scoreup = isChain;

		maxComb = 0;
		comb = 0;
		combTimer = 0;
		superMode = false;

		setLayout(null);
		setComp();
		setBounds(0, 0, 1016, 678);
		this.isLeftToRight = false;

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
		bombList = new ArrayList<Tile>();
		animation = new Animation();
		remindTimer = 0;
		playTimer = 0;
		fieldtime = 0;
		remindTime = 3;
		remindList = new ArrayList<Tile>();
		islocked = false;
		isPaused = false;
		score = 0;
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
	}

	public void setComp() {
		// ���ذ�ť
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());

		// �÷�
		scoreLabel = new JLabel("�÷� : " + score);
		scoreLabel.setBounds(800, 500, 180, 40);
		scoreLabel.setFont(new Font("����", Font.BOLD, 24));
		scoreLabel.setForeground(Color.blue);
		this.add(scoreLabel);

		ImageIcon imageA = new ImageIcon("img/game/�÷�UP��small.png");
		JLabel labelA = new JLabel(imageA);
		ImageIcon imageB = new ImageIcon("img/game/������ʾsmall.png");
		JLabel labelB = new JLabel(imageB);
		ImageIcon imageC = new ImageIcon("img/game/��������small.png");
		JLabel labelC = new JLabel(imageC);
		ImageIcon imageF = new ImageIcon("img/game/ʱ�⵹��small.png");
		JLabel labelF = new JLabel(imageF);
		ImageIcon imageG = new ImageIcon("img/game/���㵼��small.png");
		final JLabel labelG = new JLabel(imageG);

		JLabel labelAA = new JLabel("�÷�Up��");
		JLabel labelBB = new JLabel("������ʾ");
		JLabel labelCC = new JLabel("��������");
		JLabel labelFF = new JLabel("ʱ�⵹��");
		JLabel labelGG = new JLabel("���㵼��");

		labelG.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				reqList = service.useSharkProp();
				animation.fall();
				labelG.removeMouseListener(this);
			}
		});

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
		if (isBack) {
			propStr.add(labelFF);
			propImage.add(labelF);
		}
		if (isShark) {
			propStr.add(labelGG);
			propImage.add(labelG);
		}

		PropPanel p = new PropPanel(propStr, propImage);
		p.setBounds(240, 600, 72 * propStr.size(), 66);
		p.setOpaque(false);
		this.add(p);

	}

	public void updateScore(int s) {
		score = score + s;
		scoreLabel.setText("�÷�:" + score);
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

		if (gameStart) {
			for (Tile t : tileList) {
				Image tileImg = tileImage.get(t.getColor());
				if (t.getType().equals(Type.bomb)) {
					g.drawImage(propsB, t.getX() - 5, t.getY() - 5, 55, 55,
							this);
				} else {
					g.drawImage(tileImg, t.getX(), t.getY(), this);
					if (t.isSelected()) {
						g.drawImage(selected, t.getX(), t.getY(), 55, 55, this);
					}
					if (t.getType().equals(Type.blink)) {
						g.drawImage(propsA, t.getX() + 30, t.getY() + 30, 25,
								25, this);
					}
				}
			}

			for (Tile t : bombList) {
				g.drawImage(bombImage, t.getX(), t.getY(), 55, 55, this);
			}

			if (remindTimer >= remindTime) {// �������һ��ʱ��û������,����,���ݶ��Ǿ�̬
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
		}

	}

	public void run() {
		while (true) {
			try {
				Message mes = (Message) in.readObject();
				if (mes.getCommand().equals(Message.cmd_pkstart)) {
					// ��Ϸ��ʼ
					progressBar = new JProgressBar(SwingConstants.VERTICAL, 0,
							60);
					progressBar.setValue(60);
					progressBar.setBounds(100, 200, 20, 380);
					progressBar.setStringPainted(true);
					progressBar.setFont(new Font("����", Font.BOLD, 15));
					this.add(progressBar);
					progressTimer = new Timer(1000, this);
					progressTimer.start();
					tileList = mes.getTileList();
					service = new GameController(new Board(tileList));
					gameStart = true;
					GameListener listener = new GameListener();
					addMouseListener(listener);
					addMouseMotionListener(listener);
					Thread t = new Thread(new RepaintThread());
					t.start();
					setComp();
				} else if (mes.getCommand().equals(Message.cmd_pkfinish)) {
					System.out.println("ivpkfinish");
					if(mes.getScore()>score){//����
						setComponent(score,mes.getScore(),false);
					}else{
						setComponent(score,mes.getScore(),true);//Ӯ��
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("�ѶϿ������������");
				e.printStackTrace();
				break;
			}

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
				if (clickedTile.getType().equals(Type.bomb)) {// ����B
					islocked = true;
					remindList.clear();
					animation.propsClick(clickedTile);
					checkComb();
				} else {
					if (getSelectedTile() == null) {// δ�б�ѡ��
						if (clickedTile.getType().equals(Type.blink)) {// ����A
							islocked = true;
							remindList.clear();
							animation.propsClick(clickedTile);
							checkComb();
						} else {// ����λ�õ�������Ϊ��ѡ��״̬
							getTile(row, col).setSelected(true);
						}
					} else if (getTile(row, col).isSelected()) {// �ٴε����ѡ����
						getTile(row, col).setSelected(false);
					} else if (!getSelectedTile().isNeighbor(clickedTile)) {// �����ѡ������ڵ���
						if (clickedTile.getType().equals(Type.blink)) {// ����A
							islocked = true;
							remindList.clear();
							animation.propsClick(clickedTile);
							checkComb();
						} else {
							getSelectedTile().setSelected(false);
							getTile(row, col).setSelected(true);
						}

					} else if (getSelectedTile().isNeighbor(clickedTile)) {// �����������,����;
						islocked = true;
						remindList.clear();
						animation.swap(getSelectedTile(), clickedTile);
						checkComb();
					}
				}

			}
		}

		public void mouseDragged(MouseEvent e) {
			if (!islocked && !isPaused) {
				calcr(e.getPoint());
				Tile t1 = getTile(row, col);
				Tile t2 = getTile(rowr, colr);
				if (t1.isNeighbor(t2)) {// ������Խ���//&if checkswap()
					islocked = true;
					remindList.clear();
					animation.swap(t1, t2);
					checkComb();
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
			col = (x - 215) / 60;// ����x��Ӧ�ü�ȥ�ϱ�Ե
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
			colr = (x - 215) / 60;// ����x��Ӧ�ü�ȥ�ϱ�Ե
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

		public Tile getTile(int r, int c) {// ����������ĳ��λ�õ�����
			for (Tile t : tileList) {
				if (t.getRow() == r && t.getCol() == c) {
					return t;
				}
			}
			return null;
		}

		public void swap() {

		}

		public void checkComb() {
			if ((playTimer - combTimer) <= 1) {
				comb++;
				if (comb > maxComb) {
					maxComb = comb;
				}
				if (comb >= 4) {
					superMode = true;
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
		progressBar.setString(value + "");
		progressBar.setValue(--value);
		fieldtime++;
		if (fieldtime > 60) {
			this.islocked = true;
			if (scoreup) {
				score = (int) (score * 1.2);
			}
			Message mes = new Message(new UserPO(to_user), new UserPO(to_user));
			mes.setScore(score);
			mes.setCommand(Message.cmd_pkfinish);
			sendMessage(mes);
			progressTimer.stop();
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
				PanelController.moveToStage(choosePropsPanel, InvitedPk.this,
						mode);
			} else {

				PropsControllerService propsService = new PropsController(
						PanelController.s);
				int coin = propsService.getCoin(userName);

				ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(
						userName, mode, PanelController.s);
				choosePropsPanel.setCoinLabel(coin);
				PanelController.moveToStage(choosePropsPanel, InvitedPk.this,
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

	class PropPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		PropPanel(ArrayList<JLabel> propStrInfo, ArrayList<JLabel> propImageInfo) {

			this.setLayout(new GridLayout(2, propStrInfo.size(), 0, 0));
			for (int i = 0; i < propStrInfo.size(); i++) {
				this.add(propImageInfo.get(i));
			}
			for (int i = 0; i < propStrInfo.size(); i++) {
				this.add(propStrInfo.get(i));
			}
		}
	}

	public class Animation implements ActionListener {

		public Timer timer;
		public AnimType type;
		public fallDirection downOrRight;
		Tile tile1;// ��Ҫ����������
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
			if (service.canSwap(tile1, tile2)) {// �������������
				reqList = service.swap(tile1, tile2);
				fall();
			} else {// ����bomblist���Ѿ�����tile,Ӧ���ȵ�һ��Ȼ��������;����֮ǰ��Ӧ�����bomblist
				back(tile1, tile2);
			}
		}

		public void fall() {
			tileList = reqList.get(0).getTileList();
			bombList = reqList.get(0).getBombList();
			score +=reqList.get(0).getScore();
			scoreLabel.setText("�÷� ��"+score);
			type = AnimType.FALL;
			setLocked(true);
			timer.start();
		}

		public void endfall() {
			stepnum = 0;
			timer.stop();
			reqList.remove(0);
			if (reqList.size() > 0) {
				fall();
			} else {
				for (Tile t : tileList) {// �ı������tile��rowֵ��colֵ
					if (downOrRight == fallDirection.DOWNWARD) {
						t.setRow(t.getRow() + t.fallDistance);

					} else {
						t.setCol(t.getCol() + t.fallDistance);
					}
					t.fallDistance = 0;
				}
				setLocked(false);
				setRemindTimer(0);// ����ʱ������Ϊ0
			}

		}

		public void actionPerformed(ActionEvent e) {
			if (type == AnimType.SWAP) {// ����
				swapAction();
			} else if (type == AnimType.BACK) {// ����
				backAction();
			} else if (type == AnimType.FALL) {// ����
				fallAnim();
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

		public void swapAnim() {// �ı�tile��x,yֵ
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

	class RepaintThread implements Runnable {

		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				InvitedPk.this.repaint();
			}
		}

	}

	public void sendMessage(Message message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void setComponent(int score, int enemyScore ,boolean success) {

		ImageIcon over;
		over = new ImageIcon("img/game/defeat.png");
		overImg = new MyJButton(over);
		overImg.setBounds(165, 0, over.getIconWidth(), over.getIconHeight());
		this.add(overImg);

		overScoreLabel = new JLabel("" + score);
		overScoreLabel.setBounds(458, 305, 120, 40);
		overScoreLabel.setFont(new Font("����", Font.BOLD, 40));
		overScoreLabel.setForeground(Color.orange);
		add(overScoreLabel, 0);

		enemyScoreLabel = new JLabel("" + enemyScore);
		enemyScoreLabel.setBounds(458, 405, 120, 40);
		enemyScoreLabel.setFont(new Font("����", Font.BOLD, 40));
		enemyScoreLabel.setForeground(Color.orange);
		add(enemyScoreLabel, 0);

	}
	
}
