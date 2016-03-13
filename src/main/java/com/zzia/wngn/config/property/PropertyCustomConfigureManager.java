/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config.property;

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
 * @Date 2015年1月2日 下午2:16:08
 * @Version 1.0
 * @Description 用户自定义属性配置文件 projectName/config/core.properties或者classes/core.properities
 */
public class PropertyCustomConfigureManager extends PropertyConfigureManager implements IConfigureManager {

	private static final String CONFIG_SUFFIX = ".properties";
	private static final String CONFIG_PATH = "/config/";
	private static final Map<String, String> CONFIG_MAPS = new HashMap<String, String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zzia.wngn.config.IConfigureManager#getConfigureMap()
	 */
	@Override
	public Map<String, String> getConfigureMap(String configFileName) {
		InputStream configInputStream = null;
		InputStream configInputFileStream = null;
		//first load classes path file
		try {
			configInputStream = this.getClass().getResourceAsStream("/" + configFileName + CONFIG_SUFFIX);
			if(configInputStream!=null){
				Map<String, String> propertyConfig = super.parserPropertyConfig(configInputStream);
				CONFIG_MAPS.putAll(propertyConfig);
			}
			//next load classes path file
			File file = new File(System.getProperty("user.dir") + CONFIG_PATH + configFileName + CONFIG_SUFFIX);
			if (file.isFile() && file.exists()) {
				configInputFileStream = new FileInputStream(file);
				Map<String, String> propertyConfig = super.parserPropertyConfig(configInputFileStream);
				CONFIG_MAPS.putAll(propertyConfig);
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
