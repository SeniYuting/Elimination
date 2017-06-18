package unitTest.controller.testRank;

import static org.junit.Assert.*;

import java.util.ArrayList;

import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.rank.RankPO;
import controller.rank.CoopRankController;

//------------------------�Ѳ���-----------------------------------

/*
 * ����controller.rank�����CoopRankController��
 */
public class TestCoopRankController {
	SocketClientService s;
	CoopRankController crc=new CoopRankController(s);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ArrayList<RankPO> ranklist=crc.getCoopRank();
		//System.out.println(ranklist.get(0).getID());
		//System.out.println(ranklist.get(0).getScore());
		//System.out.println(ranklist.get(0).getUserNames());
		
		//����һ����ע�͵���
//		assertEquals(ranklist.get(0).getID(),1);
		assertEquals(ranklist.get(0).getScore(),100);
		assertEquals(ranklist.get(0).getUserNames().get(0),"[qqq, eee, rrr, sss]");

		
	}

}
