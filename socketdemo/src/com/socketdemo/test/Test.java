package com.socketdemo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.socketdemo.SocketClient;

public class Test {

	public static void main(String[] args) {
		SocketClient client = new SocketClient("TEST");
		String transcode = "TEST";
		
		//请求添加报文体信息
		Map<String, Object> inParam = new HashMap<String, Object>();
		inParam.put("name","zs");  
		inParam.put("age",25); 
		inParam.put("marriage","22");
		inParam.put("sex","3");    	
		inParam.put("income",5500); 

		ArrayList<Map<String, String>> boysArrayList = new ArrayList<>();
		Map<String, String> boyInfoMap_0 = new HashMap<String, String>();
		Map<String, String> boyInfoMap_1 = new HashMap<String, String>();
		boyInfoMap_0.put("name", "cf");
		boyInfoMap_0.put("birthday", "1937-04-19");
		boyInfoMap_0.put("stuid", "20130090114");
		boyInfoMap_1.put("name", "lx");
		boyInfoMap_1.put("birthday", "1949-10-01");
		boyInfoMap_1.put("stuid", "33333333333");
		boysArrayList.add(boyInfoMap_0);
		boysArrayList.add(boyInfoMap_1);
		inParam.put("boys",boysArrayList);
		
		try {
			client.getResult(inParam, transcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
