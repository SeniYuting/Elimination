package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import po.user.UserPO;
import model.DataBaseHelper.DataBaseHelper;
import modelservice.user.FriendModelService;

public class FriendModel implements FriendModelService{
	/*friend表中有userName，friendName两个字段*/
	DataBaseHelper db = new DataBaseHelper();
	String tableName = "friend";
	@Override
	public String insert(UserPO po) {
		for(int i=0;i<po.getFriend().size();i++){
			String sqlInfo = "INSERT INTO "+tableName+"(userName,friendName) VALUES ('"+po.getUserName()+"','"
					+po.getFriend().get(i)+"')";
			String s = db.insert(sqlInfo);
			if(!(s.equals("添加成功！"))){
				return "添加失败！";
			}
		}
		return "添加成功！";
	}

	@Override
	public String delete(UserPO po) {
		// TODO Auto-generated method stub
		String sqlInfo = "delete from "+tableName+" where userName = '"+po.getUserName()+"'";
		return db.delete(sqlInfo);
	}

	@Override
	public UserPO find(UserPO po) {
		// TODO Auto-generated method stub
		String sqlInfo = "select userName,friendName from "
				+tableName +" where userName = '"+po.getUserName()+"'";
		ArrayList<String> friend = new ArrayList<String>();
		
		try {
			ResultSet rs = db.find(sqlInfo);
	
			while(rs.next()){
	            friend.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po.setFriend(friend);
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
