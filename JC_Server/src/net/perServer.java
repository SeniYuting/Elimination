package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import po.message.Message;
import po.user.UserPO;
import controller.Controller;
import controller.game.GameController;
import controller.game.GameControllerService;
import controller.game.GameSituationException;
import controller.game.ReqResult;
import controller.game.Tile;

/**
 * <code><b>perServer</b></code> contains functions about service for every
 * client.
 * 
 * @author Hongyu Qu
 */
public class perServer implements Runnable {

	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;

	Message message;
	HashMap<String, perServer> hashm;
	boolean onLine;

	UserPO user, to_user;

	public perServer(Socket socket, HashMap<String, perServer> hashm)
			throws IOException {
		this.socket = socket;
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
		this.hashm = hashm;
		message = new Message();
		onLine = true;
	}

	public void sendMessage(Message message) throws IOException {
		out.writeObject(message);
	}

	private void getCommand() throws IOException {
		Message message = null;
		UserPO userPo;
		UserPO to_user;
		Message inviteMessage;
		Message acceptMessage;
		Message rejectMessage;
		GameController gamecontroller;
		HashMap<String, perServer> coopHs;
		ArrayList<ReqResult> resultList = new ArrayList<ReqResult>();
		ArrayList<ReqResult> reqList;
		Set<String> coopers;
		try {
			message = (Message) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Controller con = new Controller();

		switch (message.getCommand()) {
		case "login":
			message = con.login(message.getUser());
			if (message.isSuccess().equals("登录成功！")) {
				if (hashm.containsKey(message.getUser().getUserName())) {
					message.setSuccess("已在别处登录");
				}
			}
			sendMessage(message);
			break;

		case "logout":
			hashm.remove(message.getUser().getUserName());
			System.out.println(message.getUser().getUserName());
			break;

		case "listen":
			hashm.put(message.getUser().getUserName(), this);
			this.user = message.getUser();
			break;

		case "acceptpk":
			System.out.println("acceptpk");
			to_user = message.getToUser();
			userPo = message.getUser();
			acceptMessage = new Message(userPo);
			acceptMessage.setCommand(Message.cmd_acceptplgame);
			CoopManager.pkList.get(to_user.getUserName()).put(
					userPo.getUserName(), this);
			CoopManager.pkList.get(to_user.getUserName())
					.get(to_user.getUserName()).sendMessage(acceptMessage);
			break;

		case "acceptplgame":

			to_user = message.getToUser();
			userPo = message.getUser();
			if (CoopManager.coopController.containsKey(to_user.getUserName())) {
				Message mes = new Message();
				mes.setCommand(Message.cmd_coopinvalid);
				sendMessage(mes);
			} else {
				acceptMessage = new Message(userPo);
				acceptMessage.setCommand(Message.cmd_acceptplgame);
				CoopManager.coopList.get(to_user.getUserName()).put(
						userPo.getUserName(), this);
				CoopManager.coopList.get(to_user.getUserName())
						.get(to_user.getUserName()).sendMessage(acceptMessage);
			}

			break;

		case "rejectplgame":
			to_user = message.getToUser();
			userPo = message.getUser();
			rejectMessage = new Message(userPo);
			rejectMessage.setCommand(Message.cmd_rejectplgame);
			CoopManager.coopList.get(to_user.getUserName())
					.get(to_user.getUserName()).sendMessage(rejectMessage);
			break;

		case "rejectpk":
			to_user = message.getToUser();
			userPo = message.getUser();
			rejectMessage = new Message(userPo);
			rejectMessage.setCommand(Message.cmd_rejectplgame);
			CoopManager.pkList.get(to_user.getUserName())
					.get(to_user.getUserName()).sendMessage(rejectMessage);
			break;

		case "pkstart":

			userPo = message.getUser();// 通过协作发起者得到所有协作用户
			coopHs = CoopManager.pkList.get(userPo.getUserName());
			coopers = coopHs.keySet();

			GameControllerService pkCtrl = new GameController(message.isUp,
					message.isChain);
			ArrayList<Tile> tileList1 = pkCtrl.initGame();

			for (String cooper : coopers) {
				perServer server = coopHs.get(cooper);// 得到了所有协作用户的socket
				message = new Message(tileList1);
				message.setCommand(Message.cmd_pkstart);
				server.sendMessage(message);
			}

			break;

		case "pkfinish":

			userPo = message.getUser();// 通过pk发起者得到socket
			coopHs = CoopManager.pkList.get(userPo.getUserName());
			if (message.getToUser() == null) {// 这是协作发起者
				coopers = coopHs.keySet();
				for (String cooper : coopers) {
					if (!coopers.equals(userPo.getUserName())) {
						perServer server = coopHs.get(cooper);
						server.sendMessage(message);
					}
				}

			} else {
				perServer server = coopHs.get(userPo.getUserName());
				server.sendMessage(message);
			}

			break;

		case "coopstart":

			userPo = message.getUser();// 通过协作发起者得到所有协作用户
			coopHs = CoopManager.coopList.get(userPo.getUserName());
			coopers = coopHs.keySet();

			GameControllerService controller = new GameController(message.isUp,
					message.isChain);
			CoopManager.coopController.put(userPo.getUserName(),
					(GameController) controller);
			ArrayList<Tile> tileList = controller.initGame();

			for (String cooper : coopers) {
				perServer perServer = coopHs.get(cooper);// 得到了所有协作用户的socket
				Message startMessage = new Message(tileList);
				startMessage.setCommand(Message.cmd_coopstart);
				startMessage.isShark = message.isShark;
				startMessage.isUp = message.isUp;
				startMessage.isChain = message.isChain;
				perServer.sendMessage(startMessage);
			}

			break;

		case "coopfinish":

			ArrayList<String> userList = new ArrayList<>();
			coopHs = CoopManager.coopList.get(message.getUser().getUserName());
			coopers = coopHs.keySet();
			for (String cooper : coopers) {
				userList.add(cooper);
			}
			int score = CoopManager.coopController.get(
					message.getUser().getUserName()).getScore();
			int maxcombo = CoopManager.coopController.get(
					message.getUser().getUserName()).getMaxCombo();
			for (String cooper : coopers) {
				perServer perServer = coopHs.get(cooper);
				message = new Message(score, maxcombo);
				message.setCommand(Message.cmd_coopfinish);
				perServer.sendMessage(message);
				System.out.println(cooper);
			}
			con.setCoopRank(userList, score);
			CoopManager.coopList.remove(message.getUser().getUserName());
			CoopManager.coopController.remove(message.getUser().getUserName());
			break;

		case "coopbreak":// 这里要通知

			CoopManager.coopList.remove(message.getUser().getUserName());
			CoopManager.coopController.remove(message.getUser().getUserName());

			break;

		case "swap":

			userPo = message.getUser();
			coopHs = CoopManager.coopList.get(userPo.getUserName());
			coopers = coopHs.keySet();
			gamecontroller = CoopManager.coopController.get(userPo
					.getUserName());
			resultList = gamecontroller.swap(message.getTile1(),
					message.getTile2());

			reqList = new ArrayList<ReqResult>();
			for (ReqResult results : resultList) {
				ArrayList<Tile> allTile = new ArrayList<Tile>();
				for (Tile t : results.getTileList()) {
					allTile.add(t.clone());
				}
				ArrayList<Tile> bombTile = new ArrayList<Tile>();
				for (Tile t : results.getBombList()) {
					bombTile.add(t.clone());
				}
				reqList.add(new ReqResult(allTile, bombTile, results.getScore()));
			}

			message = new Message(reqList, 0);
			message.setCommand(Message.cmd_updategame);
			for (String cooper : coopers) {
				perServer perServer = coopHs.get(cooper);// 得到了所有协作用户的socket
				perServer.sendMessage(message);
			}

			break;

		case "clickprops":

			userPo = message.getUser();
			coopHs = CoopManager.coopList.get(userPo.getUserName());
			coopers = coopHs.keySet();
			gamecontroller = CoopManager.coopController.get(userPo
					.getUserName());
			try {
				resultList = gamecontroller.checkTile(message.getTile1());
			} catch (GameSituationException e) {
				e.printStackTrace();
			}

			reqList = new ArrayList<ReqResult>();
			for (ReqResult results : resultList) {
				ArrayList<Tile> allTile = new ArrayList<Tile>();
				for (Tile t : results.getTileList()) {
					allTile.add(t.clone());
				}
				ArrayList<Tile> bombTile = new ArrayList<Tile>();
				for (Tile t : results.getBombList()) {
					bombTile.add(t.clone());
				}
				reqList.add(new ReqResult(allTile, bombTile, results.getScore()));
			}

			message = new Message(reqList, 0);
			message.setCommand(Message.cmd_updategame);
			for (String cooper : coopers) {
				perServer perServer = coopHs.get(cooper);// 得到了所有协作用户的socket
				perServer.sendMessage(message);
			}

			break;

		case "useShark":

			userPo = message.getUser();
			coopHs = CoopManager.coopList.get(userPo.getUserName());
			coopers = coopHs.keySet();
			gamecontroller = CoopManager.coopController.get(userPo
					.getUserName());
			resultList = gamecontroller.useSharkProp();

			reqList = new ArrayList<ReqResult>();
			for (ReqResult results : resultList) {
				ArrayList<Tile> allTile = new ArrayList<Tile>();
				for (Tile t : results.getTileList()) {
					allTile.add(t.clone());
				}
				ArrayList<Tile> bombTile = new ArrayList<Tile>();
				for (Tile t : results.getBombList()) {
					bombTile.add(t.clone());
				}
				reqList.add(new ReqResult(allTile, bombTile, results.getScore()));
			}

			message = new Message(reqList, 0);
			message.setCommand(Message.cmd_updategame);
			for (String cooper : coopers) {
				perServer perServer = coopHs.get(cooper);// 得到了所有协作用户的socket
				perServer.sendMessage(message);
			}

			break;

		case "register":
			message = con.register(message.getUser());
			sendMessage(message);
			break;

		case "changepw":
			con.changePassword(message.getUser());
			break;

		case "addfriends":
			if (hashm.containsKey(message.getFriendName())) {
				hashm.get(message.getFriendName()).sendMessage(message);
			} else {
				con.setFriendRequest(message.getUserName(),
						message.getFriendName());
			}
			sendMessage(message);
			break;

		case "tobefriend":
			// 调用数据库的方法写入好友关系
			System.out.print(message.getUserName() + "name");
			con.makeFriends(message.getUserName(), message.getFriendName());
			break;

		case "invitplgame":// 邀请好友玩一局游戏;
			to_user = message.getToUser();
			userPo = message.getUser();
			inviteMessage = new Message(userPo);
			inviteMessage.setCommand(Message.cmd_invitedplgame);
			hashm.get(to_user.getUserName()).sendMessage(inviteMessage);
			if (!CoopManager.coopList.containsKey(userPo.getUserName())) {// 如果hashmap中还没有该协作发起者
				// 将协作发起者的姓名以及socket存入;
				CoopManager.coopList.put(userPo.getUserName(),
						new HashMap<String, perServer>());
				CoopManager.coopList.get(userPo.getUserName()).put(
						userPo.getUserName(), this);
			}

			break;

		case "invitepk":
			to_user = message.getToUser();
			userPo = message.getUser();
			inviteMessage = new Message(userPo);
			inviteMessage.setCommand(Message.cmd_invitedpk);
			hashm.get(to_user.getUserName()).sendMessage(inviteMessage);
			CoopManager.pkList.put(userPo.getUserName(),
					new HashMap<String, perServer>());
			CoopManager.pkList.get(userPo.getUserName()).put(
					userPo.getUserName(), this);
			break;

		case "updateinformation":
			con.setUserPO(message.getUser());
			message.setSuccess("更新成功");
			sendMessage(message);
			break;

		case "getrank":
			message.setNetRank(con.getNetSingleRankPO());
			message.setSuccess("查找_排行榜成功");
			sendMessage(message);
			break;

		case "getuser":
			message = con.getUserPO(message);
			sendMessage(message);
			break;

		case "getallusers":
			message.setallusers(con.getAllUsers());
			sendMessage(message);
			break;

		case "getcooprank":
			message.setCoopRank(con.getCoopRank());
			sendMessage(message);
			break;

		case "getolfriend":
			ArrayList<String> olfriend = new ArrayList<String>();
			message = con.getUserPO(message);
			for (String tempfriendname : message.getUser().getFriend()) {
				if (hashm.containsKey(tempfriendname)) {
					olfriend.add(tempfriendname);
				}
			}
			message.setolfriend(olfriend);
			sendMessage(message);
			break;

		default:
			// 错误信息
			message.setSuccess("本次操作失败");
			sendMessage(message);
			break;
		}

	}

	@Override
	public void run() {
		/*
		 * 开启一个服务线程
		 */
		while (true) {
			try {
				this.getCommand();
			} catch (IOException e) {
				if (user != null) {
					hashm.remove(user.getUserName());
					if (CoopManager.coopList.containsKey(user.getUserName())) {// 通知协作者房主掉线
						HashMap<String, perServer> coopHs = CoopManager.coopList
								.get(user.getUserName());
						Set<String> coopers = coopHs.keySet();
						Message message = new Message();
						message.setCommand(Message.cmd_coopbreak);
						for (String cooper : coopers) {
							if (!cooper.equals(user.getUserName())) {
								perServer perServer = coopHs.get(cooper);// 得到了所有协作用户的socket
								try {
									perServer.sendMessage(message);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
					System.out.println(user.getUserName());
				}
				break;
			}
		}

	}

}
