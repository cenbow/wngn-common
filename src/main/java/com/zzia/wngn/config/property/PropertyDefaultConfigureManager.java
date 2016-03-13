/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.zzia.wngn.config.IConfigureManager;

/**
 * @title 属性配置文件管理
 * @author Wang 2015年1月2日
 * @Date    2015年1月2日 下午2:08:26
 * @Version 1.0
 * @Description  
 */
public class PropertyDefaultConfigureManager extends PropertyConfigureManager implements IConfigureManager{

	private static final String CONFIG_SUFFIX = "-default.properties";
	private static final String CONFIG_PATH = "/resources/";
	private static final Map<String, String> CONFIG_MAPS = new HashMap<String, String>();
    
	/* (non-Javadoc)
	 * @see com.zzia.wngn.config.IConfigureManager#getConfigureMap()
	 */
	@Override
	public Map<String, String> getConfigureMap(String configFileName) {
		InputStream configInputStream = null;
		try {
			//从jar包中读取文件
			configInputStream = this.getClass().getResourceAsStream(CONFIG_PATH+configFileName+CONFIG_SUFFIX);
			Map<String, String> propertyConfig = super.parserPropertyConfig(configInputStream);
			CONFIG_MAPS.putAll(propertyConfig);
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
