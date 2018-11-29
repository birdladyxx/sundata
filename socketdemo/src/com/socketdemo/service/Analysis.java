package com.socketdemo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;

/**
 * 
 * @author qinzh
 *
 */
public class Analysis {

	public static HashMap<String, Object> enCodeElemList(List<Element> elemList, List<DataElement> dataList) throws Exception {
		HashMap<String, Object> retMap = new HashMap<String,Object>();
		String sType = "";
		String sLength = "";
		for (int i = 0; i < elemList.size(); i++) {
			Element el = elemList.get(i);
			String name = el.getAttribute("name").getValue();
			List<DataElement> arList = null;
			for (DataElement ele : dataList) {
				if (ele.getName().equalsIgnoreCase(name)) {
					sType = ele.getType();
					sLength = String.valueOf(ele.getLength());
					arList = ele.getList();
				}
			}
			if (sType == null) {
				continue;
			}
			if (sType.equalsIgnoreCase("string")) {
				Element em = el.getChild("field");
				String value = "";
				if (em == null) {
					
				} else {
					value = em.getText();
					if (value == null) {
						value = "";
					} else {
						value = value.trim();
					}
					if (!"".equals(sLength)) {
						int leng = Integer.parseInt(sLength);
						if (value.length() > leng) {
							value = value.substring(0, leng);
						}
					}
				}
				retMap.put(name, value);
			} else if (sType.equalsIgnoreCase("double")) {
				Element em = el.getChild("field");
				String value = em.getText();
				value = value == null ? "" : value.trim();
				if (!"".equals(sLength)) {
					int leng = Integer.parseInt(sLength);
					if (value.length() > leng) {
						value = value.substring(0, leng);
					}
				}
				retMap.put(name, value);
			} else if (sType.equalsIgnoreCase("array")) {
				Element arrayElement = el.getChild("array");
				List<Element> arrayElemList = arrayElement.getChildren();
				ArrayList<HashMap<String, Object>> arrayField = new ArrayList<HashMap<String, Object>>();
				for (int j = 0; j < arrayElemList.size(); j++) {
					Element documentStruct = arrayElemList.get(j);
					List<Element> subElemList = documentStruct.getChildren();
					HashMap<String, Object> subRetMap = enCodeElemList(subElemList, arList);
					arrayField.add(subRetMap);
				}
				retMap.put(name, arrayField);
				sType = "";
			}
		}
		return retMap;
	}
}//end

















