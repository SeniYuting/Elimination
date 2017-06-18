package view.help;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import localdata.LocalDataHelper;
import po.user.UserPO;
import controller.props.PropsController;
import controllerservice.props.PropsControllerService;
import view.component.MyJButton;
import view.component.PanelController;
import view.props.ChoosePropsPanel;
/**
 * <code><b>HelpPanel</b></code> contains components about game help
 * 
 * @author ≤‹”ÍÊ√
 * 
 */
public class HelpPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ImageIcon bacgrd;
	JLabel imLabel;

	int mode;
	String userName;

	public HelpPanel(String userName, int mode) {
		this.userName = userName;
		this.mode = mode;

		setLayout(null);
		setBounds(0, 0, 1016, 678);

		bacgrd = new ImageIcon("img/Background1.png");
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
		// ∑µªÿ∞¥≈•
		ImageIcon returnIcon = new ImageIcon("img/ReturnBack.png");
		MyJButton returnButton = new MyJButton(returnIcon);
		returnButton.setBounds(5, 10, returnIcon.getIconWidth(),
				returnIcon.getIconHeight());
		add(returnButton, 0);
		returnButton.addMouseListener(new returnButtonListener());
	}

	class returnButtonListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (mode == 0) {
				LocalDataHelper ldh = new LocalDataHelper();
				UserPO po = ldh.getFullUserPO();
				int coin = po.getCoin();

				ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(
						userName, mode, PanelController.s);
				choosePropsPanel.setCoinLabel(coin);
				PanelController.moveToStage(choosePropsPanel, HelpPanel.this,
						mode);
			} else {
				PropsControllerService propsService = new PropsController(
						PanelController.s);
				int coin = propsService.getCoin(userName);

				ChoosePropsPanel choosePropsPanel = new ChoosePropsPanel(
						userName, mode, PanelController.s);
				choosePropsPanel.setCoinLabel(coin);
				PanelController.moveToStage(choosePropsPanel, HelpPanel.this,
						mode);
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
