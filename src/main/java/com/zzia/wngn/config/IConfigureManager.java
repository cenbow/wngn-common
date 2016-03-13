/**
 * @by WangGang @2015年1月2日
 */
package com.zzia.wngn.config;

import java.util.Map;

/**
 * @title 
 * @author Wang 2015年1月2日
 * @Date    2015年1月2日 下午2:04:52
 * @Version 1.0
 * @Description  
 */
public interface IConfigureManager {

	public Map<String,String> getConfigureMap(String configFileName);
}
