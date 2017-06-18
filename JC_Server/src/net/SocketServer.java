package net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * <code><b>SocketServer</b></code> contains functions about service.
 * 
 * @author Hongyu Qu
 */
public class SocketServer {

	private ServerSocket ss;
	static HashMap<String, perServer> hashm;

	public SocketServer() throws IOException {
		String result = readFile("file/url.txt");
		int port = Integer.parseInt(result.split("%")[1]);	
		ss = new ServerSocket(port);
		hashm = new HashMap<String, perServer>();
		while (true) {
			Socket socket = ss.accept();
			perServer ps = new perServer(socket, hashm);
			new Thread(ps).start();
		}
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

	public static void main(String[] args) {
		try {
			new SocketServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
