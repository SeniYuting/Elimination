package controller.rank;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.SocketClient;
import netservice.SocketClientService;
import po.message.Message;
import po.rank.RankPO;
import controllerservice.rank.CoopRankControllerService;

public class CoopRankController implements CoopRankControllerService {

	SocketClientService s;

	public CoopRankController(SocketClientService s) {
		this.s = s;
	}

	@Override
	public ArrayList<RankPO> getCoopRank() {
		// TODO Auto-generated method stub
		Message m = new Message();
		try {
			s = new SocketClient();
			m = s.getCoopRank();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<RankPO> ranklist = m.getCoopRank();
		return ranklist;
	}

	@Override
	public void setCoopRank(RankPO po) {
		// TODO Auto-generated method stub
		ArrayList<RankPO> rank = new ArrayList<RankPO>();
		try {
			s = new SocketClient();
			rank.add(po);
			s.setCoopRank(rank);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
