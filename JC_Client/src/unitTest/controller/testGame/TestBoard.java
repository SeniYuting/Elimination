package unitTest.controller.testGame;

import org.junit.After;
import org.junit.Before;

import controller.game.Board;

/*
 * 测试 controller.game包里的Board类
 */
public class TestBoard {

	Board board=new Board();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/* 
	@Test
	public void testSwapTile() {
		Tile tile1=new Tile(1,2,Color.picture1);
		Tile tile2=new Tile(3,4,Color.picture1);
		board.swapTile(tile1, tile2);
		assertEquals(tile1.getRow(),3);
		assertEquals(tile1.getCol(),4);
		assertEquals(tile2.getRow(),1);
		assertEquals(tile2.getCol(),2);
	
	}
	*/
	
	

}
