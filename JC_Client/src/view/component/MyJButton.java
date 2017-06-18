package view.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * <code><b>MyJButton</b></code> 
 * extends JButton for show image
 * 
 * @author ������
 * 
 */
public class MyJButton extends JButton {

	private static final long serialVersionUID = 1L;

	public MyJButton(ImageIcon icon) {
		super(icon);
		setContentAreaFilled(false);// JButton����Ϊ͸��
		setBorderPainted(false); // ��ť�߽粻��ʾ
		setFocusPainted(false); // ͼ���ޱ߿�
	}
}
