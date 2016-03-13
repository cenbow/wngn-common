/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.zzia.wngn.config.IConfigureManager;

/**
 * @title
 * @author Wang 2015年1月2日
 * @Date 2015年1月2日 下午2:58:10
 * @Version 1.0
 * @Description
 */
public class XmlCustomConfigureManager extends XmlConfigureManager implements IConfigureManager {

	private static final String CONFIG_SUFFIX = ".xml";
	private static final String CONFIG_PATH = "/config/";
	private static final Map<String, String> CONFIG_MAPS = new HashMap<String, String>();

	@Override
	public Map<String, String> getConfigureMap(String configFileName) {
		InputStream configInputStream = null;
		InputStream configInputFileStream = null;
		try {
			configInputStream = this.getClass().getResourceAsStream("/" + configFileName + CONFIG_SUFFIX);
			if(configInputStream!=null){
				Map<String, String> propertyConfig = super.parserXmlConfig(configInputStream);
				CONFIG_MAPS.putAll(propertyConfig);
			}
			File file = new File(System.getProperty("user.dir") + CONFIG_PATH + configFileName + CONFIG_SUFFIX);
			if (file.isFile() && file.exists()) {
				configInputFileStream = new FileInputStream(file);
				Map<String, String> xmlConfig = parserXmlConfig(configInputFileStream);
				CONFIG_MAPS.putAll(xmlConfig);
			}
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
			if(configInputFileStream!=null){
				try {
					configInputFileStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
