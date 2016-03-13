package com.zzia.wngn.config.xml;

import com.zzia.wngn.config.IConfigure;
import com.zzia.wngn.config.PropertyConfigure;
import com.zzia.wngn.config.XmlConfigure;

public class ConfigureFactory {

	private static  IConfigure configure = null;
	
	public static IConfigure getPropertyConfigureInstance(String name){
		if(configure == null){
			configure = new PropertyConfigure(name);
		}
		return configure;
	}
	
	public static IConfigure getXmlConfigureInstance(String name){
		if(configure == null){
			configure = new XmlConfigure(name);
		}
		return configure;
	}
}
