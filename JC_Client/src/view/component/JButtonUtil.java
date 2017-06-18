package view.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * <code><b>JButtonUtil</b></code> contains components about button
 * 
 * @author 曹雨婷
 * 
 */
public class JButtonUtil {
	public static JButton getBtnClose() {
		JButton btnClose = new JButton();
		btnClose.setRolloverIcon(new ImageIcon(
				"img/start/close_down.png"));
		btnClose.setPressedIcon(new ImageIcon(
				"img/start/close_highlight.png"));
		btnClose.setIcon(new ImageIcon(
				"img/start/close_normal.png"));
		btnClose.setToolTipText("关闭");
		btnClose.setBorder(null);
		btnClose.setFocusPainted(false);
		btnClose.setContentAreaFilled(false);

		return btnClose;
	}

	public static JButton getBtnMin() {
		JButton btnMin = new JButton();
		btnMin.setRolloverIcon(new ImageIcon(
				"img/start/mini_down.png"));
		btnMin.setPressedIcon(new ImageIcon(
				"img/start/mini_highlight.png"));
		btnMin.setIcon(new ImageIcon("img/start/mini_normal.png"));
		btnMin.setToolTipText("最小化");
		btnMin.setBorder(null);
		btnMin.setFocusPainted(false);
		btnMin.setContentAreaFilled(false);
		return btnMin;
	}
}
