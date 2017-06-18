package unitTest.controller.testGame;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.game.Color;
import controller.game.Tile;

//---------已完成----------

/*
 * 测试controller.game包里的Tile类
 */
public class TestTile {

	Tile tile=new Tile(2, 2, Color.picture1);
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsNeighbor() {
		Tile other_tile1=new Tile(1,2,Color.picture1);
		Tile other_tile2=new Tile(2,1,Color.picture1);
		Tile other_tile3=new Tile(1,1,Color.picture1);
		Tile other_tile4=new Tile(2,2,Color.picture1);
		assertEquals(tile.isNeighbor(other_tile1),true);
		assertEquals(tile.isNeighbor(other_tile2),true);
		assertEquals(tile.isNeighbor(other_tile3),false);
		assertEquals(tile.isNeighbor(other_tile4),false);
	}

}
