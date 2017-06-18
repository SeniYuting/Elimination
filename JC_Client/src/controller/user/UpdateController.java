package controller.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import localdata.LocalDataHelper;
import netservice.SocketClientService;
import po.message.Message;
import po.user.UserPO;
import controllerservice.user.UpdateControllerService;

public class UpdateController implements UpdateControllerService{
	SocketClientService s;
	
	public UpdateController(){
		
	}
	
	public UpdateController(SocketClientService s){
		this.s = s;	
	}
	
	//�ο�ģʽ�¸��½��
	public void updateLocalCoins(UserPO po){
		LocalDataHelper ldh = new LocalDataHelper();
		ldh.setSingleValue(po);
	}

	//��������ģʽ�¸��½����
	@Override
	public void updateNetCoins(UserPO po) {
		if(s==null){
//			System.out.print("null");
		}
		s.update(po);
		
	}
	
	//��Ϸ���������ͳ����Ϣ
	public void updateUserPO(String userName,int score,int combo,int coin){
		UserPO po = new UserPO(userName);
		LocalDataHelper ldh = new LocalDataHelper();
		
		if(userName.equals("000")){
			po = ldh.getFullUserPO();
		}else{
			Message m = s.getUserPO(po);
			po = m.getUser();
		}
		
		//�ж��Ƿ�Խ��ߵ÷֣�����Խ����д��ߵ÷�
		int maxScore = po.getMaxScore();
		if(score > maxScore){
			po.setMaxScore(score);
		}
		
		//�ж�����������Ƿ�ˢ��
		int maxCombo = po.getMaxCombo();
		if(combo > maxCombo){
			po.setMaxCombo(combo);
		}
		
		//���¼���ƽ���÷�
		int newGameNum = po.getGameNum() + 1;
		int avgScore = (po.getAvgScore()*po.getGameNum()+score)/newGameNum;
		po.setAvgScore(avgScore);				
		
		//�ܾ�����1
		po.setGameNum(newGameNum);
		
		//���½����
		po.setCoin(po.getCoin()+coin);
		
		//�������ʮ�ֵ÷�
		ArrayList<Integer> recentScore = po.getRecentGameScores();

		if(recentScore.size() == 10){
			recentScore.remove(9);
		}
		recentScore.add(0,score);
		po.setRecentGameScores(recentScore);
		
		//���µ���ľ�����ƽ���÷�
		ArrayList<Date> date = po.getDate();
	    HashMap<Date,Integer> dailyGameNum = po.getDailyTotalGameNum();
	    HashMap<Date,Integer> dailyAvgScore = po.getDailyAvgScore();
	    Date today = new Date(System.currentTimeMillis());
	    
	    if(date.size() == 0){
	    	dailyGameNum.put(today, 1);
	    	dailyAvgScore.put(today, score);
	    	date.add(0,today);  
	    }else if(date.get(date.size()-1).toString().equals(today.toString())){
	    	int todayAvgScore = dailyAvgScore.get(date.get(date.size()-1));
	    	int todayGameNum = dailyGameNum.get(date.get(date.size()-1));
	    	int newTodayGameNum = todayGameNum + 1;
	    	int newTodayAvgScore = (todayAvgScore * todayGameNum + score)/newTodayGameNum;
	    	dailyAvgScore.put(date.get(date.size()-1), newTodayAvgScore);
	    	dailyGameNum.put(date.get(date.size()-1), newTodayGameNum);
	    }else{
	    	if(dailyGameNum.size() == 7){
	    		dailyGameNum.remove(date.get(0));
	    		dailyAvgScore.remove(date.get(0));
	    		date.remove(0);
	    	}
	    	dailyGameNum.put(today, 1);
	    	dailyAvgScore.put(today, score);
	    	date.add(date.size(),today);  	
	    }
	    po.setDate(date);
	    po.setDailyAvgScore(dailyAvgScore);
	    po.setDailyTotalGameNum(dailyGameNum);

	    if(userName.equals("000")){
	    	ldh.setSingleValue(po);
	    	ldh.setRecentScore(po);
	    	ldh.setDailyGame(po);
	    }else{
	    	s.update(po);
	    }
	    
	}
	
	//�޸�����
	public void changePassword(String user,String password){
		UserPO po = new UserPO(user,password);
		s.changPassword(po);
	}

}
