package model.user;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.DataBaseHelper.DataBaseHelper;
import modelservice.user.DailyPlayModelService;
import po.user.UserPO;

public class DailyPlayModel implements DailyPlayModelService{
	/*dailyplay表中有userName，dailyGameNum，dailyAvgScore，date四个字段*/
	DataBaseHelper db = new DataBaseHelper();
	String tableName = "dailyplay";
	
	public String insert(UserPO po){
		for(int i=0;i<po.getDate().size()&&i<7;i++){
			Date date = po.getDate().get(i);
			String sqlInfo = "INSERT INTO "+tableName+"(userName,dailyGameNum,dailyAvgScore,date) VALUES ('"
					+po.getUserName()+"','"+po.getDailyTotalGameNum().get(date)+"','"+po.getDailyAvgScore().get(date)
					+"','"+date+"')";
			String s = db.insert(sqlInfo);
			if(!(s.equals("添加成功！"))){
				return "添加失败！";
			}
		}
		return "添加成功！";
		 
	}
	
	
	public String delete(UserPO po){
		String sqlInfo = "delete from "+tableName+" where userName = '"+po.getUserName()+"'";
		return db.delete(sqlInfo);
	}
	
	
	public UserPO find(UserPO po){
		String sqlInfo = "select userName,dailyGameNum,dailyAvgScore,date from "
				+tableName +" where userName = '"+po.getUserName()+"' order by date";
		ArrayList<Date> date = new ArrayList<Date>();
		HashMap<Date,Integer> dailyGameNum = new HashMap<Date,Integer>();
		HashMap<Date,Integer> dailyAvgScore = new HashMap<Date,Integer>();
		try {
			ResultSet rs = db.find(sqlInfo);
			
			while(rs.next()){
				Date single_date = Date.valueOf(rs.getString(4));
	            date.add(single_date);
				dailyGameNum.put(single_date,Integer.parseInt(rs.getString(2)));
				dailyAvgScore.put(single_date,Integer.parseInt(rs.getString(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po.setDailyTotalGameNum(dailyGameNum);
		po.setDailyAvgScore(dailyAvgScore);
		po.setDate(date);
		return po;
	}


	@Override
	public String modify(UserPO po) {
		// TODO Auto-generated method stub
		delete(po);
		insert(po);
		return "更新成功！";
	}

}
