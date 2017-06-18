package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import po.user.UserPO;
import model.DataBaseHelper.DataBaseHelper;
import modelservice.user.*;
public class UserModel implements UserModelService {
	/*user表中有userName，password，coin，maxCombo，maxScore，gameNum，avgScore七个字段*/
	DataBaseHelper db = new DataBaseHelper();
	String tableName = "user";
	
	/* (non-Javadoc)
	 * @see model.user.UserModelService#insert(po.user.UserPO)
	 */
	@Override
	public String insert(UserPO po){
		String sqlInfo = "INSERT INTO "+tableName+"(userName,password,coin,maxCombo,maxScore,gameNum,avgScore) VALUES ('"+po.getUserName()+"','"+po.getPassword()
				+"','"+po.getCoin()+"','"+po.getMaxCombo()+"','"+po.getMaxScore()+"','"+po.getGameNum()+"','"+po.getAvgScore()+"')";
		return db.insert(sqlInfo);
		 
	}
	/* (non-Javadoc)
	 * @see model.user.UserModelService#delete(po.user.UserPO)
	 */
	@Override
	public String delete(UserPO po){
		String sqlInfo = "delete from "+tableName+" where userName = '"+po.getUserName()+"'";
		return db.delete(sqlInfo);
	}
	/* (non-Javadoc)
	 * @see model.user.UserModelService#modify(po.user.UserPO)
	 */
	@Override
	public String modify(UserPO po){
		String sqlInfo = "update "+tableName+" set password = '"+po.getPassword()+"', coin = '"+po.getCoin()+"', maxCombo = '" + po.getMaxCombo()+"', maxScore = '"+po.getMaxScore()+"', gameNum = '"
				+po.getGameNum()+ "', avgScore = '" + po.getAvgScore()+"' where userName = '"+po.getUserName()+"'";
		return db.update(sqlInfo);
	}
	/* (non-Javadoc)
	 * @see model.user.UserModelService#find(po.user.UserPO)
	 */
	public UserPO find(UserPO po){
		String sqlInfo = "select userName,password,coin,maxCombo,maxScore,gameNum,avgScore from "
				+tableName +" where userName = '"+po.getUserName() +"'";
		
		try {
			ResultSet rs = db.find(sqlInfo);
	
			if(rs.next()){
				po.setUserName(rs.getString(1));
				po.setPassword(rs.getString(2));
				po.setCoin(Integer.parseInt(rs.getString(3)));
				po.setMaxCombo(Integer.parseInt(rs.getString(4)));
				po.setMaxScore(Integer.parseInt(rs.getString(5)));
				po.setGameNum(Integer.parseInt(rs.getString(6)));
				po.setAvgScore(Integer.parseInt(rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return po;
	}
	
	public ArrayList<UserPO> find(){
		String sqlInfo = "select userName,password,coin,maxCombo,maxScore,gameNum,avgScore from " +tableName;
		ArrayList<UserPO> poList = new ArrayList<UserPO>();
		
		try {
			ResultSet rs = db.find(sqlInfo);
			
			while(rs.next()){
				UserPO po = new UserPO();
				po.setUserName(rs.getString(1));
				po.setPassword(rs.getString(2));
				po.setCoin(Integer.parseInt(rs.getString(3)));
				po.setMaxCombo(Integer.parseInt(rs.getString(4)));
				po.setMaxScore(Integer.parseInt(rs.getString(5)));
				po.setGameNum(Integer.parseInt(rs.getString(6)));
				po.setAvgScore(Integer.parseInt(rs.getString(7)));
				poList.add(po);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return poList;
	}
	
	public int find(String userName){
		String sqlInfo = "select userName from "+tableName +" where userName = '"+userName + "'";
		int result = 0;
		try {
			ResultSet rs = db.find(sqlInfo);
			if(rs.next()){
				result = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
