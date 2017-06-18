package view.component;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import netservice.SocketClientService;
import view.user.LoginFrame;

/**
 * <code><b>PanelController</b></code> for changing panels
 * 
 * @author ������
 * 
 */
public class PanelController {
	public static LoginFrame mainFrame;
	static JButton btnMin; // ��С����ť
	static JButton btnClose; // �رհ�ť
	// �ο�ģʽ 0 ����ģʽ 1 Э��ģʽ 2 ��սģʽ 3
	static String userName;
	public static SocketClientService s;

	public PanelController(JFrame mainFrame, String userName, int mode,
			int musicVolume, int soundVolume, boolean isDown,
			SocketClientService ss) {
		s = ss;
		PanelController.userName = userName;
		PanelController.mainFrame = (LoginFrame) mainFrame;
	}

	public static void moveToStage(JPanel panel) {

		mainFrame.getMenuPanel().removeAll();
		mainFrame.add(panel, 0);

		btnMin = JButtonUtil.getBtnMin();
		btnClose = JButtonUtil.getBtnClose();
		btnClose.setActionCommand("exit");
		btnMin = JButtonUtil.getBtnMin();
		panel.add(btnClose, 0);
		btnClose.setBounds(976, -2, 39, 20);
		panel.add(btnMin, 0);
		btnMin.setBounds(948, -2, 28, 20);

		btnMin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainFrame.setExtendedState(JFrame.ICONIFIED);
			}
		});

		btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("exit")) {
					if (JOptionPane.showConfirmDialog(null, "ȷ���˳���", "ϵͳ��Ϣ",
							JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						if(!(s==null)&&!(userName==null)){
							s.logOut(userName);
						}
						((Window) btnClose.getTopLevelAncestor()).dispose();
						System.exit(0);
					}
				} else if (e.getActionCommand().equals("dispose")) {
					mainFrame.setVisible(false);
					mainFrame.dispose();
				}
			}
		});

		mainFrame.repaint();
	}

	public static void moveToStage(JPanel panel, int mode) {

		mainFrame.add(panel, 0);

		btnMin = JButtonUtil.getBtnMin();
		btnClose = JButtonUtil.getBtnClose();
		btnClose.setActionCommand("exit");
		btnMin = JButtonUtil.getBtnMin();
		panel.add(btnClose, 0);
		btnClose.setBounds(976, -2, 39, 20);
		panel.add(btnMin, 0);
		btnMin.setBounds(948, -2, 28, 20);

		btnMin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainFrame.setExtendedState(JFrame.ICONIFIED);
			}
		});

		btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("exit")) {
					if (JOptionPane.showConfirmDialog(null, "ȷ���˳���", "ϵͳ��Ϣ",
							JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
						if(!(s==null)&&!(userName==null)){
							s.logOut(userName);
						}
						((Window) btnClose.getTopLevelAncestor()).dispose();
						System.exit(0);
					}
				} else if (e.getActionCommand().equals("dispose")) {
					mainFrame.setVisible(false);
					mainFrame.dispose();
				}
			}
		});

		mainFrame.repaint();
	}

	public static void moveToStage(JPanel panel, JPanel oldPanel, int mode) {
		mainFrame.remove(oldPanel);
		moveToStage(panel, mode);
	}

	public static void returnToMain(JPanel panel) {
		mainFrame.remove(panel);
		mainFrame.repaint();
	}
	
}
