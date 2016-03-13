/**
 * 
 */
package com.zzia.wngn.config;

import java.util.HashMap;
import java.util.Map;
import com.zzia.wngn.config.property.PropertyCustomConfigureManager;
import com.zzia.wngn.config.property.PropertyDefaultConfigureManager;
import com.zzia.wngn.config.xml.XmlCustomConfigureManager;
import com.zzia.wngn.config.xml.XmlDefaultConfigureManager;


/**
 * @title 配置文件管理类
 * @author Wang 2015年1月2日
 * @Date    2015年1月2日 下午1:52:58
 * @Version 1.0
 * @Description
 */
public class ConfigureManager {
    
    private static Map<String, String>  CONFIG_MAPS = null;
    public static final String  CONFIG_TYPE_PORPERY = "propery";
    public static final String  CONFIG_TYPE_XML = "xml";

    public static void loadConfigure(String type,String configFileName) {
    	IConfigureManager defaultConfigure = null;
		IConfigureManager customConfigure = null;
		CONFIG_MAPS = new HashMap<String, String>();
    	if(type.equals(CONFIG_TYPE_PORPERY)){
    		defaultConfigure = new PropertyDefaultConfigureManager();
    		customConfigure = new PropertyCustomConfigureManager();
    	}else if(type.equals(CONFIG_TYPE_XML)){
    		defaultConfigure = new XmlDefaultConfigureManager();
    		customConfigure = new XmlCustomConfigureManager();
    	}
    	Map<String, String> defaultConfigureMap = defaultConfigure.getConfigureMap(configFileName);
    	Map<String, String> customConfigureMap = customConfigure.getConfigureMap(configFileName);
    	CONFIG_MAPS.putAll(defaultConfigureMap);
    	if(customConfigureMap!=null){
    		CONFIG_MAPS.putAll(customConfigureMap);
    	}
    }
    
    public static void loadXmlConfigure(String name){
    	loadConfigure(CONFIG_TYPE_XML, name);
    }
    
    public static void loadProperyConfigure(String name){
    	loadConfigure(CONFIG_TYPE_PORPERY, name);
    }
    
    /**
     * 获得配置项值
     * @param configName 配置名称
     * @return 配置项值
     */
    public static String getConfigValue(String type,String name,String configName) {
    	if(CONFIG_MAPS==null){
    		loadConfigure(type, name);
    	}
        return CONFIG_MAPS.get(configName);
    }
}
