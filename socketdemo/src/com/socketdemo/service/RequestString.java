package com.socketdemo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;


public class RequestString {

	static String service_code = "";
	static String source_type = "";
	static String message_code = "";
	static String workdate = "";
	static String transflag = "";
	static String lsh = "";
	static String transcode = "";
	static String account = "";
	static String deptcode = "";
	static String custid = "";
	static String seqno = "";
	static String pgup_or_pgdn = "";
	static String total_num = "";
	static String current_num = "";
	static String page_start = "";
	static String page_end = "";
	
	public static String getService_code() {
		return service_code;
	}

	public static void setService_code(String service_code) {
		RequestString.service_code = service_code;
	}

	public static String getSource_type() {
		return source_type;
	}

	public static void setSource_type(String source_type) {
		RequestString.source_type = source_type;
	}

	public static String getMessage_code() {
		return message_code;
	}

	public static void setMessage_code(String message_code) {
		RequestString.message_code = message_code;
	}

	public static String getWorkdate() {
		return workdate;
	}

	public static void setWorkdate(String workdate) {
		RequestString.workdate = workdate;
	}

	public static String getTransflag() {
		return transflag;
	}

	public static void setTransflag(String transflag) {
		RequestString.transflag = transflag;
	}

	public static String getLsh() {
		return lsh;
	}

	public static void setLsh(String lsh) {
		RequestString.lsh = lsh;
	}

	public static String getTranscode() {
		return transcode;
	}

	public static void setTranscode(String transcode) {
		RequestString.transcode = transcode;
	}

	public static String getAccount() {
		return account;
	}

	public static void setAccount(String account) {
		RequestString.account = account;
	}

	public static String getDeptcode() {
		return deptcode;
	}

	public static void setDeptcode(String deptcode) {
		RequestString.deptcode = deptcode;
	}

	public static String getCustid() {
		return custid;
	}

	public static void setCustid(String custid) {
		RequestString.custid = custid;
	}

	public static String getSeqno() {
		return seqno;
	}

	public static void setSeqno(String seqno) {
		RequestString.seqno = seqno;
	}

	public static String getPgup_or_pgdn() {
		return pgup_or_pgdn;
	}

	public static void setPgup_or_pgdn(String pgup_or_pgdn) {
		RequestString.pgup_or_pgdn = pgup_or_pgdn;
	}

	public static String getTotal_num() {
		return total_num;
	}

	public static void setTotal_num(String total_num) {
		RequestString.total_num = total_num;
	}

	public static String getCurrent_num() {
		return current_num;
	}

	public static void setCurrent_num(String current_num) {
		RequestString.current_num = current_num;
	}

	public static String getPage_start() {
		return page_start;
	}

	public static void setPage_start(String page_start) {
		RequestString.page_start = page_start;
	}

	public static String getPage_end() {
		return page_end;
	}

