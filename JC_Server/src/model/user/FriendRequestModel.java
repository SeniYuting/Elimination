package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import po.user.UserPO;
import model.DataBaseHelper.DataBaseHelper;
import modelservice.user.FriendRequestModelService;

public class FriendRequestModel implements FriendRequestModelService{
	/*friendrequest表中有userName，friendName两个字段*/
	DataBaseHelper db = new DataBaseHelper();
	String tableName = "friendrequest";
	
	@Override
	public String insert(String userName, String friendName) {
		String sqlInfo = "INSERT INTO "+tableName+"(userName,friendName) VALUES ('"+userName+"','"+friendName+"')";
		return db.insert(sqlInfo);
	}

	@Override
	public String delete(String userName) {
		String sqlInfo = "delete from "+tableName+" where friendName = '"+userName+"'";
		return db.delete(sqlInfo);
	}
	
	public ArrayList<String> find(UserPO po) {
		String sqlInfo =  "select userName,friendName from "
				+tableName +" where friendName = '"+po.getUserName()+"'";
		ArrayList<String> friendRequest = new ArrayList<String>();
		
		try {
			ResultSet rs = db.find(sqlInfo);
	
			while(rs.next()){
	            friendRequest.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return friendRequest;
	}
	
}
