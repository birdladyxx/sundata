package com.socketdemo.server;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.socketdemo.service.ResponseString;
import com.socketdemo.service.SocketServiceAction;

public class TestServer implements SocketServiceAction {

	Map<String, Object> bodymap = new HashMap<String, Object>();
	Map<String, Object> inParam = new HashMap<String, Object>();
	String subTemp = "";
	
	public TestServer(Map<String, Object> bodymap) {
		this.bodymap = bodymap;
	}

	protected String getTradeCode() {
		return "TEST";
	}
	
	/**
	 * 执行服务
	 */
	protected void executeService() {
		
		try {
			// 服务功能逻辑
			Map<String, Object> bodyDataMap = bodymap;
			String name = (String) bodyDataMap.get("name");
			System.out.println("大哥姓名：" + name);
			ArrayList<Map<String, String>> boyInfoMap = (ArrayList<Map<String, String>>) bodyDataMap.get("boys");
			for (Map<String, String> map : boyInfoMap) {
				System.out.println("姓名：" + map.get("name"));
				System.out.println("生日：" + map.get("birthday"));
				System.out.println("学生号" + map.get("stuid"));
			}
			// (略)
			inParam.put("returncode", "1");
			inParam.put("returnmsg", "success");
			inParam.put("score", "99");
		} catch (Exception e) {
			inParam.put("returncode", "0");
			inParam.put("returnmsg", "fail");
			inParam.put("score", "0");
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理响应
	 * @return
	 */
	protected String handleResponse() {
		
		// 设置响应报文头
		ResponseString rps = new ResponseString();		
		ResponseString.setSret_status(((String)inParam.get("returncode")).equals("1") ? "S":"F");//S成功  F失败
		ResponseString.setSbranch_id("");
		ResponseString.setSsource_branch_no("000000");
		ResponseString.setSdest_branch_no("");
		ResponseString.setSmessage_type("");
		ResponseString.setSmessage_code("BCCESS002");
		ResponseString.setSret_code("00000"+(String)inParam.get("returncode"));
		ResponseString.setSret_msg((String) inParam.get("returnmsg"));
		ResponseString.setSseq_no(String.valueOf(System.currentTimeMillis()));
		// 响应报文
		// 参数1:响应体数据MAP；参数2：报文响应体配置文件节点名称；参数3、	参数4、参数5,设置报文头格式标志
		StringBuffer response = rps.getFeedbackStr(inParam, "TEST_RCV", true, false, false);	
		subTemp=new String(response.toString());		
		return subTemp;
		
	}
	
	@Override
	public String execute() throws Exception {
		executeService();
		String respStr = handleResponse();
		return respStr;
	}

}
