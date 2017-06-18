//package unitTest.model.testRank;
//
//
//import java.util.ArrayList;
//
//import junit.framework.TestCase;
//import model.rank.RankModel;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import po.rank.RankPO;
//
////--------------------------------�Ѳ���---------------------------------------
//
///*
// * ����model.rank�����RankModel��
// */
//public class TestRankModel extends TestCase{
//
//	RankModel rm = new RankModel();
//	ArrayList<String> nameList = new ArrayList<String>();
//	
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	
//	@Test
//	public void testInsert() {
//		nameList.add("001");
//		nameList.add("002");
//		nameList.add("003");
//		RankPO po = new RankPO(2,100,nameList);
//		String s=rm.insert(po);
//		assertEquals(s,"��ӳɹ���");
//	}
//	
//	
//	
//	@Test
//	public void testDelete(){
//		
//		//ɾ�����м�¼
//		RankPO po1 = new RankPO(2);
//		String s1=rm.delete(po1);
//		assertEquals(s1,"ɾ���ɹ���");
//				
//		//ɾ�������ڵ��û���
//		RankPO po2 = new RankPO(5);
//		String s2=rm.delete(po2);
//		assertEquals(s2,"ɾ��ʧ�ܣ�");
//	}
//	
//	
//	@Test
//	public void testFind() {
//		RankPO po1=new RankPO(2);
//		RankPO new_po=rm.find(po1);
//		//System.out.println(new_po.getID());
//		//System.out.println(new_po.getScore());
//		//System.out.println(new_po.getUserNames().get(1));
//		//System.out.println(new_po.getUserNames().get(2));
//		//System.out.println(new_po.getUserNames().get(3));
//		
//		assertEquals(new_po.getID(),2);
//		assertEquals(new_po.getScore(),100);
//		assertEquals(new_po.getUserNames().get(1),"001");
//		assertEquals(new_po.getUserNames().get(2),"002");
//		assertEquals(new_po.getUserNames().get(3),"003");
//	}
//	
//	
//
//}
