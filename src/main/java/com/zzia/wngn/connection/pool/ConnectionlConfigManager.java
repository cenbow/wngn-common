package com.zzia.wngn.connection.pool;

/** 
 * 操作配置文件类 读  写 修改 删除等操作 
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class ConnectionlConfigManager {

	private static final String CONFIG_NAME = "ds.config.xml";
	private static final String DEFAULT_CONFIG_NAME = "ds.config.default.xml";
	private static final String DEFAULT_CONFIG_PATH = "/";
	private static final String CUSTOM_CONFIG_PATH = "/";

	private static final String CONFIG_TAG_TYPE = "type";
	private static final String CONFIG_TAG_NAME = "name";
	private static final String CONFIG_TAG_DRIVER = "driver";
	private static final String CONFIG_TAG_URL = "url";
	private static final String CONFIG_TAG_USERNAME = "username";
	private static final String CONFIG_TAG_PASSWORD = "password";
	private static final String CONFIG_TAG_MAXCONN = "maxconn";

	private static final Logger logger = Logger.getLogger(ConnectionlConfigManager.class);

	/**
	 * 构造函数
	 */
	public ConnectionlConfigManager() {

	}

	public static void parserDefaultXmlConfig() {
		InputStream configInputStream = ConnectionlConfigManager.class.getResourceAsStream(DEFAULT_CONFIG_PATH + CONFIG_NAME);
		Map<String, DSConfigBean> xmlMap = parserXmlConfig(configInputStream);
	}

	public static File getCustomConfigureFile() {
		File file = new File(System.getProperty("user.dir") + CUSTOM_CONFIG_PATH + CONFIG_NAME);
		if (file.isFile() && file.exists()) {
			return file;
		}
		return null;
	}

	public static File getDefaultConfigureFile() {
		File file = new File(ConnectionlConfigManager.class.getResource(DEFAULT_CONFIG_PATH + CONFIG_NAME).getFile());
		return file;
	}

	public static File getConfigureFile() {
		File file = getCustomConfigureFile();
		if (file != null) {
			return file;
		}
		return getDefaultConfigureFile();
	}

	public static void parserCustomXmlConfig() {
		try {
			File file = new File(System.getProperty("user.dir") + CUSTOM_CONFIG_PATH + CONFIG_NAME);
			if (file.isFile() && file.exists()) {
				InputStream configInputStream = new FileInputStream(file);
				Map<String, DSConfigBean> xmlMap = parserXmlConfig(configInputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static Map<String, DSConfigBean> parserXmlConfig(InputStream is) {
		Map<String, DSConfigBean> xmlMap = new HashMap<String, DSConfigBean>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document document = db.parse(is);
			NodeList employees = document.getChildNodes();
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();
					String key = "";
					DSConfigBean dcb = new DSConfigBean();
					for (int k = 0; k < employeeMeta.getLength(); k++) {
						if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_NAME)) {
							key = employeeMeta.item(k).getTextContent();
							dcb.setName(employeeMeta.item(k).getTextContent());
						} else if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_TYPE)) {
							dcb.setType(employeeMeta.item(k).getTextContent());
						} else if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_DRIVER)) {
							dcb.setDriver(employeeMeta.item(k).getTextContent());
						} else if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_URL)) {
							dcb.setUrl(employeeMeta.item(k).getTextContent());
						} else if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_USERNAME)) {
							dcb.setUsername(employeeMeta.item(k).getTextContent());
						} else if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_PASSWORD)) {
							dcb.setPassword(employeeMeta.item(k).getTextContent());
						} else if (employeeMeta.item(k).getNodeName().equals(CONFIG_TAG_MAXCONN)) {
							String value = employeeMeta.item(k).getTextContent();
							dcb.setMaxconn(Integer.parseInt(value));
						}
					}
					if (!"".equals(key)) {
						xmlMap.put(key, dcb);
					}
				}
			}
			return xmlMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取xml配置文件
	 * 
	 * @param path
	 * @return
	 */
	public static Vector readConfigInfo() {
		Vector dsConfig = null;
		try {
			Map<String, DSConfigBean> defaultConfigInfo = readDefaultConfigInfo();
			Map<String, DSConfigBean> constomConfigInfo = readConstomConfigInfo();
			defaultConfigInfo.putAll(constomConfigInfo);
			dsConfig = new Vector();
			for (String key : defaultConfigInfo.keySet()) {
				dsConfig.add(defaultConfigInfo.get(key));
			}
			return dsConfig;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, DSConfigBean> readXmlConfigInfo(InputStream is) throws Exception {
		Map<String, DSConfigBean> map = new HashMap<String, DSConfigBean>();
		if (is == null) {
			return map;
		}
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(is);
		Element root = doc.getRootElement();
		List pools = root.getChildren();
		Element pool = null;
		Iterator allPool = pools.iterator();
		while (allPool.hasNext()) {
			pool = (Element) allPool.next();
			DSConfigBean dscBean = new DSConfigBean();
			dscBean.setType(pool.getChild("type").getText());
			dscBean.setName(pool.getChild("name").getText());
			logger.info(dscBean.getName());
			dscBean.setDriver(pool.getChild("driver").getText());
			dscBean.setUrl(pool.getChild("url").getText());
			dscBean.setUsername(pool.getChild("username").getText());
			dscBean.setPassword(pool.getChild("password").getText());
			dscBean.setMaxconn(Integer.parseInt(pool.getChild("maxconn").getText()));
			if (map.containsKey(dscBean.getName())) {
				continue;
			}
			map.put(dscBean.getName(), dscBean);
		}

		return map;
	}

	public static Map<String, DSConfigBean> readDefaultConfigInfo() throws Exception {
		InputStream configInputStream = null;
		try {
			configInputStream = ConnectionlConfigManager.class.getResourceAsStream(DEFAULT_CONFIG_PATH + DEFAULT_CONFIG_NAME);
			return readXmlConfigInfo(configInputStream);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (configInputStream != null) {
					configInputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static Map<String, DSConfigBean> readConstomConfigInfo() throws Exception {
		InputStream configInputStream = null;
		try {
			configInputStream = ConnectionlConfigManager.class.getResourceAsStream(CUSTOM_CONFIG_PATH + CONFIG_NAME);
			return readXmlConfigInfo(configInputStream);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (configInputStream != null) {
					configInputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改配置文件 没时间写 过段时间再贴上去 其实一样的
	 */
	public static void modifyConfigInfo(InputStream is, DSConfigBean dsb) throws Exception {
		FileOutputStream fo = null; // 写入

	}

	/**
	 * 增加配置文件
	 * 
	 */
	public static void addConfigInfo(DSConfigBean dsb) {
		FileOutputStream fo = null;
		InputStream is = null;
		try {
			is = new FileInputStream(getConfigureFile());
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is); // 得到xml
			Element root = doc.getRootElement();
			List pools = root.getChildren();// 得到xml子树

			Element newpool = new Element("pool"); // 创建新连接池

			Element pooltype = new Element("type"); // 设置连接池类型
			pooltype.setText(dsb.getType());
			newpool.addContent(pooltype);

			Element poolname = new Element("name");// 设置连接池名字
			poolname.setText(dsb.getName());
			newpool.addContent(poolname);

			Element pooldriver = new Element("driver"); // 设置连接池驱动
			pooldriver.addContent(dsb.getDriver());
			newpool.addContent(pooldriver);

			Element poolurl = new Element("url");// 设置连接池url
			poolurl.setText(dsb.getUrl());
			newpool.addContent(poolurl);

			Element poolusername = new Element("username");// 设置连接池用户名
			poolusername.setText(dsb.getUsername());
			newpool.addContent(poolusername);

			Element poolpassword = new Element("password");// 设置连接池密码
			poolpassword.setText(dsb.getPassword());
			newpool.addContent(poolpassword);

			Element poolmaxconn = new Element("maxconn");// 设置连接池最大连接
			poolmaxconn.setText(String.valueOf(dsb.getMaxconn()));
			newpool.addContent(poolmaxconn);
			pools.add(newpool);// 将child添加到root
			Format format = Format.getPrettyFormat();
			format.setIndent("");
			format.setEncoding("utf-8");
			XMLOutputter outp = new XMLOutputter(format);
			fo = new FileOutputStream(getConfigureFile());
			outp.output(doc, fo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除配置文件
	 */
	public static void delConfigInfo(String name) {
		FileOutputStream fo = null;
		InputStream is = null;
		try {
			is = new FileInputStream(getConfigureFile());
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			List pools = root.getChildren();
			Element pool = null;
			Iterator allPool = pools.iterator();
			while (allPool.hasNext()) {
				pool = (Element) allPool.next();
				if (pool.getChild("name").getText().equals(name)) {
					pools.remove(pool);
					break;
				}
			}
			Format format = Format.getPrettyFormat();
			format.setIndent("");
			format.setEncoding("utf-8");
			XMLOutputter outp = new XMLOutputter(format);
			fo = new FileOutputStream(getConfigureFile());
			outp.output(doc, fo);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}