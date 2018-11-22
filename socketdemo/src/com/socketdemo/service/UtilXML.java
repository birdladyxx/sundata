package com.socketdemo.service;

import java.io.InputStream;
import java.io.Reader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class UtilXML {

	/* XML编码方式 */
	public static final String ENCODE_GBK = "GBK";
	public static final String ENCODE_UTF8 = "UTF-8";
	public static final String ENCODE_UTF16 = "UTF-16";
	public static final String ENCODE_GB2312 = "GB2312";
	public static final String ENCODE_ISO8859 = "ISO8859-1";
	
	/**
	 * 以"/"为准，判断有几个上层节点。
	 * @param parentNode
	 * @return
	 */
	public static int getInt(String parentNode) {
		int i = 0;
		int j = 0;
		String xpath = parentNode;
		while (i > -1) {
			i = xpath.indexOf("/");
			if (i != -1) {
				xpath = xpath.substring(i + 1, xpath.length());
			}
			j++;
		}
		return j;
	}
	
	/**
	 * 获取Element数组
	 * @param doc
	 * @param parentNode
	 * @return
	 */
	public static Element[] getElement(Document doc, String parentNode) {
		String xpath = parentNode;
		Element root = doc.getRootElement();
		int count = getInt(parentNode);
		Element[] elem = new Element[count +1];
		int i = 0;
		int j = 0;
		String path = "";
		while (i > -1) {
			i = xpath.indexOf("/");
			if (i != -1) {
				path = xpath.substring(0, i);
				xpath = xpath.substring(i + 1, xpath.length());
			} else {
				path = xpath.substring(0, xpath.length());
			}
			elem[j] = root.getChild(path);
			root = elem[j];
			j++;
		}
		return elem;
	}
	
	/**
	 * 多层进行增加元素
	 * @param doc
	 * @param parentNode
	 * @param childNode
	 */
	public static void addContent(Object doc, String parentNode, Element childNode) {
		try {
			if ("".equals(parentNode)) {
				throw new Exception("parentNode不能为空！");
			}
			Document dom = (Document) doc;
			Element[] elem = getElement(dom, parentNode);
			elem[elem.length - 1] = childNode;
			for (int i = elem.length - 1; i > 0; i--) {
				elem[i - 1].addContent(elem[i]);
				if (elem[i].getParent() != null) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根节点下加入元素
	 * @param childNode
	 * @param parentNode
	 */
	public static void addContent(String childNode, Element parentNode) {
		try {
			Element child = new Element(childNode);
			parentNode.addContent(child);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 传入两个参数，加入元素
	 * @param doc
	 * @param parentNode
	 * @param childNode
	 */
	public static void addContent(Object doc, String parentNode, String childNode) {
		try {
			Document dom = (Document) doc;
			Element[] elem = getElement(dom, parentNode);
			Element em = new Element(childNode);
			elem[elem.length - 1] = em;
			for (int i = elem.length - 1; i > 0;) {
				elem[i - 1].addContent(elem[i]);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加属性
	 * @param data
	 * @param file
	 * @param scale
	 * @param type
	 * @param length
	 * @param text
	 */
	public static void addAttribute(Element data, Element file, String scale, String type, String length, String text) {
		file.setAttribute("scale", scale);
		file.setAttribute("type", type);
		file.setAttribute("length", length);
		file.setText(text);
		data.addContent(file);
	}
	
	/**
	 * 以String类型对报文进行输出
	 * @param doc
	 * @param encodingMode
	 * @return
	 */
	public static String outputter(Document doc, String encodingMode) {
		XMLOutputter out = null;
		String xml = "";
		try {
			out = new XMLOutputter();
			xml = out.outputString(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	/**
	 * 读取InputStream数据
	 * @param bin
	 * @return
	 */
	public static Document load(InputStream bin) {
		Document doc = null;
		try {
			XMLOutputter put = new XMLOutputter();
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(bin);
			put.output(doc, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 读取Reader数据
	 * @param bin
	 * @return
	 */
	public static Document load(Reader bin) {
		Document doc = null;
		try {
			XMLOutputter put = new XMLOutputter();
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(bin);
			put.output(doc, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 根据路径查出此节点取出此节点的元素
	 * @param doc
	 * @param xpath
	 * @param attribute
	 * @param attributeValue
	 * @param childNo
	 * @return
	 */
	public static Element childNode(Document doc, String xpath, String attribute, String attributeValue, String childNo) {
		Element childNode = null;
		try {
			Element elem = doc.getRootElement();//121321321
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return childNode;
	}
	
	/**
	 * 返回字符串数组
	 * @param xpath
	 * @return
	 */
	public static String[] elemStr(String xpath) {
		String[] elemName = new String[getInt(xpath)];
		String path = "";
		int i = 0;
		int j = 0;
		while (i > -1) {
			i = xpath.indexOf("/");
			if (i != -1) {
				path = xpath.substring(i + 1, xpath.length());
				xpath = xpath.substring(0, xpath.length());
			} else {
				path = xpath.substring(0, xpath.length());
			}
			elemName[j] = path;
			j++;
		}
		return elemName;
	}
	
	
	
	
	
	
	
	
	
	
}// end
