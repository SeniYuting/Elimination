package view.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * <code><b>MyJButton</b></code> 
 * extends JButton for show image
 * 
 * @author 曹雨婷
 * 
 */
public class MyJButton extends JButton {

	private static final long serialVersionUID = 1L;

	public MyJButton(ImageIcon icon) {
		super(icon);
		setContentAreaFilled(false);// JButton设置为透明
		setBorderPainted(false); // 按钮边界不显示
		setFocusPainted(false); // 图标无边框
	}
}
