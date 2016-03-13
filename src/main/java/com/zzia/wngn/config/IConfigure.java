package com.zzia.wngn.config;

public interface IConfigure {

	public String getStringValue(String configName);
	
	public Integer getIntegerValue(String configName);
	
	public Boolean getBooleanValue(String configName);
	
	public Float getFloatValue(String configName);
	
	public Double getDoubleValue(String configName);
	
	public Long getLongValue(String configName);
	
}
