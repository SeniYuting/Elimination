package unitTest.model.testUser;

import java.util.ArrayList;

import junit.framework.TestCase;
import model.user.RecentScoreModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.user.UserPO;

//---------已完成--------------

/*
 * 测试model.user包里的RecentScoreModel类
 */
public class TestRecentScoreModel extends TestCase{
	
	RecentScoreModel rs=new RecentScoreModel();
	ArrayList<Integer> recentScore=new ArrayList<Integer>();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	@Test
	public void testInsert() {
		//插入一条新的记录
		recentScore.add(100);
		recentScore.add(200);
		recentScore.add(321);
		recentScore.add(256);
		recentScore.add(456);
		recentScore.add(456);
		recentScore.add(456);
		recentScore.add(456);
		recentScore.add(456);
		recentScore.add(456);
		UserPO po1=new UserPO("005",recentScore);
		String s1=rs.insert(po1);
		assertEquals(s1,"添加成功！");
				
		//插入一条用户名已经存在的记录
		//UserPO po2=new UserPO("001",recentScore);
		//String s2=rs.insert(po2);
		//assertEquals(s2,"添加失败！");	
	}
	*/
	
	/*
	@Test
	public void testDelete(){
		//删除已有记录
		UserPO po1=new UserPO("005");
		String s1=rs.delete(po1);
		assertEquals(s1,"删除成功！");
		
		//删除不存在的用户名
		UserPO po2=new UserPO("200");
		String s2=rs.delete(po2);
		assertEquals(s2,"删除失败！");
		
	}
	*/
	
	@Test
	public void testFindPO() {
		UserPO po1=new UserPO("001");
		UserPO new_po=rs.find(po1);
		assertEquals(new_po.getUserName(),"001");
		//System.out.println(new_po.getRecentGameScores().get(1));
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(0).toString()),120);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(1).toString()),500);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(2).toString()),230);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(3).toString()),333);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(4).toString()),433);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(5).toString()),230);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(6).toString()),1000);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(7).toString()),123);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(8).toString()),900);
		assertEquals(Integer.parseInt(new_po.getRecentGameScores().get(9).toString()),200);
	}
	
	

}
