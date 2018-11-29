package com.socketdemo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * 解析XML配置文件，放在dataMap中。
 * @author qinzh
 *
 */
public class ParseResponseXML {

	private Map<String, List<DataElement>> dataMap = new HashMap<String, List<DataElement>>();
	private List<DataElement> dataList = null;
	private static ParseResponseXML parseXML = null;
	public Map<String, List<DataElement>> getDataMap() {
		return dataMap;
	}
	public List<DataElement> getDatalList(String sHeader) throws Exception {
		dataList = dataMap.get(sHeader);
		if (dataList != null) {
			return dataList;
		} else {
			throw new Exception("配置文件中找不到相应的交易码！");
		}
	}
	ParseResponseXML() throws Exception {
		init();
	}
	
	public static ParseResponseXML getIntance() throws Exception {
		if (parseXML == null) {
			parseXML = new ParseResponseXML();
			return (ParseResponseXML) parseXML.clone();
		}
		return (ParseResponseXML) parseXML.clone();
	}
	
	private void init() throws Exception {
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			doc = sb.build(this.getClass().getClassLoader().getResource("socket_message.xml").getPath());
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new Exception("解析socket_message.xml文件时出错！");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("解析配置文件时出错！");
		}
		Element eroot = doc.getRootElement();// 获取配置文件的根目录
		List<Element> sHeaderList = eroot.getChildren();// 获取配置文件的一级子节点
		DataElement dataElement = null;
		Element ele = null;
		String sHeader = "";
		List<Element> listChild = null;
		List<DataElement> dataList = null;
		for (Iterator<Element> it = sHeaderList.iterator(); it.hasNext();) {// 循环取出对一级子节点下的标签做处理
			ele = it.next();
			sHeader = ele.getName().trim();
			listChild = ele.getChildren();
			dataList = new ArrayList<DataElement>();
			for (int i = 0; i < listChild.size(); i++) {
				ele = listChild.get(i);
				// 获取配置文件中的配置值
				dataElement = new DataElement(ele.getName(), ele.getAttributeValue("type"), ele.getAttributeValue("scale"), Integer.parseInt(ele.getAttributeValue("length")), ele.getAttributeValue("remark"));
				List<DataElement> arList = new ArrayList<DataElement>();
				if (ele.getAttributeValue("type").equalsIgnoreCase("array")) {
					List<Element> listChild2 = ele.getChildren();
					for (int j = 0; j < listChild2.size(); j++) {
						ele = listChild2.get(j);
						DataElement dataElement2 = new DataElement(ele.getName(), ele.getAttributeValue("type"), ele.getAttributeValue("scale"), Integer.parseInt(ele.getAttributeValue("length")), ele.getAttributeValue("remark"));
						arList.add(dataElement2);
					}
				}
				dataElement.setList(arList);
				dataList.add(dataElement);
				dataElement = null;
			}
			dataMap.put(sHeader, dataList);
			dataList = null;
		}
	}
	
	/**
	 * 复制对象
	 */
	protected Object clone() throws CloneNotSupportedException {
		try {
			ParseResponseXML cl = new ParseResponseXML();
			for (String key : this.dataMap.keySet()) {
				List<DataElement> list = dataMap.get(key);
				List<DataElement> listnew = new ArrayList<DataElement>();
				for (DataElement de : list) {
					DataElement denew = new DataElement(de.getName(), de.getType(), de.getScale(), de.getLength(), de.getRemark());
					denew.setList(de.getList());
					listnew.add(denew);
				}
				cl.dataMap.put(key, listnew);
			}
			return cl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.clone();
	}
	
}//end
