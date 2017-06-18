package view.systemset;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.component.PanelController;
import view.game.Music;
//import view.game.Music;

/**
 * <code><b>SystemSetPanel</b></code> contains components about systemSet
 * 
 * @author 曹雨婷
 * 
 */
public class SystemSetPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	JLabel imLabel;

	JSlider musicSlider;
	JSlider soundSlider;
	JLabel musicValue;
	JLabel soundValue;

	JRadioButton down;
	JRadioButton left;

	int musicVolume;
	int soundVolume;
	boolean isDown;

	final Color color1 = new Color(225, 237, 233);
	final Color color2 = new Color(76, 124, 206);

	public SystemSetPanel(int musicVolume, int soundVolume, boolean isDown) {

		this.musicVolume = musicVolume;
		this.soundVolume = soundVolume;
		this.isDown = isDown;

		setLayout(null);
		setBounds(0, 0, 1016, 678);

		bacgrd = new ImageIcon("img/setBg.jpg");

		setComp();
		setVisible(true);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(0, 0, color1, this.getWidth(), this
				.getHeight(), color2));
		g2d.fill(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
	}

	public void setComp() {
		// 音乐音量
		final JLabel musicLabel = new JLabel("音乐音量");
		musicLabel.setBounds(5, 25, 100, 30);
		musicLabel.setForeground(new Color(0, 0, 139));
		musicLabel.setFont(new Font("楷体", Font.BOLD, 20));
		add(musicLabel, 0);

		// 音效音量
		final JLabel soundLabel = new JLabel("音效音量");
		soundLabel.setBounds(5, 95, 100, 30);
		soundLabel.setForeground(new Color(0, 0, 139));
		soundLabel.setFont(new Font("楷体", Font.BOLD, 20));
		add(soundLabel, 0);

		// JSlider滑标元件
		musicSlider = new JSlider(0, 100, musicVolume);
		musicSlider.setBounds(145, 25, 400, 60);
		musicSlider.setOpaque(false);
		musicSlider.setMajorTickSpacing(20);
		musicSlider.setMinorTickSpacing(5);
		musicSlider.setPaintTicks(true);
		musicSlider.setPaintLabels(true);

		musicSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				musicValue.setText("(" + musicSlider.getValue() + "" + ")");
				writeFile(musicSlider.getValue() + "", 0);
				PanelController.mainFrame.music.setVolume(
						(double) musicSlider.getValue(), true);
			}
		});
		musicSlider.addMouseListener(new HandListener());

		add(musicSlider, 0);

		soundSlider = new JSlider(0, 100, soundVolume);
		soundSlider.setBounds(145, 95, 400, 60);
		soundSlider.setOpaque(false);
		soundSlider.setMajorTickSpacing(20);
		soundSlider.setMinorTickSpacing(5);
		soundSlider.setPaintTicks(true);
		soundSlider.setPaintLabels(true);

		soundSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				soundValue.setText("(" + soundSlider.getValue() + "" + ")");
			}
		});
		soundSlider.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				writeFile(soundSlider.getValue() + "", 1);
				new Music("select");
				// 鼠标松开执行
			}
		});
		soundSlider.addMouseListener(new HandListener());

		add(soundSlider, 0);

		// 显示标签
		musicValue = new JLabel("(" + musicSlider.getValue() + "" + ")");
		musicValue.setBounds(85, 25, 60, 30);
		musicValue.setForeground(new Color(0, 0, 139));
		musicValue.setFont(new Font("楷体", Font.BOLD, 20));
		add(musicValue, 0);

		soundValue = new JLabel("(" + soundSlider.getValue() + "" + ")");
		soundValue.setBounds(85, 95, 60, 30);
		soundValue.setForeground(new Color(0, 0, 139));
		soundValue.setFont(new Font("楷体", Font.BOLD, 20));
		add(soundValue, 0);

		// 掉落方向
		final JLabel directionLabel = new JLabel("掉落方向");
		directionLabel.setBounds(25, 160, 110, 30);
		directionLabel.setForeground(new Color(0, 0, 139));
		directionLabel.setFont(new Font("楷体", Font.BOLD, 20));
		add(directionLabel, 0);

		down = new JRadioButton("自上而下");
		down.setBounds(180, 170, 120, 20);
		down.setFont(new Font("楷体", Font.PLAIN, 20));
		down.setSelected(isDown);
		down.setOpaque(false);

		down.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent arg0) {
				writeFile(down.isSelected() + "", 2);
			}
		});
		down.addMouseListener(new HandListener());
		add(down, 0);

		left = new JRadioButton("自左而右");
		left.setBounds(300, 170, 120, 20);
		left.setFont(new Font("楷体", Font.PLAIN, 20));
		left.setSelected(!isDown);
		left.setOpaque(false);
		left.addMouseListener(new HandListener());
		add(left, 0);

		ButtonGroup group = new ButtonGroup();
		group.add(down);
		group.add(left);

	}

	class HandListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			writeFile(down.isSelected() + "", 2);
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

	public void writeFile(String info, int num) {
		File file = new File("SystemSet");
		String writeInfo = "";

		String result = readFile("SystemSet");
		int mv = Integer.parseInt(result.split("%")[0]);
		int sv = Integer.parseInt(result.split("%")[1]);
		String isDownStr = result.split("%")[2];
		boolean isDown = isDownStr.equals("true") ? true : false;

		try {
			FileWriter fr = new FileWriter(file);
			BufferedWriter bufr = new BufferedWriter(fr);

			if (num == 0)
				writeInfo += info + "%" + sv + "%" + isDown; // musicSlider
			else if (num == 1)
				writeInfo += mv + "%" + info + "%" + isDown; // soundSlider
			else if (num == 2)
				writeInfo += mv + "%" + sv + "%" + info; // down

			bufr.write(writeInfo);
			bufr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(String fileName) {
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

}
