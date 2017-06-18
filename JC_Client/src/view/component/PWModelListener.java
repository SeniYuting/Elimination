package view.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import netservice.SocketClientService;
import controller.user.UpdateController;
import controllerservice.user.UpdateControllerService;

public class PWModelListener extends MouseAdapter {
	
	String userName;
	SocketClientService s;
	
	public PWModelListener(String userName, SocketClientService s){
		this.userName = userName;
		this.s = s;
	}

	public void mouseClicked(MouseEvent e) {

		final JFrame frame = new JFrame();
		frame.setTitle("天天爱消除/修改密码");
		frame.setBounds(450, 200, 362, 250);

		final Color color1 = new Color(225, 237, 233);
		final Color color2 = new Color(76, 124, 206);

		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(new GradientPaint(0, 0, color1, this
						.getWidth(), this.getHeight(), color2));
				g2d.fill(new Rectangle(0, 0, this.getWidth(), this
						.getHeight()));
			}
		};

		panel.setLayout(null);

		Image taskbar = Toolkit.getDefaultToolkit().getImage(
				"img/start/taskbar.jpg");
		frame.setIconImage(taskbar);

		JLabel jl1 = new JLabel("新密码");
		jl1.setBounds(50, 45, 100, 30);
		jl1.setFont(new Font("楷体", Font.PLAIN, 20));
		panel.add(jl1);

		final JPasswordField jpw1 = new JPasswordField();
		jpw1.setBounds(150, 45, 150, 30);
		panel.add(jpw1);

		JLabel jl2 = new JLabel("再次确认");
		jl2.setBounds(40, 100, 150, 30);
		jl2.setFont(new Font("楷体", Font.PLAIN, 20));
		panel.add(jl2);

		final JPasswordField jpw2 = new JPasswordField();
		jpw2.setBounds(150, 100, 150, 30);
		panel.add(jpw2);

		RButton jb1 = new RButton("确定");
		jb1.setBounds(130, 150, 100, 30);
		jb1.setFont(new Font("楷体", Font.PLAIN, 20));
		panel.add(jb1);

		final JLabel jdjl = new JLabel();
		jdjl.setHorizontalAlignment(SwingConstants.CENTER);
		jdjl.setBounds(30, 20, 70, 35);
		jdjl.setFont(new Font("楷体", Font.PLAIN, 18));

		jb1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String pw1 = new String(jpw1.getPassword());
				String pw2 = new String(jpw2.getPassword());
				if (pw1.length() == 0) {
					jdjl.setText("请输入新密码");
				} else if (pw2.length() == 0) {
					jdjl.setText("请再次输入确认");
				} else if (!pw1.equals(pw2)) {
					jdjl.setText("两次输入密码不匹配");
					jpw1.setText("");
					jpw2.setText("");
				} else if (pw1.length() < 3 || pw2.length() < 3) {
					jdjl.setText("密码强度太弱，请重新设置密码");
					jpw1.setText("");
					jpw2.setText("");
				} else if (pw1.length() > 15 || pw2.length() > 15) {
					jdjl.setText("密码强度太长，请重新设置密码");
					jpw1.setText("");
					jpw2.setText("");
				} else {
					UpdateControllerService ucs = new UpdateController(s);
					ucs.changePassword(userName, pw1);
					jdjl.setText("修改密码成功！");
					deleteRecord(userName, "file/account.txt");
					deleteRecord(userName, "file/account2.txt");
					frame.setVisible(false);
				}
				JOptionPane.showMessageDialog(null, jdjl, "系统信息",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}

	public void deleteRecord(String userName, String fileName) {
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);

			String record = null;
			ArrayList<String> list = new ArrayList<String>();
			while ((record = bufr.readLine()) != null) {
				if (!record.split("%")[0].equals(userName)) {
					list.add(record);
				}
			}
			bufr.close();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bufw = new BufferedWriter(fw);
			for (int i = 0; i < list.size(); i++) {
				bufw.write(list.get(i));
				bufw.newLine();
				bufw.flush();
			}
			bufw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
