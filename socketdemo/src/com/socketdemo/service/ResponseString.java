package com.socketdemo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdom2.Document;
import org.jdom2.Element;


public class ResponseString {
	static String Sret_status="";
	static String Sbranch_id="";
	static String Ssource_branch_no="";
	static String Sdest_branch_no="";
	static String Smessage_type="";
	static String Smessage_code="";
	static String Sret_code="";
	static String Sret_msg="";
	static String Sseq_no="";
	
	static String Spgup_or_pgdn="1";
	static String Stotal_num="-1";
	static String Scurrent_num="-1";
	static String Spage_start="0";
	static String Spage_end="0";
	
	
	/**
	 * 组装反馈报文
	 * @param inParam 响应体数据
	 * @param inType  响应体类型
	 * @param sysflag，appflag，localflag 报文头格式控制标志
	 * @throws Exception 
	 */
	public StringBuffer getFeedbackStr(Map inParam,String inType,Boolean sysflag,Boolean appflag,Boolean localflag){
				//父节点
				Element root = new Element("service");
				//生成Document类
				Document doc = new Document(root);
				//子节点
				if(sysflag){
					UtilXML.addContent("sys-header", root);
					Element elem_sys = new Element("data");
					elem_sys.setAttribute("name", "SYS_HEAD");
					UtilXML.addContent(doc, "sys-header", elem_sys);
					UtilXML.addContent(doc, "sys-header/data", "struct");
					addHeader(doc,"sys");
				}
				if(appflag){
					UtilXML.addContent("app-header", root);
					Element elem_app = new Element("data");
					elem_app.setAttribute("name", "APP_HEAD");
					UtilXML.addContent(doc, "app-header", elem_app);
					UtilXML.addContent(doc, "app-header/data", "struct");
					addHeader(doc,"app");
				}
				if(localflag){
					UtilXML.addContent("local-header", root);
				}		
				
				Element bodyElem = new Element("body");
				root.addContent(bodyElem);
				//报文体
				SetEsbField esb = null;
				try {
					esb = new SetEsbField(inType);
				} catch (Exception e) {
					e.printStackTrace();
				}
				addBodyElement(bodyElem, inParam,esb.getDataList());
				String buffer = UtilXML.outputter(doc, UtilXML.ENCODE_UTF8);
				return new StringBuffer( buffer);
	}
	
	/**
	 * 组装报文头
	 * @param doc
	 */
	private static void addHeader(Document doc,String type) {
		if(type.equals("sys")){
			Element elem = new Element("data");
			elem.setAttribute("name", "RET_STATUS");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			Element file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "1", Sret_status);
			
			elem = new Element("data");
			elem.setAttribute("name", "BRANCH_ID");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "6", Sbranch_id);
			
