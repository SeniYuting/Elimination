package unitTest.controller.testRank;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.rank.NetSingleRankPO;
import controller.rank.NetSingleRankController;

//--------已完成----------

/*
 * 测试rank包的NetSingleRankController类
 */
public class TestNetSingleRankController {

	NetSingleRankController n=new NetSingleRankController();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRankList() {
		ArrayList<NetSingleRankPO> rankList=n.getRankList();
		assertEquals(rankList.get(0).getUserName(),"007");
		assertEquals(rankList.get(0).getScore(),1000);

	}

}
