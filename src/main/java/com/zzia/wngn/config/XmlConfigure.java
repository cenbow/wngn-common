package com.zzia.wngn.config;

public class XmlConfigure extends AbstractConfigure{

	private String type;
	private String name;
	
	public XmlConfigure(String name){
		this.name = name;
		this.type = ConfigureManager.CONFIG_TYPE_XML;
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
