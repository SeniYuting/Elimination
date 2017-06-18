package po.rank;

import java.io.Serializable;

public class NetSingleRankPO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userName;

	private int score;

	public NetSingleRankPO(String userName, int score) {
		this.userName = userName;
		this.score = score;
	}

	public String getUserName() {
		return userName;
	}

	public int getScore() {
		return score;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(int score) {
		this.score = score;
	}
}
