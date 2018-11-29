package com.socketdemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket服务端
 * @author qinzh
 *
 */
public class SocketServer extends Thread {

	private static int serverPort = 17110;// 服务端端口号
	private ServerSocket serverSocket = null;
	private int nThreads = 16;// 最大线程数
	Socket socket = null;
	
	public SocketServer() {
		try {
			if (serverSocket == null) {
				this.serverSocket = new ServerSocket(serverPort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		ExecutorService es = Executors.newFixedThreadPool(nThreads);
		while (!SocketServer.interrupted()) {
			try {
				socket = serverSocket.accept();
				SocketServerThread sst = new SocketServerThread();
				sst.setSocket(socket);
				es.execute(sst);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭当前线程
	 */
	public void closeSocketServer() {
		try {
			if (null != serverSocket && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}//end
