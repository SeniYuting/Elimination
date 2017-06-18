package controller;

import java.util.ArrayList;

import model.rank.RankModel;
import model.user.DailyPlayModel;
import model.user.FriendModel;
import model.user.FriendRequestModel;
import model.user.RecentScoreModel;
import model.user.UserModel;
import modelservice.rank.RankModelService;
import modelservice.user.*;
import po.message.Message;
import po.rank.NetSingleRankPO;
import po.rank.RankPO;
import po.user.UserPO;

public class Controller {
	
	public ArrayList<NetSingleRankPO> getNetSingleRankPO() {
		UserModelService um = new UserModel();
		ArrayList<UserPO> players = um.find();
		ArrayList<NetSingleRankPO> rank = new ArrayList<NetSingleRankPO>();

		for (int i = 0; i < players.size(); i++) {
			UserPO user = players.get(i);
			NetSingleRankPO po = new NetSingleRankPO(user.getUserName(),
					user.getMaxScore());
			rank.add(po);
		}

		rank = sort(rank);

		return rank;
	}

	// 此方法对联网单机的成绩进行排序，然后保留前12个
	public ArrayList<NetSingleRankPO> sort(ArrayList<NetSingleRankPO> po) {
		for (int i = 0; i < po.size() - 1; i++) {
			for (int j = i + 1; j < po.size(); j++) {
				NetSingleRankPO po_i = po.get(i);
				int score_i = po_i.getScore();
				NetSingleRankPO po_j = po.get(j);
				NetSingleRankPO tempPO = po_i;
				int score_j = po.get(j).getScore();
				if (score_j > score_i) {
					po.set(i, po_j);
					po.set(j, tempPO);
				}

			}
		}

		while (po.size() > 12) {
			po.remove(po.size() - 1);
		}

		return po;
	}

	// 此方法对联网协作的成绩进行排序，然后保留前15个
	public ArrayList<RankPO> sortCoop(ArrayList<RankPO> po) {
		for (int i = 0; i < po.size() - 1; i++) {
			for (int j = i + 1; j < po.size(); j++) {
				RankPO po_i = po.get(i);
				int score_i = po_i.getScore();
				RankPO po_j = po.get(j);
				RankPO tempPO = po_i;
				int score_j = po.get(j).getScore();
				if (score_j > score_i) {
					po.set(i, po_j);
					po.set(j, tempPO);
				}

			}
		}

		while (po.size() > 15) {
			po.remove(po.size() - 1);
		}

		return po;
	}

	// 获取所有用户姓名
	public ArrayList<UserPO> getAllUsers() {
		UserModelService ums = new UserModel();
		ArrayList<UserPO> users = ums.find();
		return users;
	}

	// 获取所有协作组的排行
	public ArrayList<RankPO> getCoopRank() {
		RankModelService rms = new RankModel();
		ArrayList<RankPO> rankPO = rms.find();
		rankPO = sortCoop(rankPO);
		return rankPO;
	}

	public Message login(UserPO user) {
		String inputPassword = user.getPassword();
		UserModelService ums = new UserModel();
		FriendModelService fms = new FriendModel();
		RecentScoreModelService rsms = new RecentScoreModel();
		DailyPlayModelService dpms = new DailyPlayModel();
		FriendRequestModelService frms = new FriendRequestModel();
		Message message = new Message(user);

		if (ums.find(user.getUserName()) == 0) {
			message.setSuccess("用户名不存在！");
		} else if (inputPassword.equals(ums.find(message.getUser())
				.getPassword())) {
			message.setSuccess("登录成功！");
			UserPO po = ums.find(message.getUser());
			po = dpms.find(po);
			po = rsms.find(po);
			po = fms.find(po);
			po.setFriendRequest(frms.find(po));
			deleteFriendRequest(po.getUserName());
			message.setUser(po);
		} else {
			message.setSuccess("密码错误！");
		}

		return message;
	}

	public Message register(UserPO user) {
		UserModelService ums = new UserModel();
		String registerUserName = user.getUserName();
		Message message = new Message(user);

		if (ums.find(registerUserName) == 1) {
			// 这里在之后会添加适当的返回信息帮助判断注册失败的原因...
			message.setSuccess("注册的用户名已存在！");
		} else {
			ums.insert(message.getUser());
			message.setSuccess("注册成功！");
		}

		return message;
	}

	public Message getUserPO(Message message) {
		UserModelService ums = new UserModel();
		FriendModelService fms = new FriendModel();
		RecentScoreModelService rsms = new RecentScoreModel();
		DailyPlayModelService dpms = new DailyPlayModel();
		message.setUser(ums.find(message.getUser()));
		message.setUser(dpms.find(message.getUser()));
		message.setUser(fms.find(message.getUser()));
		message.setUser(rsms.find(message.getUser()));
		return message;
	}

	public void setUserPO(UserPO po) {
		UserModelService ums = new UserModel();
		FriendModelService fms = new FriendModel();
		RecentScoreModelService rsms = new RecentScoreModel();
		DailyPlayModelService dpms = new DailyPlayModel();
		ums.modify(po);
		fms.modify(po);
		rsms.modify(po);
		dpms.modify(po);
	}

	public void makeFriends(String name1, String name2) {
		FriendModelService fms = new FriendModel();
		ArrayList<String> friend1 = new ArrayList<String>();
		ArrayList<String> friend2 = new ArrayList<String>();
		friend1.add(name2);
		friend2.add(name1);
		UserPO po1 = new UserPO(friend1, name1);
		UserPO po2 = new UserPO(friend2, name2);

		fms.insert(po1);
		fms.insert(po2);
	}

	public void changePassword(UserPO po) {
		UserModelService ums = new UserModel();
		String password = po.getPassword();
		UserPO user = ums.find(po);
		user.setPassword(password);
		ums.modify(user);
	}

	public void setFriendRequest(String userName, String friendName) {
		FriendRequestModelService frms = new FriendRequestModel();
		frms.insert(userName, friendName);
	}

	public void deleteFriendRequest(String userName) {
		FriendRequestModelService frms = new FriendRequestModel();
		frms.delete(userName);
	}

	public void setCoopRank(ArrayList<String> userNames,int score) {
		RankPO po = new RankPO(score,userNames);
		RankModelService rms = new RankModel();
		rms.insert(po);
	}

}
