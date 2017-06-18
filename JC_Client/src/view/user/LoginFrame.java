package view.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.SocketClient;
import netservice.SocketClientService;
import po.message.Message;
import po.user.UserPO;
import view.choose.ChoosePanel;
import view.component.JButtonUtil;
import view.component.PanelController;
import view.component.RButton;
import view.game.InvitedCoop;
import view.game.InvitedPk;
import view.game.Music;
import view.props.ChoosePropsPanel;
import controller.user.LoginController;
import controller.user.RegisterController;
import controllerservice.user.LoginControllerService;
import controllerservice.user.RegisterControllerService;

/**
 * <code><b>LoginFrame</b></code> contains components about LoginPanel
 * 
 * @author 刘硕 曹雨婷
 * 
 */
public class LoginFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	JPanel menuPanel;
	JLabel imLabel;
	InvitedCoop invitedPanel;
	InvitedPk invitedPk;
	PanelController p;
	JPasswordField jpw;
	JRadioButton rembPw;
	RButton register;
	RButton login;
	public Music music;

	JButton btnMin; // 最小化按钮
	JButton btnClose; // 关闭按钮

	JProgressBar jpb; // 填充矩形
	SimThread activity; // 表线程运行状态
	Timer activityMonitor; // 定时器

	JComboBox<String> jtjc;// 自动补全
	DefaultComboBoxModel<String> dftcbm;
	JTextField userName;

	RegisterControllerService rcService;
	LoginControllerService lgService;
	SocketClientService scs;
	Socket socket;
	String user;
	Message loginMes;
	boolean connected;

	ObjectInputStream in;
	ObjectOutputStream out;

	boolean isUserNameNumLetter;
	boolean isPW1NumLetter;
	boolean isPW2NumLetter;

	public LoginFrame() {
		setSize(1016, 678);
		setResizable(false); // 窗口大小不可变
		setLocationRelativeTo(getOwner());
		setUndecorated(true); // 去掉边框

		try {
			scs = new SocketClient();
			connected = true;
		} catch (Exception e) {
			// 未连接服务器
			connected = false;
			System.out.println("连接服务器失败");
		}

		btnMin = JButtonUtil.getBtnMin();
		btnClose = JButtonUtil.getBtnClose();
		btnClose.setActionCommand("exit");
		btnMin = JButtonUtil.getBtnMin();
		music = new Music("backgr");
		// new Thread(music).start();
		add(btnClose);
		btnClose.setBounds(976, -2, 39, 20);
		add(btnMin);
		btnMin.setBounds(948, -2, 28, 20);
		// 给关闭按钮添加活动事件
		btnClose.addActionListener(new WindowCloseListener(this));
		// 给最小化按钮添加活动事件
		btnMin.addActionListener(new WindowMinListener(this));

		setBackground(new Color(0, 0, 0, 0));
		Image taskbar = Toolkit.getDefaultToolkit().getImage(
				"img/start/taskbar.jpg");
		setIconImage(taskbar);

		menuPanel = (JPanel) this.getContentPane();
		menuPanel.setLayout(null);
		menuPanel.setBounds(0, 0, 1016, 678);
		setBacgrd();
		setComp();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void setBacgrd() {
		ImageIcon bacgrd = new ImageIcon("img/BackGround_First.jpg");
		JLabel imLabel = new JLabel(bacgrd);
		imLabel.setBounds(0, 0, bacgrd.getIconWidth(), bacgrd.getIconHeight());
		menuPanel.add(imLabel);
	}

	public void setComp() {

		final JLabel userNameLabel = new JLabel("用户名");
		userNameLabel.setBounds(210, 300, 100, 30);
		userNameLabel.setFont(new Font("楷体", Font.BOLD, 23));
		menuPanel.add(userNameLabel, 0);

		final JLabel passwordLabel = new JLabel("密 码");
		passwordLabel.setBounds(210, 385, 100, 30);
		passwordLabel.setFont(new Font("楷体", Font.BOLD, 23));
		menuPanel.add(passwordLabel, 0);

		userName = new JTextField();
		userName.setBounds(305, 300, 165, 33);
		userName.setForeground(Color.black);
		userName.setFont(new Font("楷体", Font.PLAIN, 19));
		dftcbm = new DefaultComboBoxModel<String>();
		jtjc = new JComboBox<String>(dftcbm) {
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};

		userName.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {// 插入
				autofinish();
			}

			public void removeUpdate(DocumentEvent e) {// 移除
				autofinish();
			}

			public void changedUpdate(DocumentEvent e) {
				autofinish();
			}

		});

		userName.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER
						|| e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(jtjc);
					jtjc.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						userName.setText(jtjc.getSelectedItem().toString());
						jtjc.setPopupVisible(false);
						String name = userName.getText();// 用户名
						String password = readFile(name, "file/account.txt");
						if (!password.equals("")) {
							jpw.setText(password);
							rembPw.setSelected(true);
							jpw.requestFocus();
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					jtjc.setPopupVisible(false);
				}
			}
		});

		userName.add(jtjc, BorderLayout.SOUTH);
		jtjc.setBounds(0, 10, 155, 0);

		userName.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				enterButton();
			}

		});

		jtjc.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			}

		});
		menuPanel.add(userName, 0);

		jpw = new JPasswordField();// 密码框
		jpw.setBounds(305, 385, 165, 33);
		jpw.setEchoChar('*');
		jpw.setFont(new Font("楷体", Font.PLAIN, 16));
		jpw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login.doClick();
				}
			}
		});
		jpw.addFocusListener(new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				String name = userName.getText();// 用户名
				String password = readFile(name, "file/account.txt");
				if (!password.equals("")) {
					jpw.setText(password);
					rembPw.setSelected(true);
				}
			}

		});
		jpw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				enterButton();
			}

		});
		menuPanel.add(jpw, 0);

		rembPw = new JRadioButton("记住密码");
		rembPw.setFont(new Font("楷体", Font.PLAIN, 20));
		rembPw.setBounds(490, 385, 115, 35);
		rembPw.setOpaque(false);
		menuPanel.add(rembPw, 0);

		register = new RButton("注 册");
		register.setBounds(440, 450, 100, 40);
		register.setFont(new Font("楷体", Font.BOLD, 21));
		register.addMouseListener(new RegisterListener());
		menuPanel.add(register, 0);

		final JLabel tourModel = new JLabel("<HTML><U>游客模式</U></HTML>");
		tourModel.setBounds(498, 295, 120, 40);
		tourModel.setFont(new Font("楷体", Font.BOLD, 21));
		tourModel.setForeground(new Color(0, 0, 139));
		tourModel.addMouseListener(new TourModelListener());
		menuPanel.add(tourModel, 0);

		login = new RButton("登 录");
		login.setBounds(260, 450, 100, 40);
		login.setFont(new Font("楷体", Font.BOLD, 21));

		login.addMouseListener(new LoginListener());
		menuPanel.add(login, 0);

		activityMonitor = new Timer(100, new ActionListener() {// 每0.5秒执行一次
					public void actionPerformed(ActionEvent e) {

						int current = activity.getCurrent();// 得到线程的当前进度
						jpb.setValue(current);// 更新进度栏的值
						if (current == activity.getTarget()) {// 如果到达目标值
							activityMonitor.stop();// 终止定时器
							setCompState(true);// 激活组件

							String resultFile = readFile("SystemSet");
							int mv = Integer.parseInt(resultFile.split("%")[0]);
							int sv = Integer.parseInt(resultFile.split("%")[1]);
							String isDownStr = resultFile.split("%")[2];
							boolean isDown = isDownStr.equals("true") ? true
									: false;

							String username = userName.getText();
							p = new PanelController(LoginFrame.this, username,
									-1, mv, sv, isDown, scs);
							menuPanel.removeAll();

							ChoosePanel choosePanel = new ChoosePanel(username,
									PanelController.s);
							PanelController.moveToStage(choosePanel, menuPanel,
									1);
							for (String req : loginMes.getUser()
									.getFriendRequest()) {
								if (JOptionPane.showConfirmDialog(null, req
										+ "请求加你为好友,是否接受", "好友邀请",
										JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
									Message mes = new Message(user, req);
									mes.setCommand(Message.cmd_tobefriend);
									try {
										out.writeObject(mes);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							}
						}
					}
				});

		jpb = new JProgressBar(); // 填充矩形
		jpb.setStringPainted(true);
		jpb.setBounds(285, 500, 225, 15);
		menuPanel.add(jpb, 0);
		jpb.setVisible(false);
	}

	public void setCompState(boolean isOK) { // 登录时设置组件状态
		userName.setEnabled(isOK);
		jpw.setEnabled(isOK);
		rembPw.setEnabled(isOK);
		register.setEnabled(isOK);
		login.setEnabled(isOK);
	}

	public boolean isOKToEnter() {
		String username = userName.getText();
		String password = new String(jpw.getPassword());
		boolean bool = false;

		if (username.length() == 0) {
			JOptionPane.showMessageDialog(null, "请输入用户名", "系统信息",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (password.length() == 0) {
			JOptionPane.showMessageDialog(null, "请输入密码", "系统信息",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (!connected) {
			JOptionPane.showMessageDialog(null, "无法连接到服务器,登录失败");
		} else {
			lgService = new LoginController(scs);
			loginMes = lgService.login(username, password);
			String result = loginMes.isSuccess();
			if (result.equals("用户名不存在！")) {
				JOptionPane.showMessageDialog(null, "用户名不存在", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
				userName.setText("");
				jpw.setText("");
			} else if (result.equals("密码错误！")) {
				JOptionPane.showMessageDialog(null, "密码错误", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
				jpw.setText("");
			} else if (result.equals("已在别处登录")) {
				JOptionPane.showMessageDialog(null, "账号已在别处登录", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
				jpw.setText("");
			} else {
				user = userName.getText();
				bool = true;
				if (rembPw.isSelected()) {
					writeFile(username, password, "file/account.txt");
					writeFile(username, password, "file/account2.txt");
				} else {// 把记住的密码删掉
					deleteRecord(username);
				}
				System.out.println("login success");

			}
		}
		return bool;// 在这里就应该开启一个线程了
	}

	public JPanel getMenuPanel() {
		return menuPanel;
	}

	public void enterButton() {
		if (isOKToEnter()) {
			jpb.setVisible(true);
			jpb.setMaximum(100);// 设置进度栏的最大值
			activity = new SimThread(100);
			activity.start();// 启动线程
			activityMonitor.start();// 启动定时器
			setCompState(false);// 禁止组件
			Thread t = new Thread(this);
			t.start();// 开启监听邀请线程
		}
	}

	class LoginListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			enterButton();
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

	class RegisterListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// 小窗口跳出---注册
			final JFrame frame = new JFrame();
			frame.setTitle("天天爱消除/注册");
			frame.setBounds(450, 300, 362, 250);

			final Color color1 = new Color(225, 237, 233);
			final Color color2 = new Color(76, 124, 206);

			JPanel panel = new JPanel() {
				private static final long serialVersionUID = 1L;

				protected void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setPaint(new GradientPaint(0, 0, color1, this
							.getWidth(), this.getHeight(), color2));
					g2d.fill(new Rectangle(0, 0, this.getWidth(), this
							.getHeight()));
				}
			};

			panel.setLayout(null);

			setBackground(new Color(0, 0, 0, 0));
			Image taskbar = Toolkit.getDefaultToolkit().getImage(
					"img/start/taskbar.jpg");
			frame.setIconImage(taskbar);

			JLabel j0 = new JLabel("用户名");
			j0.setBounds(50, 20, 100, 30);
			j0.setFont(new Font("楷体", Font.PLAIN, 20));
			panel.add(j0);

			final JTextField userName = new JTextField();
			userName.setBounds(150, 20, 150, 30);
			userName.setFont(new Font("楷体", Font.PLAIN, 18));
			panel.add(userName);

			JLabel jl1 = new JLabel("密码");
			jl1.setBounds(50, 60, 100, 30);
			jl1.setFont(new Font("楷体", Font.PLAIN, 20));
			panel.add(jl1);

			final JPasswordField jpw1 = new JPasswordField();
			jpw1.setBounds(150, 60, 150, 30);
			panel.add(jpw1);

			JLabel jl2 = new JLabel("再次输入");
			jl2.setBounds(40, 100, 150, 30);
			jl2.setFont(new Font("楷体", Font.PLAIN, 20));
			panel.add(jl2);

			final JPasswordField jpw2 = new JPasswordField();
			jpw2.setBounds(150, 100, 150, 30);
			panel.add(jpw2);

			RButton jb1 = new RButton("确定");
			jb1.setBounds(130, 150, 100, 30);
			jb1.setFont(new Font("楷体", Font.PLAIN, 20));
			panel.add(jb1);

			final JLabel jdjl = new JLabel();
			jdjl.setHorizontalAlignment(SwingConstants.CENTER);
			jdjl.setBounds(30, 20, 70, 35);
			jdjl.setFont(new Font("楷体", Font.PLAIN, 18));

			jb1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					String name = userName.getText();
					String pw1 = new String(jpw1.getPassword());
					String pw2 = new String(jpw2.getPassword());
					if (name.length() == 0) {
						jdjl.setText("请输入用户名");
					} else if (pw1.length() == 0) {
						jdjl.setText("请输入密码");
					} else if (pw2.length() == 0) {
						jdjl.setText("请再次输入确认");
					} else if (!pw1.equals(pw2)) {
						jdjl.setText("两次输入密码不匹配");
						jpw1.setText("");
						jpw2.setText("");
					} else if (name.length() > 50) {
						jdjl.setText("用户名太长，请重新选择用户名");
						jpw1.setText("");
						jpw2.setText("");
					} else if (pw1.length() < 3 || pw2.length() < 3) {
						jdjl.setText("密码强度太弱，请重新设置密码");
						jpw1.setText("");
						jpw2.setText("");
					} else if (pw1.length() > 15 || pw2.length() > 15) {
						jdjl.setText("密码强度太长，请重新设置密码");
						jpw1.setText("");
						jpw2.setText("");
					} else {
						rcService = new RegisterController(scs);
						String info = rcService.register(name, pw1);
						if (info.equals("注册的用户名已存在！")) {
							jdjl.setText("该用户名已存在，请重新选择用户名！");
							userName.setText("");
							jpw1.setText("");
							jpw2.setText("");
						} else {
							jdjl.setText("注册成功！");
							frame.setVisible(false);
						}
					}
					JOptionPane.showMessageDialog(null, jdjl, "系统信息",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});

			frame.add(panel);
			frame.setVisible(true);
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

	class TourModelListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			String result = readFile("SystemSet");
			int mv = Integer.parseInt(result.split("%")[0]);
			int sv = Integer.parseInt(result.split("%")[1]);
			String isDownStr = result.split("%")[2];
			boolean isDown = isDownStr.equals("true") ? true : false;

			p = new PanelController(LoginFrame.this, "000", 0, mv, sv, isDown,
					scs);
			menuPanel.removeAll();

			UserInfoPanel userInfoPanel = new UserInfoPanel("000", 0,
					PanelController.s);
			PanelController.moveToStage(userInfoPanel, menuPanel, 0);
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

	class WindowCloseListener implements ActionListener {

		JFrame frame = null;

		public WindowCloseListener(JFrame frame) {
			super();
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("exit")) {
				if (JOptionPane.showConfirmDialog(null, "确定退出？", "系统信息",
						JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					((Window) btnClose.getTopLevelAncestor()).dispose();
					System.exit(0);
				}
			} else if (e.getActionCommand().equals("dispose")) {
				frame.setVisible(false);
				frame.dispose();
			}
		}
	}

	class WindowMinListener implements ActionListener {
		JFrame frame = null;

		public WindowMinListener(JFrame frame) {
			super();
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.setExtendedState(JFrame.ICONIFIED);
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

	public void writeFile(String account, String password, String fileName) {
		File file = new File(fileName);
		try {
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter bufr = new BufferedWriter(fr);
			if (readFile(account, fileName).equals("")) {
				bufr.write(account + "%" + password);
				bufr.newLine(); // 开始新一行
				bufr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String account, String fileName) {
		File file = new File(fileName);
		String password = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			String record = null;
			while ((record = bufr.readLine()) != null) {
				if (record.split("%")[0].equals(account)) {
					password = record.split("%")[1];
					break;
				}
			}
			bufr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return password;
	}

	public void deleteRecord(String userName) {
		File file = new File("file/account.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);

			String record = null;
			ArrayList<String> list = new ArrayList<String>();
			while ((record = bufr.readLine()) != null) {
				if (!record.split("%")[0].equals(userName)) {
					list.add(record);
				}
			}
			bufr.close();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bufw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				bufw.write(list.get(i));
				bufw.newLine();
				bufw.flush();
			}
			bufw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateList() {
		boolean vis = false;
		dftcbm.removeAllElements();// 先删再加
		String input = userName.getText();
		ArrayList<String> list = new ArrayList<String>();
		File file = new File("file/account2.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			String record = null;
			while ((record = bufr.readLine()) != null) {
				if (record.startsWith(input)) {
					list.add(record.split("%")[0]);
				}
			}
			if (list.size() > 0) {
				vis = true;
			}
			bufr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!input.isEmpty()) {
			for (String record : list) {// 加入符合要求的
				dftcbm.addElement(record);
				jtjc.setModel(dftcbm);
			}
		} else {
			vis = false;
		}
		return vis;
	}

	public void autofinish() {
		if (updateList()) {
			jtjc.setPopupVisible(true);
		} else {
			jtjc.setPopupVisible(false);
		}
	}

	public void run() {
		try {
			String result = readFile("file/url.txt");
			String ipAddr = result.split("%")[0];
			int port = Integer.parseInt(result.split("%")[1]);

			socket = new Socket(ipAddr, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			Message message = new Message(new UserPO(user));
			message.setCommand(Message.cmd_listen);
			out.writeObject(message);
			in = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				Message mes = (Message) in.readObject();
				if (mes.getCommand().equals(Message.cmd_invitedplgame)) {
					if (JOptionPane.showConfirmDialog(null, mes.getUser()
							.getUserName() + "邀请你玩一局协作游戏,是否接受", "协作邀请",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						Message message = new Message(new UserPO(user),
								mes.getUser());
						message.setCommand(Message.cmd_acceptplgame);
						invitedPanel = new InvitedCoop(new UserPO(user),
								mes.getUser());
						PanelController.moveToStage(invitedPanel);
					} else {
						Message message = new Message(new UserPO(user),
								mes.getUser());
						message.setCommand(Message.cmd_rejectplgame);
						out.writeObject(message);
					}
				} else if (mes.getCommand().equals(Message.cmd_invitedpk)) {
					if (JOptionPane.showConfirmDialog(null, mes.getUser()
							.getUserName() + "邀请你pk一局,是否接受", "对战邀请",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						ChoosePropsPanel chooseprp = new ChoosePropsPanel(user,
								4, scs);
						// Message message = new Message(new UserPO(user),
						// mes.getUser());
						// message.setCommand(Message.cmd_acceptpk);
						// invitedPk = new InvitedPk(new UserPO(user),
						// mes.getUser());
						chooseprp.setTo_user(mes.getUser().getUserName());
						PanelController.moveToStage(chooseprp);
					} else {
						Message message = new Message(new UserPO(user),
								mes.getUser());
						message.setCommand(Message.cmd_rejectdpk);
						out.writeObject(message);
					}
				} else if (mes.getCommand().equals(Message.cmd_addfriends)) {
					if (JOptionPane
							.showConfirmDialog(null, mes.getUserName()
									+ "请求加你为好友,是否接受", "好友邀请",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						mes.setCommand(Message.cmd_tobefriend);
						out.writeObject(mes);
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "已断开与服务器的连接", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
				jpw.setText("");
				break;
			}
		}
	}

}