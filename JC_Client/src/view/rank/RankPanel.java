package view.rank;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import netservice.SocketClientService;
import po.rank.NetSingleRankPO;
import po.rank.RankPO;
import view.choose.ChoosePanel;
import view.component.MyJButton;
import view.component.PWModelListener;
import view.component.PanelController;
import view.component.RButton;
import view.component.SetListener;
import view.props.ChoosePropsPanel;
import view.user.UserInfoPanel;
import controller.props.PropsController;
import controller.rank.CoopRankController;
import controller.rank.NetSingleRankController;
import controllerservice.props.PropsControllerService;
import controllerservice.rank.CoopRankControllerService;
import controllerservice.rank.NetSingleRankControllerService;

/**
 * <code><b>RankPropsPanel</b></code> contains components about rank
 * 
 * @author 曹雨婷
 * 
 */
public class RankPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	JLabel imLabel;

	JLabel setLabel;
	JTable rankTable;

	ArrayList<NetSingleRankPO> rankList;
	ArrayList<RankPO> coopRankList;
	
	String userName;
	int mode;
	SocketClientService s;

	public RankPanel(String userName, int mode, SocketClientService s) {
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

		// 排行榜标签
		setLabel = new JLabel("排行榜");
		setLabel.setBounds(340, 100, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("楷体", Font.BOLD, 30));
		add(setLabel, 0);
		
		ImageIcon icon = new ImageIcon("img/rank/image.png");
		MyJButton iconButton = new MyJButton(icon);
		iconButton.setBounds(35, 205, icon.getIconWidth(),
				icon.getIconHeight());
		add(iconButton, 0);

		// 排行榜
		JLabel label3 = new JLabel("Level");
		label3.setFont(new Font("楷体", Font.BOLD, 20));
		label3.setForeground(new Color(255, 255, 255));
		label3.setBounds(75, 142, 100, 35);
		add(label3, 0);

		JLabel label4 = new JLabel("用户名");
		label4.setFont(new Font("楷体", Font.BOLD, 20));
		label4.setForeground(new Color(255, 255, 255));
		label4.setBounds(306, 142, 100, 35);
		add(label4, 0);

		JLabel label5 = new JLabel("得分");
		label5.setFont(new Font("楷体", Font.BOLD, 20));
		label5.setForeground(new Color(255, 255, 255));
		label5.setBounds(547, 142, 100, 35);
		add(label5, 0);

		ArrayList<ImageIcon> iconList = getList();

		if (mode == 1) {
			NetSingleRankControllerService rservice = new NetSingleRankController();
			ArrayList<NetSingleRankPO> rankList = rservice.getRankList();

			for (int i = 0; i < rankList.size() && i < 12; i++) {   //只取12个
				JLabel num = new JLabel(iconList.get(i));
				num.setFont(new Font("楷体", Font.PLAIN, 20));
				num.setForeground(new Color(255, 255, 255));
				num.setBounds(63, 180 + i * 35, 100, 35);
				add(num, 0);

				NetSingleRankPO rank = rankList.get(i);
				JLabel user = new JLabel(rank.getUserName() + "");
				user.setFont(new Font("楷体", Font.PLAIN, 20));
				if(i<7)
				    user.setForeground(new Color(0, 0, 139));
				else
					user.setForeground(new Color(255, 255, 255));
				user.setBounds(320, 180 + i * 35, 300, 35);
				add(user, 0);
				
				JLabel score = new JLabel(rank.getScore() + "");
				score.setFont(new Font("楷体", Font.PLAIN, 20));
				if(i<3||i>9)
					score.setForeground(new Color(0, 0, 139));
				else
				    score.setForeground(new Color(255, 255, 255));
				score.setBounds(547, 180 + i * 35, 300, 35);
				add(score, 0);
			}
		} else if(mode == 2){
			CoopRankControllerService crcs = new CoopRankController(s);
			ArrayList<RankPO> rankList = crcs.getCoopRank();

			for (int i = 0; i < rankList.size() && i < 12; i++) {   //只取12个
				JLabel num = new JLabel(iconList.get(i));
				num.setFont(new Font("楷体", Font.PLAIN, 20));
				num.setForeground(new Color(0, 0, 139));
				num.setBounds(63, 180 + i * 35, 100, 35);
				add(num, 0);

				RankPO rank = rankList.get(i);
				JLabel user = new JLabel(rank.getUserNames() + "");
				user.setFont(new Font("楷体", Font.PLAIN, 20));
				if(i<7)
				    user.setForeground(new Color(0, 0, 139));
				else
					user.setForeground(new Color(255, 255, 255));
				user.setBounds(240, 180 + i * 35, 300, 35);
				add(user, 0);
				
				JLabel score = new JLabel(rank.getScore() + "");
				score.setFont(new Font("楷体", Font.PLAIN, 20));
				if(i<3||i>9)
					score.setForeground(new Color(0, 0, 139));
				else
				    score.setForeground(new Color(255, 255, 255));
				score.setBounds(547, 180 + i * 35, 300, 35);
				add(score, 0);
			}
			
		}

		// 按钮
		final RButton chooseProps = new RButton("选择道具");
		chooseProps.setBounds(800, 370, 150, 40);
		chooseProps.setFont(new Font("楷体", Font.BOLD, 21));
		chooseProps.addMouseListener(new choosePropsListener());
		add(chooseProps, 0);

		final RButton myInfo = new RButton("我的信息");
		myInfo.setBounds(800, 470, 150, 40);
		myInfo.setFont(new Font("楷体", Font.BOLD, 21));
		myInfo.addMouseListener(new myInfoListener());
		add(myInfo, 0);

		ImageIcon pw = new ImageIcon("img/pw_set.png");
		MyJButton pwButton = new MyJButton(pw);
		pwButton.setBounds(715, 0, pw.getIconWidth(),
				pw.getIconHeight());
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
	
	public ArrayList<ImageIcon> getList(){
        ArrayList<ImageIcon> iconList = new ArrayList<ImageIcon>();
        ImageIcon icon = null;
        
        for(int i=0; i<12; i++){
        	icon = new ImageIcon("img/rank/icon"+(i+1)+".png"); 
        	iconList.add(icon);
        }
		
		return iconList;
	}

	class returnButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			ChoosePanel choosePanel = new ChoosePanel(userName, s);
			PanelController.moveToStage(choosePanel, RankPanel.this, mode);
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

	class choosePropsListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			PropsControllerService propsService = new PropsController(s);
			int coin = propsService.getCoin(userName);

			ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(userName,
					mode, s);
			choosePropsPanel.setCoinLabel(coin);
			PanelController.moveToStage(choosePropsPanel, RankPanel.this, mode);
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

	class myInfoListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			UserInfoPanel userInfoPanel = new UserInfoPanel(userName, mode, s);
			PanelController.moveToStage(userInfoPanel, RankPanel.this, mode);
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