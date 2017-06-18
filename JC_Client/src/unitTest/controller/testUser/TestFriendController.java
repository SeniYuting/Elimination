package unitTest.controller.testUser;

//----------------------------�Ѳ���--------------------------------

import static org.junit.Assert.*;

import java.util.ArrayList;

import netservice.SocketClientService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.user.FriendController;

/*
 * ����controller.user����FriendController��
 */
public class TestFriendController {

	SocketClientService s;
	FriendController fc=new FriendController(s);
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOnlineFriends() {
		ArrayList<String> onlineFriends=fc.getOnlineFriends("001");
		//System.out.println(onlineFriends);
		assertEquals(onlineFriends.get(0),null);
	}

}
