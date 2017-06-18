package unitTest.model.testUser;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import model.user.DailyPlayModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.user.UserPO;



//------------------------�Ѳ���--------------------------------------

/*
* ����model.user�����DailyPlayModel��
*/

public class TestDailyPlayModel extends TestCase{

	DailyPlayModel dp=new DailyPlayModel();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsert() {
		DailyPlayModel dpm = new DailyPlayModel();
		ArrayList<Date> date = new ArrayList<Date>();
		date.add(Date.valueOf("2014-6-1"));
		HashMap<Date,Integer> dailyTotalGameNum = new HashMap<Date,Integer>();
		dailyTotalGameNum.put(date.get(0), 10);
		HashMap<Date,Integer> dailyAvgScore = new HashMap<Date,Integer>();
		dailyAvgScore.put(date.get(0), 1000);
		UserPO po = new UserPO("chaoren",dailyTotalGameNum,dailyAvgScore,date);
		//����¼�¼
		String s1 = dpm.insert(po);
		assertEquals(s1,"��ӳɹ���");
		
	}
	
	
	
	@Test
	public void testDelete() {
		DailyPlayModel dpm = new DailyPlayModel();
		
		//ɾ�����ڵļ�¼
		UserPO po1 = new UserPO("002");
		String s1 = dpm.delete(po1);
		assertEquals(s1,"ɾ���ɹ���");
		
		//ɾ�������ڵ��û���¼
		UserPO po2 = new UserPO("10086");
		String s2 = dpm.delete(po2);
		assertEquals(s2,"ɾ��ʧ�ܣ�");
	}
	
	
	
	@Test
	public void testFindPO() {
		//�;��������й�
		UserPO po = new UserPO("001");
		DailyPlayModel dpm = new DailyPlayModel();
		UserPO user = dpm.find(po);
		HashMap<Date,Integer> dailyAvgScore = user.getDailyAvgScore();
		HashMap<Date,Integer> dailyTotalGameNum = user.getDailyTotalGameNum();
		ArrayList<Date> date = user.getDate();
		int avg = dailyAvgScore.get(date.get(0));
		assertEquals(avg,2300);
		int gameNum = dailyTotalGameNum.get(date.get(0));
		assertEquals(gameNum,21);
		
		
			
	}
	
	

}
