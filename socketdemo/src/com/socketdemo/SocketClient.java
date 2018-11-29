package com.socketdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.socketdemo.service.RequestString;

/**
 * socket客户端
 * @author qinzh
 *
 */
public class SocketClient {

	private String serviceName = "";
	public SocketClient(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object getResult(Object object, String transcode) throws Exception {
		if (object == null) {
			throw new Exception("请求服务端时，传入的对象为空！");
		}
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		
		// 本地服务端地址和端口
		String ip = "127.0.0.1";
		String port = "17110";
		
		try {
			if ("TEST".equalsIgnoreCase(serviceName)) {
				socket = new Socket(ip,Integer.parseInt(port));
				os = socket.getOutputStream();
				String req = "";
				
				// 请求添加报文头信息
				RequestString rs= new RequestString();		
				
				RequestString.setCustid("1111");
				RequestString.setTranscode("TEST");
				RequestString.setAccount("2222222");
				RequestString.setDeptcode("10001");
				RequestString.setWorkdate("2017-08-23");
				
				Map<String, Object> inParam = new HashMap<String, Object>();
				inParam = (Map<String, Object>) object;
				
				req = rs.getInputStr(inParam, "TEST", true, false, false).toString();
				System.out.println("请求报文：\n" + req);
				
				byte[] reqbt = req.getBytes("UTF-8");
				byte[] lenth = getByteLenth(reqbt);
				os.write(lenth);
				os.write(reqbt);
				os.flush();

				/*--------请求发送完毕---------*/
				is = socket.getInputStream();
				// 前十位为总长度
				lenth = new byte[10];
				is.read(lenth);
				

				// 使用相应长度的字节接受信息
				byte[] res = new byte[Integer.parseInt(new String(lenth))];
				is.read(res);
				
				// 转化为字符类型
				System.out.println("响应报文：\n" + new String(res,"UTF-8"));
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			// 关闭资源
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return transcode;
	}
	
	/**
	 * 获取十位的字节数组，并存储请求报文的长度。
	 */
	public static byte[] getByteLenth(byte[] reqbt){
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
}//end
