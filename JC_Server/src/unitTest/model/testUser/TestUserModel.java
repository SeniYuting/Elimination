package unitTest.model.testUser;

import junit.framework.TestCase;
import model.user.UserModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.user.UserPO;

//----------------�����-----------------

/*
 * ����model.user�����UserModel��
 */
public class TestUserModel extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testInsert() {
		UserModel um=new UserModel();
		//����һ���µļ�¼
		UserPO po1=new UserPO("200","123",300,5,200,0,0);
		String s1=um.insert(po1);
		assertEquals(s1,"��ӳɹ���");
		
		//����һ���û����Ѿ����ڵļ�¼
		UserPO po2=new UserPO("001","222",300,0,0,0,0);
		String s2=um.insert(po2);
		assertEquals(s2,"���ʧ�ܣ�");
	}
	
	
	/*
	@Test
	public void testDelete() {
		UserModel um=new UserModel();
		//ɾ��һ�����м�¼
		UserPO po1=new UserPO("200","123",300,5,200,0,0);
		String s1=um.delete(po1);
		assertEquals(s1,"ɾ���ɹ���");
		
		//ɾ��һ�������ڵļ�¼
		UserPO po2=new UserPO("222","222",300,0,0,0,0);
		String s2=um.delete(po2);
		assertEquals(s2,"ɾ��ʧ�ܣ�");
	}
	*/
	
	/*
	@Test
	public void testFindPO() {
		UserModel um=new UserModel();
		
		//��UserPO("001","001")��ȡuser��������Ϣ
		UserPO po1=new UserPO("001","001");
		UserPO po2=um.find(po1);
		assertEquals(po2.getUserName(),"001");
		assertEquals(po2.getPassword(),"001");
		assertEquals(po2.getCoin(),300);
		assertEquals(po2.getMaxCombo(),5);
		assertEquals(po2.getMaxScore(),200);
		assertEquals(po2.getGameNum(),1002);
		assertEquals(po2.getAvgScore(),789);
				
	}
	*/
	
	
	/*
	@Test
	public void testFindInt() {
		UserModel um=new UserModel();
		String userName1="001";
		String userName2="300";
		
		int result1=um.find(userName1);
		assertEquals(result1,1);
		
		int result2=um.find(userName2);
		assertEquals(result2,0);
			
	}
	*/
	
	
	
	

}
