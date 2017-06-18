package netservice;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

import po.message.Message;
import po.rank.RankPO;
import po.user.UserPO;

public interface SocketClientService {

	/**
	 * Contact the <code><b>SocketClientService</code></b> to close.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return 
	 * 用于关闭socket.
	 */
    public void close() throws IOException;

	/**
	 * Contact the <code><b>SocketClientService</code></b> to login.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * 登陆方法
	 */
	public Message login(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to update.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * 更新个人信息
	 */
	public Message update(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to register.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * 注册方法
	 */
	public Message register(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to logOut.
	 *  
	 * @see SocketClient
	 * @param userName
	 * @return 
	 * 登出方法
	 */
	public void logOut(String userName);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to findFriends.
	 *  
	 * @see SocketClient
	 * @param friend
	 * @return Message
	 * 查找好友方法
	 */
	public Message findFriends(UserPO friend);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to addFriends.
	 *  
	 * @see SocketClient
	 * @param userName, friendName
	 * @return 
	 */
	public void addFriends(String userName,String friendName);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getRank.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Message
	 * 添加好友方法
	 */
	public Message getRank();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getUserPO.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return 
	 * 用于获取排名信息
	 */
	public Message getUserPO(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getAllUsers.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Message
	 * 用于获取用户信息
	 */
	public Message getAllUsers();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getCoopRank.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Message
	 * 用于获取合作排行信息
	 */
	public Message getCoopRank();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getfriendol.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * 用于获得在线好友列表
	 */
	public Message getfriendol(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to invitefriend.
	 *  
	 * @see SocketClient
	 * @param user, to_user
	 * @return 
	 * 用于邀请好友进行游戏
	 */
	public void invitefriend(UserPO user,UserPO to_user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getClient.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Socket
	 * 用于获取socket实例而方便保存
	 */
	public Socket getClient();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to changePassword.
	 *  
	 * @see SocketClient
	 * @param po
	 * @return 
	 * 修改密码方法
	 */
	public void changPassword(UserPO po);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to setCoopRank.
	 *  
	 * @see SocketClient
	 * @param rankpo
	 * @return Message
	 * 用于更新协作排行
	 */
	public Message setCoopRank(ArrayList<RankPO> rankpo);
	
}