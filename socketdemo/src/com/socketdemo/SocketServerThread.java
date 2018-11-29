package com.socketdemo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.socketdemo.server.TestServer;
import com.socketdemo.service.Analysis;
import com.socketdemo.service.SetEsbField;
import com.socketdemo.service.SocketServiceAction;
import com.socketdemo.service.UtilXML;

public class SocketServerThread implements Runnable {

	int iTimes = 1;
	Socket socket = null;
	Document xDocument = null;
	String sThreadNo;// 交易码
	String respMessage = "";// 响应报文
	Map<String, Object> bodymap = new HashMap<String, Object>();// 请求报文字段数据
	SocketServiceAction ssa;
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream is = null;
		OutputStream os = null;
		try {
			System.out.println("第"+iTimes+"次通讯开始！");
			SAXBuilder b = new SAXBuilder();
			is = socket.getInputStream();
			byte[] lenth = new byte[10];
			// 读取请求报文长度
			is.read(lenth);
			// 读取请求报文
			Reader reader = new InputStreamReader(is);
			char chars[] = new char[1024];
			int len;
			StringBuffer sb = new StringBuffer();
			String temp;
			while ((len = reader.read(chars)) != -1) {
				temp = new String(chars, 0, len);
				temp = new String(temp.getBytes(), "UTF-8");
				sb.append(temp);
//				System.out.println("循环报文：" + sb.toString());
				// 设置循环结束点，否则输入流会一直阻塞。
				if (sb.toString().length() == Integer.parseInt(new String(lenth))) {
					break;
				}
			}
			System.out.println("请求报文长度：" + Integer.parseInt(new String(lenth)));
			String sIncept = sb.toString();
			System.out.println("请求报文：\n" + sIncept);
			xDocument  = b.build(new StringReader(sIncept));
			Element elem = UtilXML.childNode(xDocument, "sys-header/data/struct", "name", "transcode", "field");
			sThreadNo = elem.getText().trim();
			System.out.println("交易码：" + sThreadNo);
			List<Element> elemList = UtilXML.getElementlList(xDocument, "body");
			
			// 处理业务逻辑
			if ("TEST".equals(sThreadNo)) {
				SetEsbField esb = new SetEsbField("TEST");
				bodymap = Analysis.enCodeElemList(elemList, esb.getDataList());
				ssa = new TestServer(bodymap);
			} else {
				System.out.println("没有此交易！");
			}
			
			if (ssa != null) {
				respMessage = ssa.execute();
				respMessage = new String(respMessage.getBytes(), "UTF-8");
				System.out.println("响应报文：\n" + respMessage);
			}
			byte[] resbt = respMessage.getBytes("UTF-8");
			Writer writer = new OutputStreamWriter(socket.getOutputStream());
			int length = resbt.length;
			String str = String.valueOf(length);
			String lengthstr = "";
			for (int i = 0; i < 10 - str.length(); i++) {
				lengthstr += "0";
			}
			lengthstr += str;
			writer.write(lengthstr + respMessage);
			writer.flush();
			writer.close();
			reader.close();
			System.out.println("第" + iTimes + "次通讯结束！");
			iTimes++;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				os = socket.getOutputStream();
				byte[] resbt = respMessage.getBytes();
				byte[] length = getByteLenth(resbt);
				os.write(length);
				os.flush();
				os.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	// 将长度信息拼接成十位的字节数组 例："0000000125"
	public byte[] getByteLenth(byte[] reqbt){
		reqbt = Integer.toString(reqbt.length).getBytes();
		byte[] length = new byte[10];
		for(int i = 0;i<10; i++){
			if(i >= 10-reqbt.length){
				length[i] = reqbt[reqbt.length -(10-i)];
			}else{
				length[i] = '0';
			}
		}
		return length;
	}
}
