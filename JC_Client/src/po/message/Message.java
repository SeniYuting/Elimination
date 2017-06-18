package po.message;

import java.io.Serializable;
import java.util.ArrayList;

import po.rank.NetSingleRankPO;
import po.rank.RankPO;
import po.user.UserPO;
import controller.game.ReqResult;
import controller.game.Tile;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 用于网络通信的信息单元, 此类仅用于登陆,注册等基本功能. 可被对战信息,协作信息继承达成其他通信目的.
	 */
	UserPO user;// 记录一条消息中的发起用户(或者登陆时的待验证用户)
	UserPO to_user;
	String messagesuccess;// 记录消息的名称
	String messagecommand;// 记录消息所要完成的行为
	String userName;
	String friendName;
	ArrayList<UserPO> allUsers;
	Tile t1, t2;
	private int score;
	private int maxCombo;
	public static String cmd_login = "login";
	public static String cmd_logout = "logout";
	public static String cmd_listen = "listen";
	public static String cmd_register = "register";
	public static String cmd_changepw = "changepw";
	public static String cmd_findfriends = "findfriends";
	public static String cmd_addfriends = "addfriends";
	public static String cmd_update = "updateinformation";
	public static String cmd_getrank = "getrank";
	public static String cmd_getallusers = "getallusers";
	public static String cmd_getcooprank = "getcooprank";
	public static String cmd_getolfriend = "getolfriend";
	public static String cmd_invitepk = "invitepk";// 邀请对战
	public static String cmd_invitedpk = "invitedpk";// 被邀请对战
	public static String cmd_rejectdpk = "rejectpk";// 拒绝对战邀请
	public static String cmd_acceptpk = "acceptpk";// 接受对战邀请
	public static String cmd_pkstart = "pkstart";// 对战开始
	public static String cmd_pkfinish = "pkfinish";// 对战结束
	public static String cmd_invitedplgame = "invitedplgame";// 被邀请协作
	public static String cmd_invitplgame = "invitplgame";// 邀请协作
	public static String cmd_acceptplgame = "acceptplgame";// 同意协作邀请
	public static String cmd_rejectplgame = "rejectplgame";// 拒绝协作邀请
	public static String cmd_coopstart = "coopstart";// 协作开始
	public static String cmd_coopinvalid = "coopinvalid";// 协作失效
	public static String cmd_coopfinish = "coopfinish";// 协作结束
	public static String cmd_coopbreak = "coopbreak";// 协作中断
	public static String cmd_swap = "swap";// 交换
	public static String cmd_useShark = "useShark";// 鲨鱼导弹
	public static String cmd_clickprops = "clickprops";// 道具
	public static String cmd_updategame = "updategame";// 服务器更新游戏面板
	public static String cmd_tobefriend = "tobefriend";// 被请求加为好友
	public static String cmd_lock = "lock";// 对战时的lock
	public static String cmd_SetCoopRank = "setcooprank";
	ArrayList<NetSingleRankPO> nsrp;
	ArrayList<RankPO> coopRank;
	ArrayList<Tile> tileList;
	ArrayList<String> olfriend;
	ArrayList<ReqResult> reqList;
	public boolean isUp,isChain,isShark;

	public Message(UserPO user) {
		this.user = user;
		messagesuccess = "";
	}

	public Message() {
		messagesuccess = "";
	}

	public Message(String userName, String friendName) {
		this.userName = userName;
		this.friendName = friendName;
	}

	public Message(int score, int maxCombo) {
		this.setScore(score);
		this.setMaxCombo(maxCombo);
	}

	public Message(UserPO user, UserPO to_user, Tile t1, Tile t2) {
		this.user = user;
		this.to_user = to_user;
		this.t1 = t1;
		this.t2 = t2;
	}

	public Message(ArrayList<Tile> tileList) {
		this.tileList = tileList;
	}

	public Message(ArrayList<ReqResult> reqList, int i) {
		this.reqList = reqList;
	}

	public Message(UserPO user, Tile t1, Tile t2) {
		this.user = user;
		this.t1 = t1;
		this.t2 = t2;
	}

	public Message(UserPO user, UserPO to_user) {
		this.user = user;
		this.to_user = to_user;
	}

	public void setTarget(UserPO user) {
		this.to_user = user;
	}

	public void setCommand(String command) {
		this.messagecommand = command;
	}

	public String getCommand() {
		return this.messagecommand;
	}

	public UserPO getUser() {
		return user;
	}

	public ArrayList<UserPO> getAllUsers() {
		return allUsers;
	}

	public void setUser(UserPO user) {
		this.user = user;
	}

	public void setSuccess(String success) {
		this.messagesuccess = success;
	}

	public String isSuccess() {
		return messagesuccess;
	}

	public ArrayList<NetSingleRankPO> getNetRank() {
		return nsrp;
	}

	public void setNetRank(ArrayList<NetSingleRankPO> nsrp) {
		this.nsrp = nsrp;
	}

	public void setallusers(ArrayList<UserPO> allUsers) {
		// TODO Auto-generated method stub
		this.allUsers = allUsers;
	}

	public ArrayList<UserPO> getallusers() {
		return this.allUsers;
	}

	public ArrayList<RankPO> getCoopRank() {
		return this.coopRank;
	}

	public void setCoopRank(ArrayList<RankPO> coopRank) {
		this.coopRank = coopRank;
	}

	public void setolfriend(ArrayList<String> olfriend) {
		this.olfriend = olfriend;
	}

	public ArrayList<String> getolfriend() {
		return this.olfriend;
	}

	public UserPO getToUser() {
		return this.to_user;
	}

	public Tile getTile1() {
		return t1;
	}

	public Tile getTile2() {
		return t2;
	}

	public ArrayList<Tile> getTileList() {
		return tileList;
	}

	public String getUserName() {
		return userName;
	}

	public String getFriendName() {
		return friendName;
	}

	public ArrayList<ReqResult> getReqList() {
		return reqList;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMaxCombo() {
		return maxCombo;
	}

	public void setMaxCombo(int maxCombo) {
		this.maxCombo = maxCombo;
	}

}