	public static void setPage_end(String page_end) {
		RequestString.page_end = page_end;
	}

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
			UtilXML.addContent("sys-header", root);
			Element elem_sys = new Element("data");
			elem_sys.setAttribute("name", "SYS_HEAD");
			UtilXML.addContent(doc, "sys-header", elem_sys);
			addHeader(doc, "sys");
		}
		if (appFlag) {
			UtilXML.addContent("app-header", root);
			Element elem_app = new Element("data");
			elem_app.setAttribute("name", "APP_HEAD");
			UtilXML.addContent(doc, "app-header", elem_app);
			UtilXML.addContent(doc, "app-header/data", "struct");
			addHeader(doc, "app");
		}
		if (localFalg) {
			UtilXML.addContent("local-header", root);
		}
		Element bodyElem = new Element("body");
		root.addContent(bodyElem);
		SetEsbField esb = null;
		try {
			esb = new SetEsbField(inType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addBodyElement(bodyElem, inParam, esb.getDataList());
		String buffer = UtilXML.outputter(doc, UtilXML.ENCODE_UTF8);
		String lengthStr = String.valueOf(buffer.getBytes().length);
		StringBuffer zzhead = new StringBuffer();
		for (int i = 0; i < 10 - lengthStr.length(); i++) {
			zzhead.append("0");
		}
		zzhead.append(lengthStr).append(buffer);
		return new StringBuffer(buffer);
	}
	
	/**
	 * 添加请求报文头
	 * @param doc
	 * @param type
	 */
	private static void addHeader(Document doc, String type) {
		if ("sys".equals(type)) {
			Element elem = null;
			Element file = null;
			
			elem = new Element("data");
			elem.setAttribute("name", "lsh");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", lsh);
			
			elem = new Element("data");
			elem.setAttribute("name", "SERVICE_CODE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", service_code);
			
			elem = new Element("data");
			elem.setAttribute("name", "SOURCE_TYPE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", source_type);
			
			elem = new Element("data");
			elem.setAttribute("name", "SERVICE_CODE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", service_code);
			
			elem = new Element("data");
			elem.setAttribute("name", "MESSAGE_CODE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", message_code);
			
			elem = new Element("data");
			elem.setAttribute("name", "transcode");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", transcode);
			
			elem = new Element("data");
			elem.setAttribute("name", "account");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", account);
			
			elem = new Element("data");
			elem.setAttribute("name", "deptcode");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", deptcode);
			
			elem = new Element("data");
			elem.setAttribute("name", "workdate");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", workdate);
			
			elem = new Element("data");
			elem.setAttribute("name", "transflag");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", transflag);
			
			elem = new Element("data");
			elem.setAttribute("name", "custid");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", custid);
			
			elem = new Element("data");
			elem.setAttribute("name", "SEQ_NO");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "12", seqno);
		}
		
		if ("app".equals(type)) {
			Element elem_app = null;
			Element file_app = null;
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "PGUP_OR_PGDN");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "12", pgup_or_pgdn);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "TOTAL_NUM");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "12", total_num);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "CURRENT_NUM");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "12", current_num);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "PAGE_START");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "12", page_start);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "PAGE_END");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "12", page_end);
		}
	}
	
	/**
	 * 添加请求报文体
	 */
	private static void addBodyElement(Element parentElem, Map inParam, List<DataElement> dataList) {
		Set set = inParam.entrySet();
		Iterator ite = set.iterator();
		String sScale = "";
		String sType = "";
		String sLength = "";
		while (ite.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) ite.next();
			Element elem = new Element("data");
			elem.setAttribute("name", (String) mapEntry.getKey());
			parentElem.addContent(elem);
			List<DataElement> currEle = null;
			for (DataElement ele : dataList) {
				if (ele.getName().equalsIgnoreCase((String) mapEntry.getKey())) {
					sScale = ele.getScale();
					sType = ele.getType();
					sLength = String.valueOf(ele.getLength());
					currEle = ele.getList();
				}
			}
			if (sType.equalsIgnoreCase("string") || sType.equalsIgnoreCase("double")) {
				Element file = new Element("field");
				file.setAttribute("scale", sScale);
				file.setAttribute("type", sType.toLowerCase());
				file.setAttribute("length", sLength);
				file.setText(String.valueOf(mapEntry.getValue()));
				elem.addContent(file);
			} else if (sType.equalsIgnoreCase("array")) {
				Element arrayElem = new Element("array");
				elem.addContent(arrayElem);
				ArrayList dataList_a = (ArrayList) mapEntry.getValue();
				if (dataList_a != null && dataList_a.size() > 0) {
					for(int i=0;i<dataList_a.size();i++){
						Map dataMap = (Map)dataList_a.get(i);
						Element	strutElem = new Element("struct");
						arrayElem.addContent(strutElem);
						addBodyElement(strutElem,dataMap,currEle);
					}
				}
			}
		}
	}
	
	
	public String getErrMsg(Document doc){
		Element elem = UtilXML.childNode(doc,"sys-header/data/struct","name","RET","array");
		List arrayElemList=null;
		if(elem!=null)	arrayElemList = elem.getChildren();
		else return "";	
		String retMsgs="";	
		for (int j = 0; j < arrayElemList.size(); j++) {
			Element documentStruct = (Element) arrayElemList.get(j);
			String retCode="",retMsg="";
			List subElemList = documentStruct.getChildren();
			for(int i=0;i<subElemList.size();i++){
				Element el = (Element) subElemList.get(i);	
				String name=el.getAttribute("name").getValue();
				if("returncode".equals(name)) {
					Element em = el.getChild("field");
					retCode=em.getText();
				}else if("returnmsg".equals(name)){
					Element em = el.getChild("field");
					retMsg=em.getText();
				}
			}
			retMsgs = retMsgs+retCode+":"+retMsg+";  ";
		}
		return retMsgs;
	}
	
	
	
	
}//end
