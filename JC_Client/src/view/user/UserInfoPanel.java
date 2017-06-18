package view.user;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import localdata.LocalDataHelper;
import netservice.SocketClientService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import po.user.UserPO;
import view.component.MyJButton;
import view.component.PWModelListener;
import view.component.PanelController;
import view.component.RButton;
import view.component.SetListener;
import view.props.ChoosePropsPanel;
import view.rank.RankPanel;
import controller.user.LoginController;
import controllerservice.user.LoginControllerService;

/**
 * <code><b>UserInfoPanel</b></code> contains components about UserInfo
 * 
 * @author 刘硕 曹雨婷
 * 
 */
public class UserInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	ImageIcon bacgrd;
	JLabel imLabel;
	JLabel label1;
	JLabel label2;
	MyJButton exitButton;
	JLabel setLabel;

	String userName;
	int mode;
	SocketClientService s;

	public UserInfoPanel(String userName, int mode, SocketClientService s) {
		this.userName = userName;
		this.mode = mode;
		this.s = s;

		setLayout(null);
		setBounds(0, 0, 1016, 678);
		setComp();

		bacgrd = new ImageIcon("img/Background.jpg");
		setBacgrd(bacgrd);

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

		setLabel = new JLabel("成就榜");
		setLabel.setBounds(340, 100, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("楷体", Font.BOLD, 30));
		add(setLabel, 0);

		LoginControllerService lcs = new LoginController(s);
		LocalDataHelper ldh = new LocalDataHelper();
		UserPO myPO = new UserPO();

		if (mode == 0) {
			myPO = ldh.getFullUserPO();
		} else {
			myPO = lcs.getUserPO(userName);
		}

		if (mode != 0) { // 除游客模式---返回按钮
			ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
			MyJButton returnButton = new MyJButton(returnIcon);
			returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
					returnIcon.getIconHeight());
			add(returnButton, 0);
			returnButton.addMouseListener(new returnButtonListener());
		}

		JLabel setLabel = new JLabel("最近10局得分");
		setLabel.setBounds(485, 150, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("楷体", Font.BOLD, 23));
		add(setLabel, 0);
		
		JLabel label3 = new JLabel("局次");
		label3.setFont(new Font("楷体", Font.BOLD, 20));
		label3.setForeground(new Color(255, 255, 255));
		label3.setBounds(470, 200, 100, 35);
		add(label3, 0);

		JLabel label4 = new JLabel("得分");
		label4.setFont(new Font("楷体", Font.BOLD, 20));
		label4.setForeground(new Color(255, 255, 255));
		label4.setBounds(620, 200, 100, 35);
		add(label4, 0);
		
		ArrayList<ImageIcon> iconList = getList();
		
		ArrayList<Integer> recentScore = myPO.getRecentGameScores();

		for (int i = 0; i < recentScore.size(); i++) {
			JLabel num = new JLabel(iconList.get(i));
			num.setFont(new Font("楷体", Font.PLAIN, 20));
			num.setForeground(new Color(0, 0, 139));
			num.setBounds(440, 240 + i * 35, 100, 35);
			add(num, 0);
			
			JLabel score = new JLabel(recentScore.get(i)+"");
			score.setFont(new Font("楷体", Font.PLAIN, 20));
			score.setForeground(new Color(0, 0, 139));
			score.setBounds(620, 240 + i * 35, 300, 35);
			add(score, 0);
		}

		label1 = new JLabel("<HTML><U>每日局数曲线</U></HTML>");
		label1.setFont(new Font("楷体", Font.BOLD, 21));
		label1.setForeground(new Color(0, 0, 139));
		label1.addMouseListener(new Button1Listener());

		label2 = new JLabel("<HTML><U>每日得分曲线</U></HTML>");
		label2.setFont(new Font("楷体", Font.BOLD, 21));
		label2.setForeground(new Color(0, 0, 139));
		label2.addMouseListener(new Button2Listener());
		
		ImageIcon icon = new ImageIcon("img/user/line.png");
		MyJButton iconButton = new MyJButton(icon);
		iconButton.setBounds(70, 360, icon.getIconWidth(),
				icon.getIconHeight());
		add(iconButton, 0);
	
		label1.setBounds(50, 550, 150, 40);
		label2.setBounds(210, 550, 150, 40);

		if (mode == 0) { // 游客模式			
			final RButton chooseProps = new RButton("选择道具");
			chooseProps.setBounds(800, 420, 150, 40);
			chooseProps.setFont(new Font("楷体", Font.BOLD, 21));
			chooseProps.addMouseListener(new choosePropsListener());
			add(chooseProps, 0);
		}

		add(label1);
		add(label2);

		int gameNum = myPO.getGameNum();
		JLabel totalnum = new JLabel("总局数:" + gameNum);
		totalnum.setBounds(50, 190, 160, 30);
		totalnum.setFont(new Font("楷体", Font.BOLD, 20));
		totalnum.setForeground(new Color(0, 0, 139));
		add(totalnum);

		int maxCombo = myPO.getMaxCombo();
		JLabel maxComb = new JLabel("最高连击:" + maxCombo);
		maxComb.setBounds(210, 190, 160, 30);
		maxComb.setFont(new Font("楷体", Font.BOLD, 20));
		maxComb.setForeground(new Color(0, 0, 139));
		add(maxComb);

		int max = myPO.getMaxScore();
		JLabel maxScore = new JLabel("最大得分:" + max);
		maxScore.setBounds(50, 270, 160, 30);
		maxScore.setFont(new Font("楷体", Font.BOLD, 20));
		maxScore.setForeground(new Color(0, 0, 139));
		add(maxScore);

		int avg = myPO.getAvgScore();
		JLabel aveScore = new JLabel("平均得分:" + avg);
		aveScore.setBounds(210, 270, 160, 30);
		aveScore.setFont(new Font("楷体", Font.BOLD, 20));
		aveScore.setForeground(new Color(0, 0, 139));
		add(aveScore);

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

		setVisible(true);

	}

	public ArrayList<ImageIcon> getList(){
        ArrayList<ImageIcon> iconList = new ArrayList<ImageIcon>();
        ImageIcon icon = null;
        
        for(int i=0; i<10; i++){
        	icon = new ImageIcon("img/rank/icon"+(i+1)+".png"); 
        	iconList.add(icon);
        }
		
		return iconList;
	}
	
	class returnButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			RankPanel rankPanel = new RankPanel(userName, mode, PanelController.s);
			PanelController.moveToStage(rankPanel, UserInfoPanel.this, mode);
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

	class Button1Listener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			StandardChartTheme mChartTheme = new StandardChartTheme("CN");
			mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
			mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
			mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
			ChartFactory.setChartTheme(mChartTheme);

			if (mode == 0) {
				CategoryDataset mDataset = GetDataset_gameNum(0);
				JFreeChart mChart = ChartFactory.createLineChart("折线图", "日期",
						"局数", mDataset, PlotOrientation.VERTICAL, true, true,
						false);
				ChartFrame mChartFrame = new ChartFrame("每日局数曲线", mChart);
				mChartFrame.pack();
				mChartFrame.setBounds(200, 70, 950, 600);
				mChartFrame.setVisible(true);
			} else {
				CategoryDataset mDataset = GetDataset_gameNum(1);
				JFreeChart mChart = ChartFactory.createLineChart("折线图", "日期",
						"局数", mDataset, PlotOrientation.VERTICAL, true, true,
						false);
				ChartFrame mChartFrame = new ChartFrame("每日局数曲线", mChart);
				mChartFrame.pack();
				mChartFrame.setBounds(200, 70, 950, 600);
				mChartFrame.setVisible(true);
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

	public CategoryDataset GetDataset_gameNum(int mode) {
		UserPO myPO = new UserPO();

		if (mode == 0) {
			LocalDataHelper ldh = new LocalDataHelper();
			myPO = ldh.getFullUserPO();
		} else {
			LoginControllerService lcs = new LoginController(s);
			myPO = lcs.getUserPO(userName);
		}

		HashMap<Date, Integer> dailyGameNum = myPO.getDailyTotalGameNum();
		ArrayList<Date> date = myPO.getDate();
		//比较日期大小，按从小到大的顺序显示
		for(int i = 0;i < 6 && i < date.size() - 1;i ++){
			for(int j = i + 1;j < 7 && j < date.size();j ++){
				if(date.get(j).before(date.get(i))){
					Date tmp = date.get(i);
					date.set(i, date.get(j));
					date.set(j, tmp);
				}
			}
		}
		DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
		for (int i = 0; i < 7 && i < date.size(); i++) {
			mDataset.addValue(dailyGameNum.get(date.get(i)), "每日总局数",
					date.get(i));
		}

		return mDataset;
	}

	class Button2Listener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			StandardChartTheme mChartTheme2 = new StandardChartTheme("CN");
			mChartTheme2.setLargeFont(new Font("黑体", Font.BOLD, 20));
			mChartTheme2.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
			mChartTheme2.setRegularFont(new Font("宋体", Font.PLAIN, 15));
			ChartFactory.setChartTheme(mChartTheme2);
			if (mode == 0) {
				CategoryDataset mDataset = GetDataset_avgScore(0);

				JFreeChart mChart = ChartFactory.createLineChart("折线图", "日期",
						"平均得分", mDataset, PlotOrientation.VERTICAL, true, true,
						false);
				ChartFrame mChartFrame2 = new ChartFrame("每日得分曲线", mChart);
				mChartFrame2.pack();
				mChartFrame2.setBounds(200, 70, 950, 600);
				mChartFrame2.setVisible(true);
			} else {
				CategoryDataset mDataset = GetDataset_avgScore(1);

				JFreeChart mChart = ChartFactory.createLineChart("折线图", "日期",
						"平均得分", mDataset, PlotOrientation.VERTICAL, true, true,
						false);
				ChartFrame mChartFrame2 = new ChartFrame("每日得分曲线", mChart);
				mChartFrame2.pack();
				mChartFrame2.setBounds(200, 70, 950, 600);
				mChartFrame2.setVisible(true);
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

	public CategoryDataset GetDataset_avgScore(int mode) {
		UserPO myPO = new UserPO();

		if (mode == 0) {
			LocalDataHelper ldh = new LocalDataHelper();
			myPO = ldh.getFullUserPO();
		} else {
			LoginControllerService lcs = new LoginController(s);
			myPO = lcs.getUserPO(userName);
		}

		HashMap<Date, Integer> dailyAvgScore = myPO.getDailyAvgScore();
		ArrayList<Date> date = myPO.getDate();
		
		//比较日期大小，按从小到大的顺序显示
		for(int i = 0;i < 6 && i < date.size() - 1;i ++){
			for(int j = i + 1;j < 7 && j < date.size();j ++){
				if(date.get(j).before(date.get(i))){
					Date tmp = date.get(i);
					date.set(i, date.get(j));
					date.set(j, tmp);
				}
			}
		}
		
		DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
		for (int i = 0; i < 7 && i < date.size(); i++) {
			mDataset.addValue(dailyAvgScore.get(date.get(i)), "每日平均得分",
					date.get(i));
		}

		return mDataset;
	}

	// 游客模式下选择道具
	class choosePropsListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			LocalDataHelper ldh = new LocalDataHelper();
			UserPO po = ldh.getFullUserPO();
			int coin = po.getCoin();

			ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(userName,
					mode, s);
			choosePropsPanel.setCoinLabel(coin);
			PanelController.moveToStage(choosePropsPanel, UserInfoPanel.this,
					mode);
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
