package controller.props;

import localdata.LocalDataHelper;
import netservice.SocketClientService;
import po.user.UserPO;
import controller.user.LoginController;
import controller.user.UpdateController;
import controllerservice.props.PropsControllerService;
import controllerservice.user.LoginControllerService;
import controllerservice.user.UpdateControllerService;

//道具isUp金币数：140
//道具isPrompt金币数：50
//道具isChain金币数：120
//道具isBack金币数：80
//道具isShark金币数：100


/**
 * <code><b>PropsController</b></code> deal with the update of coin
 * 
 * @author 曹燕
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

	// 联网模式下返回用户的剩余金币数
	public int getCoin(String userName) {
		userpo = lc.getUserPO(userName);
		return userpo.getCoin();
	}

	// 联网模式下更新金币数
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
		if (current_coin < total_Price) { // 购买道具的金币数不够
			return -1; // 返回-1表示金币数不够
		} else {
			int new_coin = current_coin - total_Price;
			userpo.setCoin(new_coin);
			uc.updateNetCoins(userpo);
			return 1; // 返回1表示金币数够用
		}

	}

	// 不联网返回用户的剩余金币数(无userName参数)
	public int getLocalCoin() {
		UserPO po = new UserPO("000", "000");
		LocalDataHelper ldh = new LocalDataHelper();
		UserPO po2 = ldh.getSingleValue(po);
		return po2.getCoin();
	}

	// 不联网模式下更新金币数(无userName参数)
	public int updateLocalCoin(boolean isUp, boolean isPrompt, boolean isChain, boolean isBack, boolean isShark) {
		int total_Price = 0;// 需花费的金币

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

		int current_coins = po2.getCoin();// 现有金币

		if (current_coins < total_Price) { // 购买道具的金币数不够
			return -1; // 返回-1表示金币数不够
		} else {
			int new_coin = current_coins - total_Price;
			po2.setCoin(new_coin);
			ldh.setSingleValue(po2);
			return 1; // 返回1表示金币数够用
		}
	}
}
