package unitTest.controller.testUser;

import static org.junit.Assert.assertEquals;
import net.SocketClient;
import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.user.RegisterController;



//--------------------已测完---------------------------

/*
 * 测试user包的RegisterController类
 */
public class TestRegisterController {

	RegisterController rc;
	
	@Before
	public void setUp() throws Exception {
		SocketClientService s =new SocketClient();
		rc=new RegisterController(s);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegister() {
		String s1 = rc.register("001", "123456");
	//	String s2 = rc.register("001", "001");
	//	String s3 = rc.register("", "123");  //***
	//	String s4 = rc.register("088", "");		
	//	String s5 = rc.register("", "");	//***	
	//	String s6 = rc.register("099", "099");
		
	//	System.out.println(s1);
	//	System.out.println(s2);
	//	System.out.println(s3);   //***
	//	System.out.println(s4);
	//	System.out.println(s5);
	//	System.out.println(s6);

		assertEquals(s1,"注册的用户名已存在！");
		//assertEquals(s2,"注册的用户名已存在！");
		//assertEquals(s4,"注册成功！");
		//assertEquals(s6,"注册成功！");
		
	}

}
