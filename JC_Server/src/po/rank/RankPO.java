package po.rank;

import java.io.Serializable;
import java.util.ArrayList;

public class RankPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int score;  //协作得分
	private ArrayList<String> userNames; //小组成员姓名
	
	public RankPO(int score,ArrayList<String> userNames){
		this.score = score;
		this.userNames = userNames;
	}
	
	public RankPO(){
		
	}
	public int getScore(){
		return this.score;
	}
	
	public ArrayList<String> getUserNames(){
		return this.userNames;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void setUserNames(ArrayList<String> userNames){
		this.userNames = userNames;
	}
}
