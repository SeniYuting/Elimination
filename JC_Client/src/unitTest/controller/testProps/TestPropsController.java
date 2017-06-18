package unitTest.controller.testProps;

import static org.junit.Assert.assertEquals;
import net.SocketClient;
import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.props.PropsController;

//--------------------------------�Ѳ���------------------------------------

/*
 * ���԰�controller.props���PropsController��
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
	//ǰ�����û�001�н����200
	public void testGetCoin() {
		int coin=pc.getCoin("001");
		assertEquals(coin,200);
	}
	
	
	
	/*
	//ǰ�����û�001�н����200
	public void testUpdateCoin(){
		int coin1=pc.updateCoin(true, true, true, "001"); //�ܻ���300�����������
		int coin2=pc.updateCoin(true, true, false, "001");//�ܻ���200.�������,�ٽ�ʣ������Ϊ0
		int coin3=pc.updateCoin(true, false, false, "001");//�ܻ���50���������������ʣ��
		assertEquals(coin1,-1);
		assertEquals(coin2,1);
		assertEquals(coin3,1);	
	}
	*/
	
	/*
	//ǰ���Ǳ����û��н����200
	@Test
	public void testGetLocalCoin() {
		int coin=pc.getLocalCoin();
		assertEquals(coin,200);
	}
	*/
	
	/*
	//ǰ���Ǳ����û��н����200
	@Test
	public void testUpdateLocalCoin(){
		int coin1=pc.updateLocalCoin(true, true, true); //�ܻ���300�����������
		int coin2=pc.updateLocalCoin(true, true, false);//�ܻ���200.�������,�ٽ�ʣ������Ϊ0
		//int coin3=pc.updateLocalCoin(true, false, false);//�ܻ���50���������������ʣ��
		assertEquals(coin1,-1);
		assertEquals(coin2,1);
		//assertEquals(coin3,1);	
	}
	*/
	

}
