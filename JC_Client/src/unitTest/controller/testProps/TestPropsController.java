package unitTest.controller.testProps;

import static org.junit.Assert.assertEquals;
import net.SocketClient;
import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.props.PropsController;

//--------------------------------已测完------------------------------------

/*
 * 测试包controller.props里的PropsController类
 */

public class TestPropsController {

	PropsController pc;
	
	@Before
	public void setUp() throws Exception {
		SocketClientService s =new SocketClient();
		pc=new PropsController(s);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	//前提是用户001有金币数200
	public void testGetCoin() {
		int coin=pc.getCoin("001");
		assertEquals(coin,200);
	}
	
	
	
	/*
	//前提是用户001有金币数200
	public void testUpdateCoin(){
		int coin1=pc.updateCoin(true, true, true, "001"); //总花费300，金币数不够
		int coin2=pc.updateCoin(true, true, false, "001");//总花费200.金币数够,临界剩余金币数为0
		int coin3=pc.updateCoin(true, false, false, "001");//总花费50，金币数够，且有剩余
		assertEquals(coin1,-1);
		assertEquals(coin2,1);
		assertEquals(coin3,1);	
	}
	*/
	
	/*
	//前提是本地用户有金币数200
	@Test
	public void testGetLocalCoin() {
		int coin=pc.getLocalCoin();
		assertEquals(coin,200);
	}
	*/
	
	/*
	//前提是本地用户有金币数200
	@Test
	public void testUpdateLocalCoin(){
		int coin1=pc.updateLocalCoin(true, true, true); //总花费300，金币数不够
		int coin2=pc.updateLocalCoin(true, true, false);//总花费200.金币数够,临界剩余金币数为0
		//int coin3=pc.updateLocalCoin(true, false, false);//总花费50，金币数够，且有剩余
		assertEquals(coin1,-1);
		assertEquals(coin2,1);
		//assertEquals(coin3,1);	
	}
	*/
	

}
