package com.socketdemo.service;

import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

public class RequestString {

	static String lsh = "";
	static String transcode = "";
	static String account = "";
	static String deptcode = "";
	static String custid = "";
	static String seqno = "";
	
	/**
	 * 
	 * @param inParam
	 * @param inType
	 * @param sysFlag
	 * @param appFlag
	 * @param localFalg
	 * @return
	 */
	public StringBuffer getInputStr(Map<String, Object> inParam, String inType, Boolean sysFlag, Boolean appFlag, Boolean localFalg) {
		Element root = new Element("service");
		Document doc = new Document(root);
		if (sysFlag) {
			
		}
		return null;
	}
}
