package unitTest.controller.testUser;

import static org.junit.Assert.assertEquals;
import net.SocketClient;
import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.user.RegisterController;



//--------------------�Ѳ���---------------------------

/*
 * ����user����RegisterController��
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

		assertEquals(s1,"ע����û����Ѵ��ڣ�");
		//assertEquals(s2,"ע����û����Ѵ��ڣ�");
		//assertEquals(s4,"ע��ɹ���");
		//assertEquals(s6,"ע��ɹ���");
		
	}

}
