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
	 * ���ڹر�socket.
	 */
    public void close() throws IOException;

	/**
	 * Contact the <code><b>SocketClientService</code></b> to login.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * ��½����
	 */
	public Message login(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to update.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * ���¸�����Ϣ
	 */
	public Message update(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to register.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * ע�᷽��
	 */
	public Message register(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to logOut.
	 *  
	 * @see SocketClient
	 * @param userName
	 * @return 
	 * �ǳ�����
	 */
	public void logOut(String userName);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to findFriends.
	 *  
	 * @see SocketClient
	 * @param friend
	 * @return Message
	 * ���Һ��ѷ���
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
	 * ��Ӻ��ѷ���
	 */
	public Message getRank();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getUserPO.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return 
	 * ���ڻ�ȡ������Ϣ
	 */
	public Message getUserPO(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getAllUsers.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Message
	 * ���ڻ�ȡ�û���Ϣ
	 */
	public Message getAllUsers();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getCoopRank.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Message
	 * ���ڻ�ȡ����������Ϣ
	 */
	public Message getCoopRank();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getfriendol.
	 *  
	 * @see SocketClient
	 * @param user
	 * @return Message
	 * ���ڻ�����ߺ����б�
	 */
	public Message getfriendol(UserPO user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to invitefriend.
	 *  
	 * @see SocketClient
	 * @param user, to_user
	 * @return 
	 * ����������ѽ�����Ϸ
	 */
	public void invitefriend(UserPO user,UserPO to_user);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to getClient.
	 *  
	 * @see SocketClient
	 * @param 
	 * @return Socket
	 * ���ڻ�ȡsocketʵ�������㱣��
	 */
	public Socket getClient();
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to changePassword.
	 *  
	 * @see SocketClient
	 * @param po
	 * @return 
	 * �޸����뷽��
	 */
	public void changPassword(UserPO po);
	
	/**
	 * Contact the <code><b>SocketClientService</code></b> to setCoopRank.
	 *  
	 * @see SocketClient
	 * @param rankpo
	 * @return Message
	 * ���ڸ���Э������
	 */
	public Message setCoopRank(ArrayList<RankPO> rankpo);
	
}