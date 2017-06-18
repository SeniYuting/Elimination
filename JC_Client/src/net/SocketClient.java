package net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import netservice.SocketClientService;
import po.message.Message;
import po.rank.RankPO;
import po.user.UserPO;

/**
 * <code><b>SocketClient</b></code> contains functions about service.
 * 
 * @author Hongyu Qu
 */
public class SocketClient implements SocketClientService {
	/*
	 * �ͻ���:���ڴ���socket,�ʹ�����Ϣ. ����һ���ͻ�ֻ��Ҫһ��socket, �ʽ��ͻ��˿����ʹ�����������һ������. �ͷ�������һ��
	 * ,�쳣��δ����.
	 */

	Socket client;

	ObjectOutputStream out;
	ObjectInputStream in;

	public SocketClient() throws UnknownHostException, IOException {
		String result = readFile("file/url.txt");
		String ipAddr = result.split("%")[0];
		int port = Integer.parseInt(result.split("%")[1]);

		client = new Socket(ipAddr, port);
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());

	}

	public String readFile(String fileName) {
		File file = new File(fileName);
		String result = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			result = bufr.readLine();
			bufr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	void sendMessage(Message message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void receiveMessage() throws ClassNotFoundException, IOException {
		// ��ʱûʲô��,�Ժ���ܻ�嵽ĳ���߳���������.
		in.readObject();
	}

	public void close() throws IOException {
		this.client.close();
	}

	@SuppressWarnings("static-access")
	public Message login(UserPO user) {
		Message message = new Message(user);
		message.setCommand(message.cmd_login);
		try {
			this.sendMessage(message);
			Message m = (Message) this.in.readObject();
			return m;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public Message update(UserPO user) {

		Message message = new Message(user);
		message.setCommand(message.cmd_update);
		try {
			this.sendMessage(message);

			return (Message) this.in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Socket getClient() {
		return client;
	}

	@SuppressWarnings("static-access")
	@Override
	public Message register(UserPO user) {
		Message message = new Message(user);
		message.setCommand(message.cmd_register);
		try {
			this.sendMessage(message);
			return (Message) this.in.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void logOut(String userName) {
		// �����ж�ָ��...
		Message message = new Message(new UserPO(userName));
		message.setCommand(Message.cmd_logout);
		System.out.println("logout");
		sendMessage(message);
	}

	@Override
	public Message findFriends(UserPO friend) {
		/*
		 * ����Ӧ����ô���...��˭����message��Ϣ Ӧ�ð�������Ϣ��:ԭuser,����user,��Ϣ����...
		 */
		Message message = new Message();

		return message;
	}

	@Override
	public void addFriends(String userName, String friendName) {
		Message message = new Message(userName, friendName);
		message.setCommand(Message.cmd_addfriends);
		sendMessage(message);
	}

	public Message getRank() {

		Message message = new Message();
		message.setCommand(Message.cmd_getrank);
		message.setSuccess("");
		try {
			this.sendMessage(message);
			return (Message) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Message getUserPO(UserPO user) {
		Message message = new Message(user);
		message.setCommand("getuser");
		try {
			this.sendMessage(message);
			return (Message) this.in.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Message getAllUsers() {

		Message message = new Message();
		message.setCommand(Message.cmd_getallusers);
		message.setSuccess("");
		try {
			this.sendMessage(message);
			return (Message) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Message getCoopRank() {

		Message message = new Message();
		message.setCommand(Message.cmd_getcooprank);
		message.setSuccess("");
		try {
			this.sendMessage(message);
			return (Message) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Message getfriendol(UserPO user) {

		Message message = new Message(user);
		message.setCommand(Message.cmd_getolfriend);
		message.setSuccess("");
		this.sendMessage(message);
		try {
			return (Message) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void invitefriend(UserPO user, UserPO to_user) {
		Message message = new Message(user, to_user);
		message.setCommand(Message.cmd_invitplgame);
		this.sendMessage(message);
	}

	public void changPassword(UserPO po) {
		Message message = new Message(po);
		message.setCommand(Message.cmd_changepw);
		this.sendMessage(message);
	}

	@Override
	public Message setCoopRank(ArrayList<RankPO> rankpo) {

		Message message = new Message();
		message.setCommand(Message.cmd_SetCoopRank);
		message.setSuccess("");
		message.setCoopRank(rankpo);
		this.sendMessage(message);
		try {
			return (Message) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
