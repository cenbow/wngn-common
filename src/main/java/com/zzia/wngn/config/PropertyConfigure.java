package com.zzia.wngn.config;

public class PropertyConfigure extends AbstractConfigure{

	private String type;
	private String name;
	
	public PropertyConfigure(String name){
		this.name = name;
		this.type = ConfigureManager.CONFIG_TYPE_PORPERY;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}
}
