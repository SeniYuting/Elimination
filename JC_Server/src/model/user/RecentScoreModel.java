package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import po.user.UserPO;
import model.DataBaseHelper.DataBaseHelper;
import modelservice.user.RecentScoreModelService;

public class RecentScoreModel implements RecentScoreModelService {
	/*recentscore������userName��gameScore��turn�����ֶ�*/
	DataBaseHelper db = new DataBaseHelper();
	String tableName = "recentscore";
	@Override
	public String insert(UserPO po) {
		// TODO Auto-generated method stub
		for(int i=0;i<po.getRecentGameScores().size()&&i<10;i++){
			String sqlInfo = "INSERT INTO "+tableName+"(userName,gameScore,turn) VALUES ('"+po.getUserName()+"','"
					+po.getRecentGameScores().get(i)+"','"+(i+1)+"')";
			String s = db.insert(sqlInfo);
			if(!(s.equals("��ӳɹ���"))){
				return "���ʧ�ܣ�";
			}
		}
		return "��ӳɹ���";
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
		String sqlInfo = "select userName,gameScore,turn from "
				+tableName +" where userName = '"+po.getUserName()+"' order by turn";
		ArrayList<Integer> gameScore = new ArrayList<Integer>();
		
		try {
			ResultSet rs = db.find(sqlInfo);
		
			while(rs.next()){
	            gameScore.add(Integer.parseInt(rs.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		po.setRecentGameScores(gameScore);
		return po;
	}

	@Override
	public String modify(UserPO po) {
		// TODO Auto-generated method stub
		delete(po);
	    insert(po);
	    return "���³ɹ���";
	}

}
