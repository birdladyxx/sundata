package com.socketdemo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author qinzh
 *
 */
public class SocketListener implements ServletContextListener {

	private SocketServer socketThread;

	/**
	 * 销毁，当Servlet容器终止web应用时调用该方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (socketThread != null && socketThread.isInterrupted()) {
			// 关闭线程
			socketThread.closeSocketServer();
			// 中断线程
			socketThread.interrupt();
		}
	}

	/**
	 * 初始化，当Servlet容器启动web应用时调用该方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (socketThread == null) {
			socketThread = new SocketServer();
			socketThread.start();
		}
	}
	
}
