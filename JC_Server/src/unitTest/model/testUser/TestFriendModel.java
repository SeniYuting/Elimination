package unitTest.model.testUser;


import java.util.ArrayList;

import junit.framework.TestCase;
import model.user.FriendModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.user.UserPO;

//---------�����--------------

/*
* ����model.user�����FriendModel��
*/
public class TestFriendModel extends TestCase{
	
	FriendModel fm=new FriendModel();
	ArrayList<String> friendList =new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	@Test
	public void testInsert() {
		//����һ���µļ�¼
		friendList.add("aaa");
		friendList.add("bbb");
		friendList.add("ccc");
	
		UserPO po1=new UserPO(friendList,"002");
		String s1=fm.insert(po1);
		assertEquals(s1,"��ӳɹ���");					
	}
	*/
	
	/*
	@Test
	public void testDelete(){
		//ɾ�����м�¼
		UserPO po1=new UserPO("002");
		String s1=fm.delete(po1);
		assertEquals(s1,"ɾ���ɹ���");
		
		//ɾ�������ڵ��û���
		UserPO po2=new UserPO("200");
		String s2=fm.delete(po2);
		assertEquals(s2,"ɾ��ʧ�ܣ�");
		
	}
	*/
	
	@Test
	public void testFind() {
		UserPO po1=new UserPO("001");
		UserPO new_po=fm.find(po1);
		assertEquals(new_po.getUserName(),"001");
		assertEquals(new_po.getFriend().get(0),"qq");
		assertEquals(new_po.getFriend().get(1),"aa");
	}
	

}
