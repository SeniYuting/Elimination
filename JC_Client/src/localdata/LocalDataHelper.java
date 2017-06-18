package localdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import po.user.UserPO;

public class LocalDataHelper {
	/*�������а�Ĵ����߼�
	 * һ����3��txt
	 * ��һ����SingleValue.txt,һ��5��,���δ洢�ܾ�����ƽ���÷֡����������������ߵ÷֡������
	 * �ڶ�����RecentScore.txt,һ��10��,���δ洢���10�ֵ÷�,��һ�е����
	 * ��������DailyGame.txt,һ��7��,ÿ�����δ洢���ڡ����վ���������ƽ���÷�(�м��ÿո����)
	 */
	
	//��ȡSingleValue.txt�е�����
	public UserPO getSingleValue(UserPO po){
		File file = new File("SingleValue.txt");
		int gameNum = -1;
		int avgScore = -1;
		int maxCombo = -1;
		int maxScore = -1;
		int coin = -1;
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(in);
			gameNum = Integer.parseInt(br.readLine());
			avgScore = Integer.parseInt(br.readLine());
			maxCombo = Integer.parseInt(br.readLine());
			maxScore = Integer.parseInt(br.readLine());
			coin = Integer.parseInt(br.readLine());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		po.setGameNum(gameNum);
		po.setAvgScore(avgScore);
		po.setMaxCombo(maxCombo);
		po.setMaxScore(maxScore);
		po.setCoin(coin);
		
		return po;
	}
	
	//��ȡRecentScore.txt�е�����
	public UserPO getRecentScore(UserPO po){
		File file = new File("RecentScore.txt");
		ArrayList<Integer> recentScore = new ArrayList<Integer>();
	
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(in);
			String line = "";
			while((line = br.readLine())!=null){
				recentScore.add(Integer.parseInt(line));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		po.setRecentGameScores(recentScore);
			
		return po;
	}
	
	//��ȡDailyGame.txt�е�����
	public UserPO getDailyGame(UserPO po){
		File file = new File("DailyGame.txt");
		ArrayList<Date> date = new ArrayList<Date>();
		HashMap<Date,Integer> dailyGameNum = new HashMap<Date,Integer>();
		HashMap<Date,Integer> dailyAvgScore = new HashMap<Date,Integer>();
	
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream(file));
			BufferedReader br = new BufferedReader(in);
			String line = "";
			while((line = br.readLine())!=null){
				String[] token = line.split(" ");
				date.add(Date.valueOf(token[0]));
				//System.out.println(Date.valueOf(token[0]).toString());
				dailyGameNum.put(Date.valueOf(token[0]),Integer.parseInt(token[1]));
				//System.out.println(Integer.parseInt(token[1]));
				dailyAvgScore.put(Date.valueOf(token[0]),Integer.parseInt(token[2]));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		po.setDate(date);
		po.setDailyTotalGameNum(dailyGameNum);
		po.setDailyAvgScore(dailyAvgScore);
			
		return po;
	}
	
	//��ȡ3��txt��������Ϣ
	public UserPO getFullUserPO(){
		UserPO po = new UserPO("000","000");
		po = getSingleValue(po);
		po = getRecentScore(po);
		po = getDailyGame(po);
		return po;
	}
	
	public void setSingleValue(UserPO po){
		File file = new File("SingleValue.txt");
		int gameNum = po.getGameNum();
		int avgScore = po.getAvgScore();
		int maxCombo = po.getMaxCombo();
		int maxScore = po.getMaxScore();
		int coin  = po.getCoin();
		
		try{
			FileWriter fw = new FileWriter(file);
			fw.write(gameNum+"\r\n");
			fw.write(avgScore+"\r\n");
			fw.write(maxCombo+"\r\n");
			fw.write(maxScore+"\r\n");
			fw.write(coin+"\r\n");
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setRecentScore(UserPO po){
		File file = new File("RecentScore.txt");
		ArrayList<Integer> recentScore = po.getRecentGameScores();
		
		try{
			FileWriter fw = new FileWriter(file);
			for(int i = 0;i < 10 && i < recentScore.size();i++){
				fw.write(recentScore.get(i)+"\r\n");
			}
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setDailyGame(UserPO po){
		File file = new File("DailyGame.txt");
		ArrayList<Date> date = po.getDate();
		HashMap<Date,Integer> dailyGameNum = po.getDailyTotalGameNum();
		HashMap<Date,Integer> dailyAvgScore = po.getDailyAvgScore();
		
		try{
			FileWriter fw = new FileWriter(file);
			for(int i = 0;i < 7&& i < date.size();i++){
				fw.write(date.get(i)+" ");
				fw.write(dailyGameNum.get(date.get(i))+ " ");
				fw.write(dailyAvgScore.get(date.get(i))+"\r\n");
			}
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