			elem = new Element("data");
			elem.setAttribute("name", "SOURCE_BRANCH_NO");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "6", Ssource_branch_no);
			
			elem = new Element("data");
			elem.setAttribute("name", "DEST_BRANCH_NO");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "6", Sdest_branch_no);
			
			elem = new Element("data");
			elem.setAttribute("name", "MESSAGE_TYPE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "4", Smessage_type);
			
			elem = new Element("data");
			elem.setAttribute("name", "MESSAGE_CODE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem, file, "0", "string", "6", Smessage_code);

			elem = new Element("data");
			elem.setAttribute("name", "TRAN_DATE");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
			UtilXML.addAttribute(elem, file, "0", "string", "8", format.format(new java.util.Date()));			

			elem = new Element("data");
			elem.setAttribute("name", "TRAN_TIMESTAMP");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("HHmmssSSS");
			UtilXML.addAttribute(elem, file, "0", "string", "9", dateFormat.format(new java.util.Date()));
			
			elem = new Element("data");
			elem.setAttribute("name", "RET");
			Element	arrayElem = new Element("array");
			elem.addContent(arrayElem);
			Element	strutElem = new Element("struct");
			arrayElem.addContent(strutElem);
			
			Element elem_a = new Element("data");
			elem_a.setAttribute("name", "RET_CODE");
			Element file_a = new Element("field");
			UtilXML.addAttribute(elem_a,file_a,"0","string","6",Sret_code);	
			strutElem.addContent(elem_a);
			
			elem_a = new Element("data");
			elem_a.setAttribute("name", "RET_MSG");
			file_a = new Element("field");
			UtilXML.addAttribute(elem_a,file_a,"0","string","512",Sret_msg);			
			strutElem.addContent(elem_a);
			
			UtilXML.addContent(doc, "sys-header/data/struct", elem);		

			elem = new Element("data");
			elem.setAttribute("name", "SEQ_NO");
			UtilXML.addContent(doc, "sys-header/data/struct", elem);
			file = new Element("field");
			UtilXML.addAttribute(elem,file,"0","string","16","");
		}
		
		if(type.equals("app")){
			Element elem_app = new Element("data");
			elem_app.setAttribute("name", "PGUP_OR_PGDN");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			Element file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "1", Spgup_or_pgdn);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "TOTAL_NUM");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "2", Stotal_num);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "CURRENT_NUM");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "2", Scurrent_num);
			
			elem_app = new Element("data");
			elem_app.setAttribute("name", "PAGE_START");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "1", Spage_start);
			
			elem_app = new Element("app");
			elem_app.setAttribute("name", "PAGE_END");
			UtilXML.addContent(doc, "app-header/data/struct", elem_app);
			file_app = new Element("field");
			UtilXML.addAttribute(elem_app, file_app, "0", "string", "1", Spage_end);
		}		
	}
	

	/**
	 * 组装 报文体 service/body
	 * @param parentElem
	 * @param inParam
	 * @param dataList
	 */
	private static void addBodyElement(Element parentElem,Map inParam,List<DataElement> dataList){
		Set set = inParam.entrySet();
		Iterator ite = set.iterator();
		String sScale="";
		String sType="";
		String sLength="";
		while(ite.hasNext()){			
			Map.Entry mapEntry = (Map.Entry)ite.next();
			Element	elem = new Element("data");
			elem.setAttribute("name", (String)mapEntry.getKey());
			parentElem.addContent(elem);
			
			// 列表
			List<DataElement> currEle = null;
			
			for (DataElement ele : dataList) {
				if(ele.getName().equalsIgnoreCase(String.valueOf(mapEntry.getKey()))){
					sScale = ele.getScale();
					sType = ele.getType();
					sLength = String.valueOf(ele.getLength());	
				
					currEle = ele.getList();
					break;
				}
				
			}
			
			
			if(sType.equalsIgnoreCase("string") || sType.equalsIgnoreCase("double") ){
				Element file = new Element("field");
				file.setAttribute("scale", sScale);
				file.setAttribute("type", sType.toLowerCase());
				file.setAttribute("length", sLength);
				file.setText(String.valueOf(mapEntry.getValue()));
				elem.addContent(file);
			}else if(sType.equalsIgnoreCase("array")){
				Element	arrayElem = new Element("array");
				elem.addContent(arrayElem);
				ArrayList dataList_a = (ArrayList)mapEntry.getValue();
				if(dataList_a!=null&&dataList_a.size()>0)				
				for(int i=0;i<dataList_a.size();i++){
					Map dataMap = (Map)dataList_a.get(i);
					Element	strutElem = new Element("struct");
					arrayElem.addContent(strutElem);
					
					addBodyElement(strutElem,dataMap,currEle);

				}
			}
		}
	}

	public static String getSret_status() {
		return Sret_status;
	}

	public static void setSret_status(String sretStatus) {
		Sret_status = sretStatus;
	}

	public static String getSbranch_id() {
		return Sbranch_id;
	}

	public static void setSbranch_id(String sbranchId) {
		Sbranch_id = sbranchId;
	}

	public static String getSsource_branch_no() {
		return Ssource_branch_no;
	}

	public static void setSsource_branch_no(String ssourceBranchNo) {
		Ssource_branch_no = ssourceBranchNo;
	}

	public static String getSdest_branch_no() {
		return Sdest_branch_no;
	}

	public static void setSdest_branch_no(String sdestBranchNo) {
		Sdest_branch_no = sdestBranchNo;
	}

	public static String getSmessage_type() {
		return Smessage_type;
	}

	public static void setSmessage_type(String smessageType) {
		Smessage_type = smessageType;
	}

	public static String getSmessage_code() {
		return Smessage_code;
	}

	public static void setSmessage_code(String smessageCode) {
		Smessage_code = smessageCode;
	}

	public static String getSret_code() {
		return Sret_code;
	}

	public static void setSret_code(String sretCode) {
		Sret_code = sretCode;
	}

	public static String getSret_msg() {
		return Sret_msg;
	}

	public static void setSret_msg(String sretMsg) {
		Sret_msg = sretMsg;
	}

	public static String getSseq_no() {
		return Sseq_no;
	}

	public static void setSseq_no(String sseqNo) {
		Sseq_no = sseqNo;
	}

	public static String getSpgup_or_pgdn() {
		return Spgup_or_pgdn;
	}

	public static void setSpgup_or_pgdn(String spgupOrPgdn) {
		Spgup_or_pgdn = spgupOrPgdn;
	}

	public static String getStotal_num() {
		return Stotal_num;
	}

	public static void setStotal_num(String stotalNum) {
		Stotal_num = stotalNum;
	}

	public static String getScurrent_num() {
		return Scurrent_num;
	}

	public static void setScurrent_num(String scurrentNum) {
		Scurrent_num = scurrentNum;
	}

	public static String getSpage_start() {
		return Spage_start;
	}

	public static void setSpage_start(String spageStart) {
		Spage_start = spageStart;
	}

	public static String getSpage_end() {
		return Spage_end;
	}

	public static void setSpage_end(String spageEnd) {
		Spage_end = spageEnd;
	}


}
