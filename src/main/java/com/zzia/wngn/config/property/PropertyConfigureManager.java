/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config.property;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * @title
 * @author Wang 2015年1月2日
 * @Date 2015年1月2日 下午2:16:08
 * @Version 1.0
 * @Description
 */
public class PropertyConfigureManager {

	protected Map<String, String> parserPropertyConfig(InputStream is) {
		Map<String, String> propertyMap = new HashMap<String, String>();
		if(is == null){
			return propertyMap;
		}
		try {
			Properties properties = new Properties();
			properties.load(is);
			Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				propertyMap.put(key.toString(), value.toString());
			}
			return propertyMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
