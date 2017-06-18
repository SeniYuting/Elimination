package view.props;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import netservice.SocketClientService;
import view.choose.ChoosePanel;
import view.component.MyJButton;
import view.component.PWModelListener;
import view.component.PanelController;
import view.component.RButton;
import view.component.SetListener;
import view.game.GamePanel;
import view.game.InvitedPk;
import view.help.HelpPanel;
import view.rank.RankPanel;
import view.user.InviteFriendPanel;
import view.user.UserInfoPanel;
import controller.props.PropsController;
import controllerservice.props.PropsControllerService;

/**
 * <code><b>ChoosePropsPanel</b></code> contains components about choose props,
 * including propA, propB, propC, propF and propG
 * 
 * @author 曹雨婷
 * 
 */
public class ChoosePropsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	JLabel imLabel;

	JLabel coinLabel;
	JLabel coinNumLabel;
	JCheckBox propA;
	JCheckBox propB;
	JCheckBox propC;
	JCheckBox propF; // 时光倒流
	JCheckBox propG; // 鲨鱼导弹

	String userName;
	private String to_user;
	int mode;
	SocketClientService s;

	public ChoosePropsPanel(String userName, int mode, SocketClientService s) {
		this.userName = userName;
		this.mode = mode;
		this.s = s;

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

	public void setCoinLabel(int coin) {
		coinNumLabel.setText(" " + coin);
	}

	public void setBacgrd(ImageIcon bacgrd) {
		JLabel imLabel = new JLabel(bacgrd);
		imLabel.setBounds(0, 0, bacgrd.getIconWidth(), bacgrd.getIconHeight());
		add(imLabel);
	}

	public void setComp() {
		// 返回按钮
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());

		// 选择道具
		final JLabel setLabel = new JLabel("选择道具");
		setLabel.setBounds(340, 100, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("楷体", Font.BOLD, 30));
		add(setLabel, 0);

		// 金币标签
		coinLabel = new JLabel("我的金币:");
		coinLabel.setBounds(40, 145, 150, 30);
		coinLabel.setForeground(new Color(255, 255, 255));
		coinLabel.setFont(new Font("楷体", Font.BOLD, 23));
		add(coinLabel, 0);

		// 金币数标签
		coinNumLabel = new JLabel();
		coinNumLabel.setBounds(170, 145, 150, 30);
		coinNumLabel.setForeground(new Color(0, 0, 255));
		coinNumLabel.setFont(new Font("楷体", Font.BOLD, 23));
		add(coinNumLabel, 0);

		// 复选框
		propA = new JCheckBox("轻松连锁");
		propA.setSelected(false);
		propA.setBounds(75, 365, 90, 30);
		propA.addMouseListener(new propsListener());
		add(propA, 0);

		propB = new JCheckBox("得分Up！");
		propB.setSelected(false);
		propB.setBounds(295, 365, 90, 30);
		propB.addMouseListener(new propsListener());
		add(propB, 0);

		propC = new JCheckBox("快速提示");
		propC.setSelected(false);
		propC.setBounds(515, 365, 90, 30);
		propC.addMouseListener(new propsListener());
		add(propC, 0);

		propF = new JCheckBox("时光倒流");
		propF.setSelected(false);
		propF.setBounds(175, 565, 90, 30);
		propF.addMouseListener(new propsListener());
		add(propF, 0);

		propG = new JCheckBox("鲨鱼导弹");
		propG.setSelected(false);
		propG.setBounds(410, 565, 90, 30);
		propG.addMouseListener(new propsListener());
		add(propG, 0);

		// 图片
		ImageIcon propAIcon = new ImageIcon("img/props/轻松连锁.png");
		MyJButton propAButton = new MyJButton(propAIcon);
		propAButton.setBounds(55, 205, propAIcon.getIconWidth(),
				propAIcon.getIconHeight());
		propAButton.addMouseListener(new propAButtonListener());
		add(propAButton, 0);

		ImageIcon propBIcon = new ImageIcon("img/props/得分UP！.png");
		MyJButton propBButton = new MyJButton(propBIcon);
		propBButton.setBounds(275, 205, propBIcon.getIconWidth(),
				propBIcon.getIconHeight());
		propBButton.addMouseListener(new propBButtonListener());
		add(propBButton, 0);

		ImageIcon propCIcon = new ImageIcon("img/props/快速提示.png");
		MyJButton propCButton = new MyJButton(propCIcon);
		propCButton.setBounds(495, 205, propCIcon.getIconWidth(),
				propCIcon.getIconHeight());
		propCButton.addMouseListener(new propCButtonListener());
		add(propCButton, 0);

		ImageIcon propFIcon = new ImageIcon("img/props/时光倒流.png");
		MyJButton propFButton = new MyJButton(propFIcon);
		propFButton.setBounds(150, 405, propFIcon.getIconWidth(),
				propFIcon.getIconHeight());
		propFButton.addMouseListener(new propFButtonListener());
		add(propFButton, 0);

		ImageIcon propGIcon = new ImageIcon("img/props/鲨鱼导弹.png");
		MyJButton propGButton = new MyJButton(propGIcon);
		propGButton.setBounds(370, 405, propGIcon.getIconWidth(),
				propGIcon.getIconHeight());
		propGButton.addMouseListener(new propGButtonListener());
		add(propGButton, 0);

		// 按钮
		if (mode == 0 || mode == 1) {
			final RButton beginGame = new RButton("开始游戏");
			beginGame.setBounds(800, 370, 150, 40);
			beginGame.setFont(new Font("楷体", Font.BOLD, 21));
			beginGame.addMouseListener(new beginGameListener());
			add(beginGame, 0);
		} else if (mode == 2) {
			final RButton inviteFriend = new RButton("邀请好友");
			inviteFriend.setBounds(800, 370, 150, 40);
			inviteFriend.setFont(new Font("楷体", Font.BOLD, 21));
			inviteFriend.addMouseListener(new inviteFriendListener());
			add(inviteFriend, 0);
		}else if (mode == 4) {
			final RButton prepare = new RButton(" 准 备 ");
			prepare.setBounds(800, 370, 150, 40);
			prepare.setFont(new Font("楷体", Font.BOLD, 21));
			prepare.addMouseListener(new prepareListener());
			add(prepare, 0);
		}else{
			final RButton inviteFriend = new RButton("邀请好友");
			inviteFriend.setBounds(800, 370, 150, 40);
			inviteFriend.setFont(new Font("楷体", Font.BOLD, 21));
			inviteFriend.addMouseListener(new inviteFriendListener());
			add(inviteFriend, 0);
		}

		final RButton helpGame = new RButton("查看帮助");
		helpGame.setBounds(800, 470, 150, 40);
		helpGame.setFont(new Font("楷体", Font.BOLD, 21));
		helpGame.addMouseListener(new HelpGameListener());
		add(helpGame, 0);

		ImageIcon pw = new ImageIcon("img/pw_set.png");
		MyJButton pwButton = new MyJButton(pw);
		pwButton.setBounds(715, 0, pw.getIconWidth(), pw.getIconHeight());
		add(pwButton, 0);

		JLabel pwModel = new JLabel("<HTML><U>修改密码</U></HTML>");
		pwModel.setBounds(778, 45, 120, 40);
		pwModel.setFont(new Font("楷体", Font.PLAIN, 0));
		pwModel.setOpaque(false);
		pwModel.addMouseListener(new PWModelListener(userName, s));
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

	class returnButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (mode == 0) {
				UserInfoPanel userInfoPanel = new UserInfoPanel(userName, mode,
						PanelController.s);
				PanelController.moveToStage(userInfoPanel,
						ChoosePropsPanel.this, mode);
			} else {
				RankPanel rankPanel = null;
				if (mode == 1 || mode == 2) {
					rankPanel = new RankPanel(userName, mode, s);
					PanelController.moveToStage(rankPanel,
							ChoosePropsPanel.this, mode);
				} else if (mode == 3) {
					ChoosePanel choosePanel = new ChoosePanel(userName, s);
					PanelController.moveToStage(choosePanel,
							ChoosePropsPanel.this, mode);
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

	class propsListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
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

	class propAButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!propA.isSelected())
				propA.setSelected(true);
			else
				propA.setSelected(false);
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

	class propBButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!propB.isSelected())
				propB.setSelected(true);
			else
				propB.setSelected(false);
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

	class propCButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!propC.isSelected())
				propC.setSelected(true);
			else
				propC.setSelected(false);
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

	class propFButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!propF.isSelected())
				propF.setSelected(true);
			else
				propF.setSelected(false);
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

	class propGButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!propG.isSelected())
				propG.setSelected(true);
			else
				propG.setSelected(false);
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

	class beginGameListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			ChoosePropsPanel choosePropsPanel = ChoosePropsPanel.this;
			boolean isUp = choosePropsPanel.propB.isSelected();
			boolean isPrompt = choosePropsPanel.propC.isSelected();
			boolean isChain = choosePropsPanel.propA.isSelected();
			boolean isBack = choosePropsPanel.propF.isSelected();
			boolean isShark = choosePropsPanel.propG.isSelected();

			String result = readFile("SystemSet");
			int mv = Integer.parseInt(result.split("%")[0]);
			int sv = Integer.parseInt(result.split("%")[1]);
			String isDownStr = result.split("%")[2];
			boolean isDown = isDownStr.equals("true") ? true : false;

			boolean isLeftToRight = !isDown;

			GamePanel gamePanel = new GamePanel(userName, mode, isLeftToRight,
					isUp, isPrompt, isChain, isBack, isShark);
			gamePanel.setMusicAndSound(mv, sv);

			PropsControllerService propsControllerService = new PropsController(
					s);
			int isPlay;

			if (mode == 0) {
				isPlay = propsControllerService.updateLocalCoin(isUp, isPrompt,
						isChain, isBack, isShark);
			} else {
				isPlay = propsControllerService.updateCoin(isUp, isPrompt,
						isChain, isBack, isShark, userName);
			}

			if (isPlay == -1) {
				JOptionPane.showMessageDialog(null, "金币数不够", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				PanelController
						.moveToStage(gamePanel, ChoosePropsPanel.this, 1);
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

	class inviteFriendListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			ChoosePropsPanel choosePropsPanel = ChoosePropsPanel.this;
			boolean isUp = choosePropsPanel.propB.isSelected();
			boolean isPrompt = choosePropsPanel.propC.isSelected();
			boolean isChain = choosePropsPanel.propA.isSelected();
			boolean isBack = choosePropsPanel.propF.isSelected();
			boolean isShark = choosePropsPanel.propG.isSelected();

			InviteFriendPanel coopInviteFriendPanel = new InviteFriendPanel(
					userName, mode, PanelController.s, isUp, isPrompt, isChain,
					isBack, isShark);
			PanelController.moveToStage(coopInviteFriendPanel,
					ChoosePropsPanel.this, 2);
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

	class prepareListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			ChoosePropsPanel choosePropsPanel = ChoosePropsPanel.this;
			boolean isUp = choosePropsPanel.propB.isSelected();
			boolean isPrompt = choosePropsPanel.propC.isSelected();
			boolean isChain = choosePropsPanel.propA.isSelected();
			boolean isBack = choosePropsPanel.propF.isSelected();
			boolean isShark = choosePropsPanel.propG.isSelected();

			String result = readFile("SystemSet");
			int mv = Integer.parseInt(result.split("%")[0]);
			int sv = Integer.parseInt(result.split("%")[1]);

			InvitedPk gamePanel = new InvitedPk(userName, 3, isUp, isPrompt,
					isChain, isBack, isShark ,to_user);
			gamePanel.setMusicAndSound(mv, sv);

			PropsControllerService propsControllerService = new PropsController(
					s);
			int isPlay;

			if (mode == 0) {
				isPlay = propsControllerService.updateLocalCoin(isUp, isPrompt,
						isChain, isBack, isShark);
			} else {
				isPlay = propsControllerService.updateCoin(isUp, isPrompt,
						isChain, isBack, isShark, userName);
			}

			if (isPlay == -1) {
				JOptionPane.showMessageDialog(null, "金币数不够", "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				PanelController
						.moveToStage(gamePanel, ChoosePropsPanel.this, 1);
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

	public String getTo_user() {
		return to_user;
	}

	public void setTo_user(String to_user) {
		this.to_user = to_user;
	}

	class HelpGameListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			HelpPanel helpPanel = new HelpPanel(userName, mode);
			PanelController.moveToStage(helpPanel, ChoosePropsPanel.this, mode);
		}

		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
		}

	}

}
