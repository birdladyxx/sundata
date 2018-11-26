package com.socketdemo.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义ql_response.xml文件配置参数格式
 * @author qinzh
 *
 */
public class DataElement {

	private String name = "";
	private String value = "";
	private String type = "";
	private String scale = "";
	private int length = 0;
	private String remark = "";
	private List<DataElement> list = new ArrayList<DataElement>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<DataElement> getList() {
		return list;
	}
	public void setList(List<DataElement> list) {
		this.list = list;
	}
	public DataElement() {
		
	}
	public DataElement(String name, String type, int length) {
		this.name = name;
		this.type = type;
		this.length = length;
	}
	public DataElement(String name, String type, int length, String remark) {
		this.name = name;
		this.type = type;
		this.length = length;
		this.remark = remark;
	}
	public DataElement(String name, String type, String scale, int length,
			String remark) {
		this.name = name;
		this.type = type;
		this.scale = scale;
		this.length = length;
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "DataElement [name=" + name + ", value=" + value + ", type="
				+ type + ", scale=" + scale + ", length=" + length + "]";
	}
	
}
