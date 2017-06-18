package view.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import netservice.SocketClientService;
import controller.game.Tile;
import po.message.Message;
import po.user.UserPO;
import view.component.PanelController;
import view.component.RButton;
import view.game.CoopGame;
import view.game.PkGame;

public class WaitAcPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	ImageIcon modeIcon;
	JLabel headLabel;
	JLabel imLabel;
	JLabel modeLabel;
	JTable cooperTable;
	ArrayList<JLabel> cooperLabel;
	ArrayList<String> cooperList;
	RButton startButton;

	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	UserPO user;
	SocketClientService scs;
	Thread t;
	boolean notStart;
	boolean isUp, isPrompt, isChain, isBack, isShark;
	int mode;

	public WaitAcPanel(SocketClientService scs, ArrayList<String> cooperList,
			UserPO user, int mode, boolean isUp, boolean isPrompt,
			boolean isChain, boolean isBack, boolean isShark) {

		try {

			String result = readFile("file/url.txt");
			String ipAddr = result.split("%")[0];
			int port = Integer.parseInt(result.split("%")[1]);

			socket = new Socket(ipAddr, port);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			this.user = user;
			this.isUp = isUp;
			this.isPrompt = isPrompt;
			this.isChain = isChain;
			this.isBack = isBack;
			this.isShark = isShark;
			this.scs = scs;
			this.mode = mode;
			notStart = true;
			t = new Thread(this);
			t.start();
			setComp();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(null);
		setBounds(0, 0, 1016, 678);

		bacgrd = new ImageIcon("img/Background.jpg");
		setBacgrd(bacgrd);

		setComp();
		this.cooperList = cooperList;
		this.initCoopers(cooperList);
		setVisible(true);
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

	public void setBacgrd(ImageIcon bacgrd) {
		JLabel imLabel = new JLabel(bacgrd);
		imLabel.setBounds(0, 0, bacgrd.getIconWidth(), bacgrd.getIconHeight());
		add(imLabel);
	}

	public void setComp() {

		modeIcon = new ImageIcon(getImage());
		modeLabel = new JLabel(modeIcon);
		modeLabel.setBounds(100, 220, 300, 300);
		add(modeLabel, 0);

		headLabel = new JLabel("邀请列表");
		headLabel.setBounds(500, 250, 150, 40);
		headLabel.setFont(new Font("楷体", Font.PLAIN, 30));
		add(headLabel, 0);

		startButton = new RButton("开始游戏");
		startButton.setBounds(730, 400, 150, 40);
		startButton.setFont(new Font("楷体", Font.BOLD, 21));
		startButton.addMouseListener(new StartListener());
		add(startButton, 0);

	}

	public void initCoopers(ArrayList<String> cooperList) {
		cooperLabel = new ArrayList<JLabel>();
		for (int i = 0; i < cooperList.size(); i++) {
			JLabel cooper = new JLabel(cooperList.get(i) + "     " + "等待");
			cooper.setFont(new Font("楷体", Font.PLAIN, 30));
			cooper.setForeground(new Color(244, 223, 28));
			cooper.setBounds(450, 300 + i * 120, 300, 60);
			add(cooper, 0);
			cooperLabel.add(cooper);
		}
	}

	public Image getImage() {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("img/choose/introduction4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mode == 2) {
			return bi.getSubimage(600, 0, 300, 300);
		} else {
			return bi.getSubimage(900, 0, 300, 300);
		}
	}

	public void sendMessage(Message message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (notStart) {
			try {
				Message m = (Message) in.readObject();
				if (m.getCommand().equals(Message.cmd_acceptplgame)) {
					int index = cooperList.indexOf(m.getUser().getUserName());
					cooperLabel.get(index).setText(
							m.getUser().getUserName() + "      " + "同意");
					cooperLabel.get(index).setForeground(Color.green);
				} else if (m.getCommand().equals(Message.cmd_rejectplgame)) {
					int index = cooperList.indexOf(m.getUser().getUserName());
					cooperLabel.get(index).setText(
							m.getUser().getUserName() + "      " + "拒绝");
					cooperLabel.get(index).setForeground(Color.red);
				} else if (m.getCommand().equals(Message.cmd_coopstart)) {
					ArrayList<Tile> list = new ArrayList<Tile>();
					for (Tile t : m.getTileList()) {
						list.add(t.clone());
					}
//					m = null;
//					System.gc();
					CoopGame coopGame = new CoopGame(list, in, out, user, scs,m.isUp,m.isChain,m.isShark);
					notStart = false;
					PanelController.moveToStage(coopGame);
					break;
				} else if (m.getCommand().equals(Message.cmd_pkstart)) {
					ArrayList<Tile> list = new ArrayList<Tile>();
					for (Tile t : m.getTileList()) {
						list.add(t.clone());
					}
					m = null;
					System.gc();
					PkGame pkgame = new PkGame(user.getUserName(), mode, false,
							isUp, isPrompt, isChain, isBack, isShark, list, in,
							out, user, scs);
					PanelController.moveToStage(pkgame);
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("已断开与服务器连接");
				break;
			}
		}
	}

	class StartListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			Message m = new Message(user);
			if (mode == 2) {
				m.setCommand(Message.cmd_coopstart);
				m.isChain = isChain;
				m.isUp = isUp;
				m.isShark = isShark;
			} else {
				m.setCommand(Message.cmd_pkstart);
			}
			sendMessage(m);
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

}
