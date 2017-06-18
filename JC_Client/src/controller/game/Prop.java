package controller.game;

import java.util.ArrayList;
/**
 * <code><b>Prop</b></code> 负责根据爆炸的棋子链，生成道具。
 * 
 * @author 
 */

public class Prop {
	
	public static void getProps(ArrayList<Tile> achain){
		for(Tile t : achain){
			t.setWillDelete(true);
		}
		int tempsize = achain.size();
		double x = Math.random() * tempsize;
		int index = (int) x;
		Tile t = null;
		if (tempsize != 0) {
			t = achain.get(index);
		}
		if (tempsize == 3) {
		} else if (tempsize == 4) {
			t.settype(Type.blink);
			t.setWillDelete(false);

		} else if (tempsize == 5) {
			if (isStraight(achain)) {
				// 五个直线 
				t.settype(Type.bomb);
				t.setWillDelete(false);
			} else {
				// 五个的不是直线（T或者L形）
				t.settype(Type.blink);
				t.setWillDelete(false);
			}

		} else if (tempsize >= 6) {
			t.settype(Type.bomb);
			t.setWillDelete(false);
		}
	}

	private static boolean isStraight(ArrayList<Tile> list) {
		int size = list.size();
		boolean result = true;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				Tile t1 = list.get(i);
				Tile t2 = list.get(j);

				if ((t1.getRow() != t2.getRow())
						&& (t1.getCol() != t2.getCol())) {
					result = false;
					return result;
				}
			}
		}

		return result;
	}
	
	
}
