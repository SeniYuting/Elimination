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
 * @author ������
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

		// ���ذ�ť
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());

		// ������ѱ�ǩ
		setLabel = new JLabel("�������");
		setLabel.setBounds(340, 100, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("����", Font.BOLD, 30));
		add(setLabel, 0);

		// �����б�
		initFriendTable();
		friendTable.setForeground(new Color(244, 223, 28));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);// ��JScrollPane����Ϊ͸��
		scrollPane.setViewportView(friendTable);// װ�ر��
		scrollPane.setColumnHeaderView(friendTable.getTableHeader());// ����ͷ����HeaderView���֣�
		scrollPane.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��
		scrollPane.setBounds(63, 142, 300, 423);

		add(scrollPane, 0);

		JLabel add = new JLabel("����id");
		add.setBounds(80, 590, 70, 20);
		add.setForeground(new Color(0, 0, 255));
		add.setFont(new Font("����", Font.BOLD, 20));
		add(add, 0);

		friendName = new JTextField();
		friendName.setBounds(160, 590, 120, 20);
		friendName.setFont(new Font("����", Font.PLAIN, 20));
		add(friendName, 0);

		ImageIcon icon = new ImageIcon("img/user/add.png");
		MyJButton addBt = new MyJButton(icon);
		addBt.setBounds(288, 575, 40, 40);
		addBt.addMouseListener(new AddListener());
		add(addBt, 0);

		// �����б�
		initInviteTable();
		inviteTable.setForeground(new Color(0, 255, 0));

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.getViewport().setOpaque(false);
		scrollPane1.setOpaque(false);// ��JScrollPane����Ϊ͸��
		scrollPane1.setViewportView(inviteTable);// װ�ر��
		scrollPane1.setColumnHeaderView(inviteTable.getTableHeader());// ����ͷ����HeaderView���֣�
		scrollPane1.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��
		scrollPane1.setBounds(413, 402, 300, 230);
		add(scrollPane1, 0);

		// ���ߺ����б�
		initOnlineFriendTable();
		onlineFriendTable.setForeground(new Color(0, 255, 0));

		JScrollPane scrollPane0 = new JScrollPane();
		scrollPane0.getViewport().setOpaque(false);
		scrollPane0.setOpaque(false);// ��JScrollPane����Ϊ͸��
		scrollPane0.setViewportView(onlineFriendTable);// װ�ر��
		scrollPane0.setColumnHeaderView(onlineFriendTable.getTableHeader());// ����ͷ����HeaderView���֣�
		scrollPane0.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��
		scrollPane0.setBounds(413, 142, 300, 230);
		add(scrollPane0, 0);

		RButton inviteButton = new RButton("����");
		inviteButton.setBounds(800, 470, 100, 40);
		inviteButton.setFont(new Font("����", Font.BOLD, 21));
		inviteButton.addMouseListener(new InviteListener());
		add(inviteButton, 0);

		ImageIcon pw = new ImageIcon("img/pw_set.png");
		MyJButton pwButton = new MyJButton(pw);
		pwButton.setBounds(715, 0, pw.getIconWidth(), pw.getIconHeight());
		add(pwButton, 0);

		JLabel pwModel = new JLabel("<HTML><U>�޸�����</U></HTML>");
		pwModel.setBounds(778, 45, 120, 40);
		pwModel.setFont(new Font("����", Font.PLAIN, 0));
		pwModel.setOpaque(false);
		pwModel.addMouseListener(new PWModelListener(userName, scs));
		pwModel.addMouseListener(new HandListener());
		add(pwModel, 0);

		JLabel set = new JLabel("<HTML><U>ϵͳ����</U></HTML>");
		set.setBounds(778, 85, 120, 40);
		set.setFont(new Font("����", Font.PLAIN, 0));
		set.setOpaque(false);
		set.addMouseListener(new SetListener());
		set.addMouseListener(new HandListener());
		add(set, 0);

	}

	private void initFriendTable() {
		String[] columnName = new String[] { "�����б�" };

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
		render.setOpaque(false); // ����Ⱦ������Ϊ͸��
		friendTable.setDefaultRenderer(Object.class, render);

		// ������ʾ��Χ
		Dimension viewSize = new Dimension();
		viewSize.width = friendTable.getColumnModel().getTotalColumnWidth();
		viewSize.height = 10 * friendTable.getRowHeight();
		friendTable.setPreferredScrollableViewportSize(viewSize);

		friendTable.setFont(new Font("����", Font.PLAIN, 18));
		friendTable.getTableHeader().setResizingAllowed(false);// �����Զ�������С
		friendTable.getTableHeader().setReorderingAllowed(false);// ���������ƶ�

		// ����ͷ��͸��
		JTableHeader header = friendTable.getTableHeader();// ��ȡͷ��
		header.setPreferredSize(new Dimension(30, 26));
		header.setOpaque(false);// ����ͷ��Ϊ͸��
		header.getTable().setOpaque(false);// ����ͷ������ı��͸��
		header.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer = header.getDefaultRenderer();
		if (headerRenderer instanceof JLabel) {
			((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer).setOpaque(false);
		}
	}

	private void initInviteTable() {
		String[] columnName = new String[] { "�����б�" };
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
		render.setOpaque(false); // ����Ⱦ������Ϊ͸��
		inviteTable.setDefaultRenderer(Object.class, render);

		// ������ʾ��Χ
		Dimension viewSize = new Dimension();
		viewSize.width = inviteTable.getColumnModel().getTotalColumnWidth();
		viewSize.height = 10 * inviteTable.getRowHeight();
		inviteTable.setPreferredScrollableViewportSize(viewSize);

		inviteTable.getTableHeader().setFont(new Font("����", Font.PLAIN, 18));
		inviteTable.setFont(new Font("����", Font.PLAIN, 18));
		inviteTable.getTableHeader().setResizingAllowed(false);// �����Զ�������С
		inviteTable.getTableHeader().setReorderingAllowed(false);// ���������ƶ�

		// ����ͷ��͸��
		JTableHeader header = inviteTable.getTableHeader();// ��ȡͷ��
		header.setPreferredSize(new Dimension(30, 26));
		header.setOpaque(false);// ����ͷ��Ϊ͸��
		header.getTable().setOpaque(false);// ����ͷ������ı��͸��

		header.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer = header.getDefaultRenderer();
		if (headerRenderer instanceof JLabel) {
			((JLabel) headerRenderer).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer).setOpaque(false);
		}

		inviteModel = (DefaultTableModel) inviteTable.getModel();
	}

	private void initOnlineFriendTable() {
		String[] columnName = new String[] { "���ߺ����б�" };
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

		onlineFriendTable.addMouseListener(new MouseAdapter() { // �Ҽ�
					public void mousePressed(MouseEvent e) {
						if (e.getButton() == 3) {
							JPopupMenu popmenu = new JPopupMenu();
							JMenuItem item = new JMenuItem("��ӵ������б� ");
							item.setFont(new Font("����", Font.BOLD, 15));
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
												"�ú���������������б�");
									}

									JScrollPane scrollPane1 = new JScrollPane();
									scrollPane1.getViewport().setOpaque(false);
									scrollPane1.setOpaque(false);// ��JScrollPane����Ϊ͸��
									scrollPane1.setViewportView(inviteTable);// װ�ر��
									scrollPane1.setColumnHeaderView(inviteTable
											.getTableHeader());// ����ͷ����HeaderView���֣�
									scrollPane1.getColumnHeader().setOpaque(
											false);// ��ȡ��ͷ����������Ϊ͸��
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
		render.setOpaque(false); // ����Ⱦ������Ϊ͸��
		onlineFriendTable.setDefaultRenderer(Object.class, render);

		// ������ʾ��Χ
		Dimension viewSize = new Dimension();
		viewSize.width = onlineFriendTable.getColumnModel()
				.getTotalColumnWidth();
		viewSize.height = 10 * onlineFriendTable.getRowHeight();
		onlineFriendTable.setPreferredScrollableViewportSize(viewSize);

		onlineFriendTable.getTableHeader().setFont(
				new Font("����", Font.PLAIN, 18));
		onlineFriendTable.setFont(new Font("����", Font.PLAIN, 18));
		onlineFriendTable.getTableHeader().setResizingAllowed(false);// �����Զ�������С
		onlineFriendTable.getTableHeader().setReorderingAllowed(false);// ���������ƶ�

		// ����ͷ��͸��
		JTableHeader header = onlineFriendTable.getTableHeader();// ��ȡͷ��
		header.setPreferredSize(new Dimension(30, 26));
		header.setOpaque(false);// ����ͷ��Ϊ͸��
		header.getTable().setOpaque(false);// ����ͷ������ı��͸��

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
					JOptionPane.showMessageDialog(null, "��δ�������");
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
					// �������������Ϸ��ʼ��Ϣ
				}
			} else if (mode == 3) {// ��ս
				if (inviteTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "��δ�������");
				} else if (inviteTable.getRowCount() > 1) {
					JOptionPane.showMessageDialog(null, "��������һλ����");
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
				JOptionPane.showMessageDialog(null, "��δ��д����id!", "ϵͳ��Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "��Ӻ��������ѷ���!", "ϵͳ��Ϣ",
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
