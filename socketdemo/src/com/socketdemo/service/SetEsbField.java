package com.socketdemo.service;

import java.math.BigDecimal;
import java.util.List;


public class SetEsbField {

	private List<DataElement> dataList = null;
	private EsbError esbError = new EsbError();
	public List<DataElement> getDataList() {
		return dataList;
	}
	public EsbError getEsbError() {
		return esbError;
	}
	public SetEsbField(String sHeader) throws Exception {
		this.dataList = ParseResponseXML.getIntance().getDatalList(sHeader);
	}
	
	public void setFieldValue(String sFiledName, String sFiledValue) throws Exception {
		for (DataElement ele : dataList) {
			if (ele.getType().equalsIgnoreCase(sFiledName)) {
				try {
					if (ele.getType().equalsIgnoreCase("double")) {
						ele.setValue(parseDouble(sFiledValue, ele.getLength()));
					} else if (ele.getType().equalsIgnoreCase("string")) {
						if (ele.getLength() < sFiledValue.length()) {
							ele.setValue(sFiledValue.substring(0, ele.getLength()));
						} else {
							ele.setValue(sFiledValue);
						}
					}
					break;
				} catch (Exception e) {
					e.printStackTrace();
					esbError.add(ele.getRemark());
				}
			}
		}
	}
	
	private String parseDouble(String str, int len) throws Exception {
		if ("".equals(str)) {
			str = "0.0";
		}
		String s = "";
		if (len == 15) {
			s = new BigDecimal(str).setScale(2, BigDecimal.ROUND_HALF_UP).toString().replace(".", "");
			int sLen = s.length();
			if (sLen < len) {
				for (int i = 0; i < len - sLen; i++) {
					s = "0" + s;
				}
			}
		} else if (len == 8) {
			s = new BigDecimal(str).setScale(5, BigDecimal.ROUND_HALF_UP).toString().replace(".", "");
			int sLen = s.length();
			if (sLen < len) {
				for (int i = 0; i < len - sLen; i++) {
					s = "0" + s;
				}
			} else {
				throw new Exception("");
			}
		} else if (len == 5) {
			Double dData = Double.parseDouble(str);
			for (int i = 0; i < 2; i++) {
				dData *= 10;
				s = dData.intValue() + "";
			}
		}
		return s;
	}
	
	public String getErrorMessages() {
		return esbError.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
}//end
