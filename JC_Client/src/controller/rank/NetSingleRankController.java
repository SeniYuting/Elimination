package controller.rank;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.SocketClient;
import netservice.SocketClientService;
import po.message.Message;
import po.rank.NetSingleRankPO;
import controllerservice.rank.NetSingleRankControllerService;

public class NetSingleRankController implements NetSingleRankControllerService{
	//返回排行榜List----姓名、得分
	public ArrayList<NetSingleRankPO> getRankList(){
		SocketClientService scs;
		Message m = new Message();
		try {
			scs = new SocketClient();
			m = scs.getRank();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<NetSingleRankPO> ranklist = m.getNetRank();
		return ranklist;		
	}
	
}
