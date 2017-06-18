package controller.game;

import java.util.ArrayList;

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
		@SuppressWarnings("unused")
		int score = 0;
		if (tempsize == 3) {
			score += 100;
		} else if (tempsize == 4) {
			// 如果是四个直线的棋子，则生成道具：blink 效果： 爆炸一圈
			score += 200;
			t.settype(Type.blink);
			t.setWillDelete(false);

		} else if (tempsize == 5) {
			score += 500;
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
			// 这种情况一定是道具bomb，爆炸猪
			// Tile t =achain.get(index);
			score += 700;
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
