package com.zzia.wngn.connection.pool;

public class DSConfigBean {

	private String type     =""; //数据库类型
	private String name     =""; //连接池名字 
	private String driver   =""; //数据库驱动 
	private String url      =""; //数据库url 
	private String username =""; //用户名 
	private String password =""; //密码 
	private int maxconn  =0; //最大连接数 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxconn() {
		return maxconn;
	}
	public void setMaxconn(int maxconn) {
		this.maxconn = maxconn;
	}
	public DSConfigBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DSConfigBean(String type, String name, String driver, String url,
			String username, String password, int maxconn) {
		super();
		this.type = type;
		this.name = name;
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxconn = maxconn;
	}
	
}
