package view.component;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import view.systemset.SystemSetPanel;

public class SetListener extends MouseAdapter {

	public void mouseClicked(MouseEvent e) {
		final JFrame frame = new JFrame();
		frame.setTitle("天天爱消除/系统设置");
		frame.setBounds(350, 200, 605, 260);
		
		Image taskbar = Toolkit.getDefaultToolkit().getImage(
				"img/start/taskbar.jpg");
		frame.setIconImage(taskbar);

		String result = readFile("SystemSet");
		int mv = Integer.parseInt(result.split("%")[0]);
		int sv = Integer.parseInt(result.split("%")[1]);
		String isDownStr = result.split("%")[2];
		boolean isDown = isDownStr.equals("true") ? true : false;
		
		SystemSetPanel systemSetPanel = new SystemSetPanel(mv, sv, isDown);

		frame.add(systemSetPanel);
		frame.setVisible(true);
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
}
