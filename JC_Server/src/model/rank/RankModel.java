package model.rank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import po.rank.RankPO;
import model.DataBaseHelper.DataBaseHelper;
import modelservice.rank.RankModelService;

public class RankModel implements RankModelService {
	/*cooprank表中有userNames,score两个字段*/
	DataBaseHelper db = new DataBaseHelper();
	String tableName = "cooprank";
	
	@Override
	public String insert(RankPO po){
		String names = transformListToStr(po.getUserNames());
		String sqlInfo = "INSERT INTO "+tableName+"(userNames,score) VALUES ('"+names
				+"','"+po.getScore()+"')";
		String info = db.insert(sqlInfo);
		if(info.equals("添加失败！")){
			return "添加失败！";
		}else{
			return "添加成功！";
		}
	}
	
	@Override
	public String delete(RankPO po){
		String names = transformListToStr(po.getUserNames());
		String sqlInfo = "delete from "+tableName+" where userNames = '"+names+"'";
		return db.delete(sqlInfo);
	}
	
	@Override
	public void modify(RankPO po){
		
	}
	
	@Override
	//查询小组成员的得分
	public RankPO find(RankPO po){
		String names = transformListToStr(po.getUserNames());
		String sqlInfo = "select userNames,score from "+tableName +" where userNames = '"+names+"'";
		Connection conn = db.getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlInfo);
			int score = 0;
			if(rs.next()){
				score = Integer.parseInt(rs.getString(2));
			}
			po.setScore(score);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return po;
	}
	
	public ArrayList<RankPO> find() {
		String sqlInfo = "select userNames,score from "+tableName;
		Connection conn = db.getConnection();
		ArrayList<RankPO> rankPO = new ArrayList<RankPO>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlInfo);
			int score = 0;
			String names = "";
			ArrayList<String> userNames = new ArrayList<String>();
			while(rs.next()){
				RankPO po = new RankPO();
				names = rs.getString(1);
				userNames = transformStrToList(names);
				score = Integer.parseInt(rs.getString(2));
				po.setScore(score);
				po.setUserNames(userNames);
				rankPO.add(po);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rankPO;
	}
	
	public String transformListToStr(ArrayList<String> userNames){
		String names = userNames.get(0);
		for(int i = 1;i < userNames.size();i++){
			names = names + "/" +userNames.get(i);
		}
		return names;
	}
	
	public ArrayList<String> transformStrToList(String name){
		String[] token = name.split("/");
		ArrayList<String> userNames = new ArrayList<String>();
		for(int i = 0;i < token.length;i++){
			userNames.add(token[i]);
		}
		return userNames;
	}
	
}
