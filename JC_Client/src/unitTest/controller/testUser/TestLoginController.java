package unitTest.controller.testUser;

import static org.junit.Assert.assertEquals;
import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.user.UserPO;
import controller.user.LoginController;

//------------------------------已测完---------------------------------

/*
 * 测试user包的LoginController类
 */
public class TestLoginController {
	
	LoginController lc;

	SocketClientService lgs;
	
	

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetUserPO() {
		lc=new LoginController(lgs);
		UserPO up =lc.getUserPO("001");
		//System.out.println(up.getUserName());
		//System.out.println(up.getAvgScore());
		//System.out.println(up.getCoin());
		//System.out.println(up.getGameNum());
		//System.out.println(up.getMaxCombo());
		//System.out.println(up.getMaxScore());
		//System.out.println(up.getPassword());
		//System.out.println(up.getFriend());
		
		assertEquals(up.getUserName(),"001");
		assertEquals(up.getAvgScore(),790);
		assertEquals(up.getCoin(),220);
		assertEquals(up.getGameNum(),1003);
		assertEquals(up.getMaxCombo(),5);
		assertEquals(up.getMaxScore(),2000);
		assertEquals(up.getPassword(),"001");
		assertEquals(up.getFriend().get(0),"qq");
		assertEquals(up.getFriend().get(1),"aa");
	
	}
	
	
	
	
	/*
	@Test
	public void testLogin() {
		
		lc=new LoginController(lgs);
		String loginInfo2 =lc.login("010", "12345");
		String loginInfo1 =lc.login("001", "001");
		String loginInfo3 =lc.login("", "123");
		String loginInfo4 =lc.login("001", "12345");
		assertEquals(loginInfo2,"用户名不存在！");
		assertEquals(loginInfo1,"登录成功！");		
		assertEquals(loginInfo3,"用户名不存在！");
		assertEquals(loginInfo4,"密码错误！");
		
	}
	*/
	

}
