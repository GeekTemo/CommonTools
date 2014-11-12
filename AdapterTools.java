package com.gxf.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 * JSO与XML相互转换的时候常用的一些操作
 * 
 * @author gongxingfa
 * 
 */
public class AdapterTools {
	/**
	 * 对象路径转化为XPath路径
	 * 
	 * @param obj_path
	 * @return
	 */
	private static String objPath2XPath(String obj_path) {
		String xpath = "//*";
		String[] pathInfo = obj_path.split("\\.");
		for (String p : pathInfo) {
			xpath += "[local-name()='" + p + "']/*";
		}
		xpath = xpath.substring(0, xpath.lastIndexOf("/*"));
		return xpath;
	}

	/**
	 * 以对象的方式获取节点
	 * 
	 * @param doc
	 * @param objPath
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getNodes(Document doc, String objPath) {
		String xPathStr = objPath2XPath(objPath);
		XPath xPath = doc.createXPath(xPathStr);
		List list = xPath.selectNodes(doc);
		return list;
	}

	/**
	 * 以对象路径的方式获取XML节点的文本值
	 * 
	 * @param doc
	 * @param objPath
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getXmlNodeText(Document doc, String objPath) {
		String xPathStr = objPath2XPath(objPath);
		XPath xPath = doc.createXPath(xPathStr);
		List list = xPath.selectNodes(doc);
		if (list != null && list.size() > 0) {
			Node node = (Node) list.get(0);
			return node.getText();
		}
		return "";
	}

	/**
	 * 以对象路径的方式设置XML节点的值
	 * 
	 * @param doc
	 * @param objPath
	 * @param text
	 */
	@SuppressWarnings("rawtypes")
	public static void setXmlNodeText(Document doc, String objPath, String text) {
		String xPathStr = objPath2XPath(objPath);
		XPath xPath = doc.createXPath(xPathStr);
		List list = xPath.selectNodes(doc);
		if (list != null && list.size() > 0) {
			Node node = (Node) list.get(0);
			node.setText(text);
		}
	}

	/**
	 * 以对象路径的方式获取JSON对象的值
	 * 
	 * @param root
	 * @param objPath
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getJsonValue(JSONObject root, String objPath) {
		if (!objPath.contains(".")) {
			return root.getString(objPath);
		}
		String[] pathinfo = objPath.split("\\.");
		List pathList = new ArrayList();
		for (String p : pathinfo) {
			pathList.add(p);
		}
		JSONObject tempJson = root;
		String key = "";
		while (pathList.size() != 1) {
			key = (String) pathList.remove(0);
			tempJson = tempJson.getJSONObject(key);
		}
		key = (String) pathList.remove(0);
		return tempJson.getString(key);
	}

	/**
	 * 以对象的方式设置JSON对象的值
	 * 
	 * @param root
	 * @param objPath
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setJsonValue(JSONObject root, String objPath,
			Object value) {
		if (!objPath.contains(".")) {
			root.put(objPath, value);
			return;
		}
		String[] pathinfo = objPath.split("\\.");
		List pathList = new ArrayList();
		for (String p : pathinfo) {
			pathList.add(p);
		}
		JSONObject tempJson = root;
		String key = "";
		while (pathList.size() != 1) {
			key = (String) pathList.remove(0);
			tempJson = tempJson.getJSONObject(key);
		}
		key = (String) pathList.remove(0);
		tempJson.put(key, value);
	}
}
