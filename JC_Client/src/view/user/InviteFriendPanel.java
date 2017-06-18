package view.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import netservice.SocketClientService;
import po.message.Message;
import po.user.UserPO;
import view.component.HeaderCellRenderer;
import view.component.MyJButton;
import view.component.PWModelListener;
import view.component.PanelController;
import view.component.RButton;
import view.component.SetListener;
import view.props.ChoosePropsPanel;
import controller.props.PropsController;
import controller.user.FriendController;
import controller.user.LoginController;
import controllerservice.props.PropsControllerService;
import controllerservice.user.FriendControllerService;
import controllerservice.user.LoginControllerService;

/**
 * <code><b>InviteFriendPanel</b></code> contains components about inviteFriend
 * 
 * @author 曹雨婷
 * 
 */
public class InviteFriendPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	JLabel imLabel;
	JLabel setLabel;

	JTable friendTable;
	JTable inviteTable;
	JTable onlineFriendTable;
	JTable userTable;
	DefaultTableModel inviteModel;
	JTextField friendName;

	String userName;
	int mode;
	SocketClientService scs;

	String blank = "";

	WaitAcPanel wip;

	boolean isUp;
	boolean isPrompt;
	boolean isChain;
	boolean isBack;
	boolean isShark;

	public InviteFriendPanel(String userName, int mode, SocketClientService s,
			boolean isUp, boolean isPrompt, boolean isChain, boolean isBack,
			boolean isShark) {
		this.userName = userName;
		this.mode = mode;
		this.scs = s;

		this.isUp = isUp;
		this.isPrompt = isPrompt;
		this.isChain = isChain;
		this.isBack = isBack;
		this.isShark = isShark;

		setLayout(null);
		setBounds(0, 0, 1016, 678);

		bacgrd = new ImageIcon("img/Background.jpg");
		setBacgrd(bacgrd);

		setComp();
		setVisible(true);
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setBacgrd(ImageIcon bacgrd) {
		JLabel imLabel = new JLabel(bacgrd);
		imLabel.setBounds(0, 0, bacgrd.getIconWidth(), bacgrd.getIconHeight());
		add(imLabel);
	}

	public void setComp() {
		for (int i = 0; i < 17 - userName.length(); i++) {
			blank += " ";
		}

		// 返回按钮
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());

		// 邀请好友标签
		setLabel = new JLabel("邀请好友");
		setLabel.setBounds(340, 100, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("楷体", Font.BOLD, 30));
		add(setLabel, 0);

		// 好友列表
		initFriendTable();
		friendTable.setForeground(new Color(244, 223, 28));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);// 将JScrollPane设置为透明
		scrollPane.setViewportView(friendTable);// 装载表格
		scrollPane.setColumnHeaderView(friendTable.getTableHeader());// 设置头部（HeaderView部分）
		scrollPane.getColumnHeader().setOpaque(false);// 再取出头部，并设置为透明
		scrollPane.setBounds(63, 142, 300, 423);

		add(scrollPane, 0);

		JLabel add = new JLabel("好友id");
		add.setBounds(80, 590, 70, 20);
		add.setForeground(new Color(0, 0, 255));
		add.setFont(new Font("楷体", Font.BOLD, 20));
		add(add, 0);

		friendName = new JTextField();
		friendName.setBounds(160, 590, 120, 20);
		friendName.setFont(new Font("楷体", Font.PLAIN, 20));
		add(friendName, 0);

		ImageIcon icon = new ImageIcon("img/user/add.png");
		MyJButton addBt = new MyJButton(icon);
		addBt.setBounds(288, 575, 40, 40);
		addBt.addMouseListener(new AddListener());
		add(addBt, 0);

		// 邀请列表
		initInviteTable();
		inviteTable.setForeground(new Color(0, 255, 0));

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.getViewport().setOpaque(false);
		scrollPane1.setOpaque(false);// 将JScrollPane设置为透明
		scrollPane1.setViewportView(inviteTable);// 装载表格
		scrollPane1.setColumnHeaderView(inviteTable.getTableHeader());// 设置头部（HeaderView部分）
		scrollPane1.getColumnHeader().setOpaque(false);// 再取出头部，并设置为透明
		scrollPane1.setBounds(413, 402, 300, 230);
		add(scrollPane1, 0);

		// 在线好友列表
		initOnlineFriendTable();
		onlineFriendTable.setForeground(new Color(0, 255, 0));

		JScrollPane scrollPane0 = new JScrollPane();
		scrollPane0.getViewport().setOpaque(false);
		scrollPane0.setOpaque(false);// 将JScrollPane设置为透明
		scrollPane0.setViewportView(onlineFriendTable);// 装载表格
		scrollPane0.setColumnHeaderView(onlineFriendTable.getTableHeader());// 设置头部（HeaderView部分）
		scrollPane0.getColumnHeader().setOpaque(false);// 再取出头部，并设置为透明
		scrollPane0.setBounds(413, 142, 300, 230);
		add(scrollPane0, 0);

		RButton inviteButton = new RButton("邀请");
		inviteButton.setBounds(800, 470, 100, 40);
		inviteButton.setFont(new Font("楷体", Font.BOLD, 21));
		inviteButton.addMouseListener(new InviteListener());
		add(inviteButton, 0);

		ImageIcon pw = new ImageIcon("img/pw_set.png");
		MyJButton pwButton = new MyJButton(pw);
		pwButton.setBounds(715, 0, pw.getIconWidth(), pw.getIconHeight());
		add(pwButton, 0);

		JLabel pwModel = new JLabel("<HTML><U>修改密码</U></HTML>");
		pwModel.setBounds(778, 45, 120, 40);
		pwModel.setFont(new Font("楷体", Font.PLAIN, 0));
		pwModel.setOpaque(false);
		pwModel.addMouseListener(new PWModelListener(userName, scs));
		pwModel.addMouseListener(new HandListener());
		add(pwModel, 0);

		JLabel set = new JLabel("<HTML><U>系统设置</U></HTML>");
		set.setBounds(778, 85, 120, 40);
		set.setFont(new Font("楷体", Font.PLAIN, 0));
		set.setOpaque(false);
		set.addMouseListener(new SetListener());
		set.addMouseListener(new HandListener());
		add(set, 0);

	}

	private void initFriendTable() {
		String[] columnName = new String[] { "好友列表" };

		LoginControllerService lcs = new LoginController(scs);
		UserPO po = lcs.getUserPO(userName);
		ArrayList<String> list = po.getFriend();

		String[][] columnValues = new String[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			columnValues[i][0] = blank + list.get(i);
		}

		friendTable = new JTable(columnValues, columnName) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		friendTable.setRowHeight(45);
		friendTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		friendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		friendTable.setIntercellSpacing(new Dimension(0, 0));

		friendTable.setOpaque(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setOpaque(false); // 将渲染器设置为透明
		friendTable.setDefaultRenderer(Object.class, render);

		// 设置显示范围
		Dimension viewSize = new Dimension();
		viewSize.width = friendTable.getColumnModel().getTotalColumnWidth();
		viewSize.height = 10 * friendTable.getRowHeight();
		friendTable.setPreferredScrollableViewportSize(viewSize);

		friendTable.setFont(new Font("楷体", Font.PLAIN, 18));
		friendTable.getTableHeader().setResizingAllowed(false);// 不可自动调整大小
		friendTable.getTableHeader().setReorderingAllowed(false);// 不可整列移动

		// 设置头部透明
		JTableHeader header = friendTable.getTableHeader();// 获取头部
		header.setPreferredSize(new Dimension(30, 26));
		header.setOpaque(false);// 设置头部为透明
		header.getTable().setOpaque(false);// 设置头部里面的表格透明
		header.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer = header.getDefaultRenderer();
		if (headerRenderer instanceof JLabel) {
			((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer).setOpaque(false);
		}
	}

	private void initInviteTable() {
		String[] columnName = new String[] { "邀请列表" };
		String[][] columnValues = null;

		DefaultTableModel jp1deftbmd1 = new DefaultTableModel(columnValues,
				columnName);
		inviteTable = new JTable(jp1deftbmd1) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		inviteTable.setRowHeight(45);
		inviteTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		inviteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inviteTable.setIntercellSpacing(new Dimension(0, 0));

		inviteTable.setOpaque(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setOpaque(false); // 将渲染器设置为透明
		inviteTable.setDefaultRenderer(Object.class, render);

		// 设置显示范围
		Dimension viewSize = new Dimension();
		viewSize.width = inviteTable.getColumnModel().getTotalColumnWidth();
		viewSize.height = 10 * inviteTable.getRowHeight();
		inviteTable.setPreferredScrollableViewportSize(viewSize);

		inviteTable.getTableHeader().setFont(new Font("楷体", Font.PLAIN, 18));
		inviteTable.setFont(new Font("楷体", Font.PLAIN, 18));
		inviteTable.getTableHeader().setResizingAllowed(false);// 不可自动调整大小
		inviteTable.getTableHeader().setReorderingAllowed(false);// 不可整列移动

		// 设置头部透明
		JTableHeader header = inviteTable.getTableHeader();// 获取头部
		header.setPreferredSize(new Dimension(30, 26));
		header.setOpaque(false);// 设置头部为透明
		header.getTable().setOpaque(false);// 设置头部里面的表格透明

		header.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer = header.getDefaultRenderer();
		if (headerRenderer instanceof JLabel) {
			((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer).setOpaque(false);
		}

		inviteModel = (DefaultTableModel) inviteTable.getModel();
	}

	private void initOnlineFriendTable() {
		String[] columnName = new String[] { "在线好友列表" };
		FriendControllerService fcs = new FriendController(scs);
		ArrayList<String> list = fcs.getOnlineFriends(userName);

		String[][] columnValues = new String[list.size()][1];

		for (int i = 0; i < list.size(); i++) {
			columnValues[i][0] = blank + list.get(i);
		}
		onlineFriendTable = new JTable(columnValues, columnName) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		onlineFriendTable.setRowHeight(45);
		onlineFriendTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		onlineFriendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		onlineFriendTable.setIntercellSpacing(new Dimension(0, 0));

		onlineFriendTable.addMouseListener(new MouseAdapter() { // 右键
					public void mousePressed(MouseEvent e) {
						if (e.getButton() == 3) {
							JPopupMenu popmenu = new JPopupMenu();
							JMenuItem item = new JMenuItem("添加到邀请列表 ");
							item.setFont(new Font("楷体", Font.BOLD, 15));
							item.addActionListener(new ActionListener() {

								public void actionPerformed(ActionEvent e) {
									int row = onlineFriendTable
											.getSelectedRow();
									String userName = (String) onlineFriendTable
											.getValueAt(row, 0);
									String[] inviteRow = { "" + userName };

									int rowNum = inviteTable.getRowCount();
									boolean bool = true;
									for (int i = 0; i < rowNum; i++) {
										String value = (String) inviteTable
												.getValueAt(i, 0);
										if (userName.equals(value))
											bool = false;
									}

									if (bool) {
										inviteModel.addRow(inviteRow);
									} else {
										JOptionPane.showMessageDialog(null,
												"该好友已添加至邀请列表！");
									}

									JScrollPane scrollPane1 = new JScrollPane();
									scrollPane1.getViewport().setOpaque(false);
									scrollPane1.setOpaque(false);// 将JScrollPane设置为透明
									scrollPane1.setViewportView(inviteTable);// 装载表格
									scrollPane1.setColumnHeaderView(inviteTable
											.getTableHeader());// 设置头部（HeaderView部分）
									scrollPane1.getColumnHeader().setOpaque(
											false);// 再取出头部，并设置为透明
									scrollPane1.setBounds(413, 402, 300, 230);
									add(scrollPane1, 0);
									repaint();
								}

							});
							popmenu.add(item);
							if (onlineFriendTable.getSelectedRow() != -1) {
								popmenu.show(onlineFriendTable, e.getX(),
										e.getY());
							}
						}

					}
				});

		onlineFriendTable.setOpaque(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setOpaque(false); // 将渲染器设置为透明
		onlineFriendTable.setDefaultRenderer(Object.class, render);

		// 设置显示范围
		Dimension viewSize = new Dimension();
		viewSize.width = onlineFriendTable.getColumnModel()
				.getTotalColumnWidth();
		viewSize.height = 10 * onlineFriendTable.getRowHeight();
		onlineFriendTable.setPreferredScrollableViewportSize(viewSize);

		onlineFriendTable.getTableHeader().setFont(
				new Font("楷体", Font.PLAIN, 18));
		onlineFriendTable.setFont(new Font("楷体", Font.PLAIN, 18));
		onlineFriendTable.getTableHeader().setResizingAllowed(false);// 不可自动调整大小
		onlineFriendTable.getTableHeader().setReorderingAllowed(false);// 不可整列移动

		// 设置头部透明
		JTableHeader header = onlineFriendTable.getTableHeader();// 获取头部
		header.setPreferredSize(new Dimension(30, 26));
		header.setOpaque(false);// 设置头部为透明
		header.getTable().setOpaque(false);// 设置头部里面的表格透明

		header.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer = header.getDefaultRenderer();
		if (headerRenderer instanceof JLabel) {
			((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer).setOpaque(false);
		}
	}

	class returnButtonListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			PropsControllerService propsService = new PropsController(scs);
			int coin = propsService.getCoin(userName);

			ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(userName,
					mode, scs);
			choosePropsPanel.setCoinLabel(coin);
			PanelController.moveToStage(choosePropsPanel,
					InviteFriendPanel.this, mode);
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
	
	class HandListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}
	}

	class InviteListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (mode == 2) {
				if (inviteTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "还未邀请好友");
				} else {
					ArrayList<String> cooperList = new ArrayList<String>();
					for (int i = 0; i < inviteTable.getRowCount(); i++) {
						String invited_user = ((String) inviteTable.getValueAt(
								i, 0)).trim();
						cooperList.add(invited_user);
					}
					wip = new WaitAcPanel(scs, cooperList,
							new UserPO(userName), mode, isUp, isPrompt,
							isChain, isBack, isShark);
					for (int i = 0; i < inviteTable.getRowCount(); i++) {
						String invited_user = ((String) inviteTable.getValueAt(
								i, 0)).trim();
						Message mes = new Message(new UserPO(userName),
								new UserPO(invited_user));
						mes.setCommand(Message.cmd_invitplgame);
						wip.sendMessage(mes);
					}
					PanelController.moveToStage(wip);
					// 向服务器发送游戏开始消息
				}
			} else if (mode == 3) {// 对战
				if (inviteTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "还未邀请好友");
				} else if (inviteTable.getRowCount() > 1) {
					JOptionPane.showMessageDialog(null, "仅能邀请一位好友");
				} else {
					ArrayList<String> cooperList = new ArrayList<String>();
					cooperList.add(((String) inviteTable.getValueAt(0, 0))
							.trim());
					wip = new WaitAcPanel(scs, cooperList,
							new UserPO(userName), mode, isUp, isPrompt,
							isChain, isBack, isShark);
					String invited_user = ((String) inviteTable
							.getValueAt(0, 0)).trim();
					Message mes = new Message(new UserPO(userName), new UserPO(
							invited_user));
					mes.setCommand(Message.cmd_invitepk);
					wip.sendMessage(mes);
					PanelController.moveToStage(wip);
				}
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

	class AddListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (friendName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "尚未填写好友id!", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "添加好友邀请已发出!", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
				scs.addFriends(userName, friendName.getText());
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}

	}

}
