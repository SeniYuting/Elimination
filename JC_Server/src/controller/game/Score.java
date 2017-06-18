package controller.game;

import java.util.ArrayList;

public class Score {
	int score;
	ArrayList<ArrayList<Tile>> chains;

	public Score(ArrayList<ArrayList<Tile>> chains) {
		this.score = 0;
		this.chains = chains;
	}

	public int getScore(boolean isSuperMode) {
		chains = BombRule.combineChains(chains);
		for (ArrayList<Tile> achain : chains) {
			int size = achain.size();
			switch (size) {
			case 3:
				score += 100;
				break;
			case 4:
				score += 200;
				break;
			case 5:
				score += 500;
				break;
			}
			for (Tile t : achain) {
				if (t.isWillDelete()) {
					if (t.getType() == Type.blink) {
						score += 800;
					}
					if (t.getType() == Type.bomb) {
						score += 1700;
					}
				}
			}

		}
		if(isSuperMode){
			score *= 2;
		}
		return score;
	}

}
