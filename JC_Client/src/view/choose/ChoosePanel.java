package view.choose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import netservice.SocketClientService;
import view.component.MyJButton;
import view.component.PWModelListener;
import view.component.PanelController;
import view.component.RButton;
import view.component.SetListener;
import view.props.ChoosePropsPanel;
import view.rank.RankPanel;
import controller.props.PropsController;
import controllerservice.props.PropsControllerService;

/**
 * <code><b>ChoosePanel</b></code> contains components about choose mode
 * 
 * @author 曹雨婷
 * 
 */
public class ChoosePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	JLabel imLabel;

	ImageIcon image;
	JLabel label;

	MyJButton exitButton;

	int i = 0;

	String userName;
	SocketClientService s;

	public ChoosePanel(String userName, SocketClientService s) {
		this.userName = userName;
		this.s = s;
		setLayout(null);
		setBounds(0, 0, 1016, 678);

		bacgrd = new ImageIcon("img/Background.jpg");
		setBacgrd(bacgrd);

		setComp();
		setVisible(true);

	}

	public void setBacgrd(ImageIcon bacgrd) {
		JLabel imLabel = new JLabel(bacgrd);
		imLabel.setBounds(0, 0, bacgrd.getIconWidth(), bacgrd.getIconHeight());
		add(imLabel);
	}

	public void setComp() {
		// 选择模式
		final JLabel setLabel = new JLabel("选择模式");
		setLabel.setBounds(340, 100, 150, 30);
		setLabel.setForeground(new Color(255, 255, 255));
		setLabel.setFont(new Font("楷体", Font.BOLD, 30));
		add(setLabel, 0);

		// 图片标签
		image = new ImageIcon(getOneImage(0));
		label = new JLabel(image);
		label.setBounds(10, 230, 300, 300);
		add(label, 0);

		// 单机模式
		final RButton single = new RButton("单机模式");
		single.setBounds(500, 270, 150, 40);
		single.setFont(new Font("楷体", Font.BOLD, 21));
		single.addMouseListener(new SingleListener());
		add(single, 0);

		// 协作模式
		final RButton collaboration = new RButton("协作模式");
		collaboration.setBounds(500, 370, 150, 40);
		collaboration.setFont(new Font("楷体", Font.BOLD, 21));
		collaboration.addMouseListener(new CollaborationListener());
		add(collaboration, 0);

		// 对战模式
		final RButton pk = new RButton("对战模式");
		pk.setBounds(500, 470, 150, 40);
		pk.setFont(new Font("楷体", Font.BOLD, 21));
		pk.addMouseListener(new PKListener());
		add(pk, 0);

		// 动态图片
		ImageIcon icon = new ImageIcon("img/icon.gif");
		MyJButton iconButton = new MyJButton(icon);
		iconButton.setBounds(720, 460, icon.getIconWidth(),
				icon.getIconHeight());
		add(iconButton, 0);

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

	public Image getOneImage(int i) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("img/choose/introduction4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi.getSubimage(i * 300, 0, 300, 300);
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

	class SingleListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			RankPanel rankPanel = new RankPanel(userName, 1, PanelController.s);
			PanelController.moveToStage(rankPanel, ChoosePanel.this, 1);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			remove(label);
			image = new ImageIcon(getOneImage(1));
			label = new JLabel(image);
			label.setBounds(10, 230, 300, 300);
			add(label, 0);
			label.repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
			remove(label);
			image = new ImageIcon(getOneImage(0));
			label = new JLabel(image);
			label.setBounds(10, 230, 300, 300);
			add(label, 0);
			label.repaint();
		}
	}

	class CollaborationListener extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e) {
			RankPanel rankPanel = new RankPanel(userName, 2, s);
			PanelController.moveToStage(rankPanel, ChoosePanel.this, 2);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			remove(label);
			image = new ImageIcon(getOneImage(2));
			label = new JLabel(image);
			label.setBounds(10, 230, 300, 300);
			add(label, 0);
			label.repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
			remove(label);
			image = new ImageIcon(getOneImage(0));
			label = new JLabel(image);
			label.setBounds(10, 230, 300, 300);
			add(label, 0);
			label.repaint();
		}
	}

	class PKListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			PropsControllerService propsService = new PropsController(s);
			int coin = propsService.getCoin(userName);

			ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(userName,
					3, s);
			choosePropsPanel.setCoinLabel(coin);
			PanelController.moveToStage(choosePropsPanel, ChoosePanel.this, 3);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			remove(label);
			image = new ImageIcon(getOneImage(3));
			label = new JLabel(image);
			label.setBounds(10, 230, 300, 300);
			add(label, 0);
			label.repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(Cursor.getDefaultCursor());
			remove(label);
			image = new ImageIcon(getOneImage(0));
			label = new JLabel(image);
			label.setBounds(10, 230, 300, 300);
			add(label, 0);
			label.repaint();
		}
	}

}
