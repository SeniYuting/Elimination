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
	 * ��������ͨ�ŵ���Ϣ��Ԫ, ��������ڵ�½,ע��Ȼ�������. �ɱ���ս��Ϣ,Э����Ϣ�̳д������ͨ��Ŀ��.
	 */
	UserPO user;// ��¼һ����Ϣ�еķ����û�(���ߵ�½ʱ�Ĵ���֤�û�)
	UserPO to_user;
	String messagesuccess;// ��¼��Ϣ������
	String messagecommand;// ��¼��Ϣ��Ҫ��ɵ���Ϊ
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
	public static String cmd_invitepk = "invitepk";// �����ս
	public static String cmd_invitedpk = "invitedpk";// �������ս
	public static String cmd_rejectdpk = "rejectpk";// �ܾ���ս����
	public static String cmd_acceptpk = "acceptpk";// ���ܶ�ս����
	public static String cmd_pkstart = "pkstart";// ��ս��ʼ
	public static String cmd_pkfinish = "pkfinish";// ��ս����
	public static String cmd_invitedplgame = "invitedplgame";// ������Э��
	public static String cmd_invitplgame = "invitplgame";// ����Э��
	public static String cmd_acceptplgame = "acceptplgame";// ͬ��Э������
	public static String cmd_rejectplgame = "rejectplgame";// �ܾ�Э������
	public static String cmd_coopstart = "coopstart";// Э����ʼ
	public static String cmd_coopinvalid = "coopinvalid";// Э��ʧЧ
	public static String cmd_coopfinish = "coopfinish";// Э������
	public static String cmd_coopbreak = "coopbreak";// Э���ж�
	public static String cmd_swap = "swap";// ����
	public static String cmd_useShark = "useShark";// ���㵼��
	public static String cmd_clickprops = "clickprops";// ����
	public static String cmd_updategame = "updategame";// ������������Ϸ���
	public static String cmd_tobefriend = "tobefriend";// �������Ϊ����
	public static String cmd_lock = "lock";// ��սʱ��lock
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
