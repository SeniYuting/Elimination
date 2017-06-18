package controller.props;

import localdata.LocalDataHelper;
import netservice.SocketClientService;
import po.user.UserPO;
import controller.user.LoginController;
import controller.user.UpdateController;
import controllerservice.props.PropsControllerService;
import controllerservice.user.LoginControllerService;
import controllerservice.user.UpdateControllerService;

//����isUp�������140
//����isPrompt�������50
//����isChain�������120
//����isBack�������80
//����isShark�������100


/**
 * <code><b>PropsController</b></code> deal with the update of coin
 * 
 * @author ����
 * 
 */
public class PropsController implements PropsControllerService {
	int isUp_Price = 140;
	int isPrompt_Price = 50;
	int isChain_Price = 120;
	int isBack_Price = 80;
	int isShark_Price = 100;
	int total_Price = 0;
	int current_coin = 0;
	UserPO userpo;
	SocketClientService s;
	LoginControllerService lc;
	UpdateControllerService uc;

	public PropsController(SocketClientService s) {
		this.s = s;
		lc = new LoginController(s);
		if(s==null){
//			System.out.println("s is null");
		}
		uc = new UpdateController(s);
	}

	// ����ģʽ�·����û���ʣ������
	public int getCoin(String userName) {
		userpo = lc.getUserPO(userName);
		return userpo.getCoin();
	}

	// ����ģʽ�¸��½����
	public int updateCoin(boolean isUp, boolean isPrompt, boolean isChain, boolean isBack, boolean isShark,
			String userName) {
		if (isUp) {
			total_Price = total_Price + isUp_Price;
		}
		if (isPrompt) {
			total_Price = total_Price + isPrompt_Price;
		}
		if (isChain) {
			total_Price = total_Price + isChain_Price;
		}
		if(isBack) {
			total_Price = total_Price + isBack_Price;
		}
		if(isShark) {
			total_Price = total_Price + isShark_Price;
		}

		userpo = lc.getUserPO(userName);
		current_coin = userpo.getCoin();
		if (current_coin < total_Price) { // ������ߵĽ��������
			return -1; // ����-1��ʾ���������
		} else {
			int new_coin = current_coin - total_Price;
			userpo.setCoin(new_coin);
			uc.updateNetCoins(userpo);
			return 1; // ����1��ʾ���������
		}

	}

	// �����������û���ʣ������(��userName����)
	public int getLocalCoin() {
		UserPO po = new UserPO("000", "000");
		LocalDataHelper ldh = new LocalDataHelper();
		UserPO po2 = ldh.getSingleValue(po);
		return po2.getCoin();
	}

	// ������ģʽ�¸��½����(��userName����)
	public int updateLocalCoin(boolean isUp, boolean isPrompt, boolean isChain, boolean isBack, boolean isShark) {
		int total_Price = 0;// �軨�ѵĽ��

		if (isUp) {
			total_Price = total_Price + isUp_Price;
		}
		if (isPrompt) {
			total_Price = total_Price + isPrompt_Price;
		}
		if (isChain) {
			total_Price = total_Price + isChain_Price;
		}
		if(isBack) {
			total_Price = total_Price + isBack_Price;
		}
		if(isShark) {
			total_Price = total_Price + isShark_Price;
		}
		UserPO po = new UserPO("000", "000");
		LocalDataHelper ldh = new LocalDataHelper();
		UserPO po2 = ldh.getSingleValue(po);

		int current_coins = po2.getCoin();// ���н��

		if (current_coins < total_Price) { // ������ߵĽ��������
			return -1; // ����-1��ʾ���������
		} else {
			int new_coin = current_coins - total_Price;
			po2.setCoin(new_coin);
			ldh.setSingleValue(po2);
			return 1; // ����1��ʾ���������
		}
	}
}
