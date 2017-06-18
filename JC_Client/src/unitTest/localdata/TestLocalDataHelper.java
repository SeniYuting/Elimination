package unitTest.localdata;

import static org.junit.Assert.assertEquals;
import localdata.LocalDataHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import po.user.UserPO;

//---------------------------不会写testGetDailyGame-----------------------------------------

/*
 * 测试localdata包下的LocalDataHelper类
 */
public class TestLocalDataHelper {

	LocalDataHelper ldh=new LocalDataHelper();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetSingleValue() {
		UserPO up=new UserPO("000","000");
		UserPO new_up=ldh.getSingleValue(up);
		
		assertEquals(new_up.getGameNum(),430);
		assertEquals(new_up.getAvgScore(),521);
		assertEquals(new_up.getMaxCombo(),200);
		assertEquals(new_up.getMaxScore(),4700);
		assertEquals(new_up.getCoin(),264);	
	}
	
	
	
	
	@Test
	public void testGetRecentScore() {
		UserPO up=new UserPO("000","000");
		UserPO new_up=ldh.getRecentScore(up);
		
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(0).toString()),1920);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(1).toString()),1400);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(2).toString()),3500);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(3).toString()),100);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(4).toString()),100);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(5).toString()),0);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(6).toString()),0);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(7).toString()),1000);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(8).toString()),800);
		assertEquals(Integer.parseInt(new_up.getRecentGameScores().get(9).toString()),0);
		
	}
	
	
	@Test
	public void testGetDailyGame() {
		//UserPO up=new UserPO("000","000");
		//UserPO new_up=ldh.getDailyGame(up);
	}
	
	
	
	

}
