/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @title
 * @author Wang 2015年1月2日
 * @Date 2015年1月2日 下午3:50:15
 * @Version 1.0
 * @Description
 */
public class XmlConfigureManager {

	private static final String XML_CONFIG_TAG_NAME = "name";
	private static final String XML_CONFIG_TAG_VALUE = "value";

	protected Map<String, String> parserXmlConfig(InputStream is) {
		Map<String, String> xmlMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			NodeList employees = document.getChildNodes();
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();
					String key = "";
					String value = "";
					for (int k = 0; k < employeeMeta.getLength(); k++) {
						if (employeeMeta.item(k).getNodeName().equals(XML_CONFIG_TAG_NAME)) {
							key = employeeMeta.item(k).getTextContent();
						} else if (employeeMeta.item(k).getNodeName().equals(XML_CONFIG_TAG_VALUE)) {
							value = employeeMeta.item(k).getTextContent();
						}
					}
					xmlMap.put(key, value);
				}
			}
			return xmlMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
