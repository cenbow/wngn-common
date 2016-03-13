package com.zzia.wngn.config;

public abstract class AbstractConfigure implements IConfigure {

	public abstract String getType();
	
	public abstract String getName();
	
	@Override
	public String getStringValue(String configName) {
		return ConfigureManager.getConfigValue(getType(), getName(), configName);
	}

	@Override
	public Integer getIntegerValue(String configName) {
		return Integer.parseInt(getStringValue(configName));
	}

	@Override
	public Boolean getBooleanValue(String configName) {
		return Boolean.parseBoolean(getStringValue(configName));
	}

	@Override
	public Float getFloatValue(String configName) {
		return Float.parseFloat(getStringValue(configName));
	}

	@Override
	public Double getDoubleValue(String configName) {
		return Double.parseDouble(getStringValue(configName));
	}

	@Override
	public Long getLongValue(String configName) {
		return Long.parseLong(getStringValue(configName));
	}

}
