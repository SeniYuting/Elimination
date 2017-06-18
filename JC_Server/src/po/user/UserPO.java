package po.user;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class UserPO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private int coin;
	private int gameNum;
	private int avgScore;
	private HashMap<Date,Integer> dailyTotalGameNum;
	private HashMap<Date,Integer> dailyAvgScore;
	private ArrayList<Integer> recentGameScores;
	private ArrayList<Date> date;
	private int maxCombo;
	private int maxScore;
	private ArrayList<String> friend;
	private ArrayList<String> friendRequest;
	
	public UserPO(){
		
	}

	public UserPO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public UserPO(String userName){
		this.userName = userName;
	}
	
	public UserPO(String userName,String password,int coin,int maxCombo,int maxScore,int gameNum,int avgScore){
		this.userName = userName;
		this.password = password;
		this.coin = coin;
		this.maxCombo = maxCombo;
		this.maxScore = maxScore;
		this.gameNum = gameNum;
		this.avgScore = avgScore;
	}
	
	public UserPO(String userName,HashMap<Date,Integer> dailyTotalGameNum,HashMap<Date,Integer> dailyAvgScore,ArrayList<Date> date){
		this.userName = userName;
		this.dailyTotalGameNum = dailyTotalGameNum;
		this.dailyAvgScore = dailyAvgScore;
		this.date = date;
	}
	
	public UserPO(String userName,ArrayList<Integer> recentScore){
		this.userName = userName;
		this.recentGameScores = recentScore;
	}
	
	public UserPO(ArrayList<String> friend,String userName){
		this.userName = userName;
		this.friend = friend;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public int getCoin(){
		return coin;
	}
	
	public HashMap<Date,Integer> getDailyAvgScore(){
		return dailyAvgScore;
	}
	
	public ArrayList<String> getFriend(){
		return friend;
	}
	
	public int getMaxCombo(){
		return maxCombo;
	}
	
	public int getMaxScore(){
		return maxScore;
	}
	
	public ArrayList<Integer> getRecentGameScores(){
		return recentGameScores;
	}
	
	public HashMap<Date,Integer> getDailyTotalGameNum(){
		return dailyTotalGameNum;
	}
	
	public ArrayList<Date> getDate(){
		return this.date;
	}

	public int getGameNum(){
		return this.gameNum;
	}
	
	public int getAvgScore(){
		return this.avgScore;
	}
	
	public ArrayList<String> getFriendRequest(){
		return this.friendRequest;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	public void setDailyAvgScore(HashMap<Date,Integer> dailyAvgScore){
		this.dailyAvgScore = dailyAvgScore;
	}
	
	public void setFriend(ArrayList<String> friend){
		this.friend = friend;
	}
	
	public void setMaxCombo(int maxCombo){
		this.maxCombo = maxCombo;
	}
	
	public void setMaxScore(int maxScore){
		this.maxScore = maxScore;
	}
	
	public void setRecentGameScores(ArrayList<Integer> recentGameScores){
		this.recentGameScores = recentGameScores;
	}
	
	public void setDailyTotalGameNum(HashMap<Date,Integer> dailyTotalGameNum){
		this.dailyTotalGameNum = dailyTotalGameNum;
	}
	
	public void setDate(ArrayList<Date> date){
		this.date = date;
	}
	
	public void setGameNum(int gameNum){
		this.gameNum = gameNum;
	}
	
	public void setAvgScore(int avgScore){
		this.avgScore = avgScore;
	}
	
	public void setFriendRequest(ArrayList<String> friendRequest){
		this.friendRequest = friendRequest;
	}

}
