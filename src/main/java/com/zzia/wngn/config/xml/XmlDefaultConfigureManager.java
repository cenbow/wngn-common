/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config.xml;

import java.util.HashMap;
import java.util.Map;

import com.zzia.wngn.config.IConfigureManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * @title
 * @author Wang 2015年1月2日
 * @Date 2015年1月2日 下午2:58:10
 * @Version 1.0
 * @Description
 */
public class XmlDefaultConfigureManager extends XmlConfigureManager implements IConfigureManager {

	private static final String CONFIG_SUFFIX = "-default.xml";
	private static final String CONFIG_PATH = "/resources/";
	private static Map<String, String> CONFIG_MAPS = new HashMap<String, String>();
	
	@Override
	public Map<String, String> getConfigureMap(String configFileName) {
		InputStream configInputStream = null;
		try {
			configInputStream = this.getClass().getResourceAsStream(CONFIG_PATH+configFileName+CONFIG_SUFFIX);
			Map<String, String> xmlConfig = super.parserXmlConfig(configInputStream);
			CONFIG_MAPS.putAll(xmlConfig);
			return CONFIG_MAPS;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(configInputStream!=null){
				try {
					configInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
