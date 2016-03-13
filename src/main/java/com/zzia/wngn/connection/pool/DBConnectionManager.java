package com.zzia.wngn.connection.pool;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * <p>Database connection pool management</p>
 * <p>use:</p>
 * <p>  //Database connection pool configuration file path</br>String PATH = "res/ds.config.xml";<br/>
		//Get the database connection pool management class(DBConnectionManager) instance</br>DBConnectionManager dm = DBConnectionManager.getInstance(PATH);<br/>
		//Creating a database connection</br>dm.createSimpleMySqlConntion(PATH, "c", "timeManage", "root", "", 100);<br/>
		//From the connection pool gets created database connection</br>Connection conn = dm.getConnection("c");</br>
		//Release connection resources</br> dm.freeConnection("c",conn)</p>
 * @author Administrator
 * 
 */
public class DBConnectionManager {
	private static final Logger logger = Logger.getLogger(DBConnectionManager.class);
	private String[] holdType = new String[]{"mysql","oracle","db2","postgresql","sqlserver"};
	static private DBConnectionManager instance;
	@SuppressWarnings("unused")
	static private int clients; 
	@SuppressWarnings("rawtypes")
	private Vector drivers = new Vector();
	@SuppressWarnings("rawtypes")
	private Hashtable pools = new Hashtable();

	/**
	 * DBConnectionManager Constructors
	 */
	private DBConnectionManager() {
		this.init();
	}
	/**
	 * Gets the only instance of DBConnectionManager
	 * @return
	 */
	static synchronized public DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;
	}
	/**
	 *  Release connection resources
	 * @param name
	 * @param con
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool)pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}
	/**
	 * Get a database connection from pool 
	 * @param name
	 * @return
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = null;
		Connection con = null;
		////Get connection pool by name
		pool = (DBConnectionPool) pools.get(name);
		if(pool!=null){
			con = pool.getConnection();
		}
		if (con != null)
			logger.info("Getted connection...");
		return con;
	}
	/**
	 *Get a database connection 
	 * @param name
	 * @param timeout
	 * @return
	 */
	public Connection getConnection(String name, long timeout) {
		DBConnectionPool pool = null;
		Connection con = null;
		//Get connection pool by name
		pool = (DBConnectionPool) pools.get(name);
		con = pool.getConnection(timeout);
		logger.info("getted connection...");
		return con;
	}
	/**
	 * Release all connections
	 */
	@SuppressWarnings("rawtypes")
	public synchronized void release() {
		Enumeration allpools = pools.elements();
		while (allpools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allpools.nextElement();
			if (pool != null)
				pool.release();
		}
		pools.clear();
	}
	/**
	 * Create a connection pool
	 * @param dsb
	 */
	@SuppressWarnings("unchecked")
	private void createPools(DSConfigBean dsb) {
		DBConnectionPool dbpool = new DBConnectionPool();
		dbpool.setName(dsb.getName());
		dbpool.setDriver(dsb.getDriver());
		dbpool.setUrl(dsb.getUrl());
		dbpool.setUser(dsb.getUsername());
		dbpool.setPassword(dsb.getPassword());
		dbpool.setMaxConn(dsb.getMaxconn());
		logger.info("Maximum number of connections:" + dsb.getMaxconn());
		pools.put(dsb.getName(), dbpool);
	}
	/**
	 * Initialize the connection pool parameters
	 */
	@SuppressWarnings("rawtypes")
	private void init() {
		// Load drivers
		this.loadDrivers(); 
		// Create a connection pool
		Iterator alldriver = drivers.iterator();
		while (alldriver.hasNext()) {
			this.createPools((DSConfigBean)alldriver.next());
			logger.info("create a connection pool...");
		}
		logger.info("Create a connection pool is completed...");

	}
	/**
	 *  Load drivers
	 */
	private void loadDrivers() {
		drivers = ConnectionlConfigManager.readConfigInfo();
		logger.info("Load drivers...");
	}
	/**
	 * Create a DB2 database connection
	 * @param path
	 * 		Database connection pool configuration file path---D:/JavaProject/Java_4.3/MyUtil/bin/org/me/pool/ds.config.xml
	 * 		String path = "ds.config.xml";
	 * 		String rpath = this.getClass().getResource("").getPath().substring(1)
	 * 		+ path;
	 * @param name
	 * 		 pool name
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param conn
	 * 		The maximum number of database connections--1000
	 */
	public void createDb2Conntion(String name,String database,String user,String pwd,int conn){
		String url = DBConfigConstants.DB2_URL.replace("port", "60000").replace("database", database);
		createDbConntion(DBConfigConstants.DB2_TYPE, DBConfigConstants.DB2_CONNON_DRIVER, url, user, pwd, conn, name);
	}
	/**
	 * Create a ORACLE database connection
	 * @param path
	 * 		Database connection pool configuration file path---D:/JavaProject/Java_4.3/MyUtil/bin/org/me/pool/ds.config.xml
	 * 		String path = "ds.config.xml";
	 * 		String rpath = this.getClass().getResource("").getPath().substring(1)
	 * 		+ path;
	 * @param name
	 * 		 pool name
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param conn
	 * 		The maximum number of database connections--1000
	 */
	public void createOracleXE(String name,String user,String pwd,int conn){
		String url = DBConfigConstants.ORECLE_XE_URL.replace("port", "1521");
		createDbConntion(DBConfigConstants.ORECLE_XE_TYPE, DBConfigConstants.ORECLE_XE_CONNON_DRIVER, url, user, pwd, conn, name);
	}
	/**
	 * Create a POSTGRESQL database connection
	 * @param path
	 * 		Database connection pool configuration file path---D:/JavaProject/Java_4.3/MyUtil/bin/org/me/pool/ds.config.xml
	 * 		String path = "ds.config.xml";
	 * 		String rpath = this.getClass().getResource("").getPath().substring(1)
	 * 		+ path;
	 * @param name
	 * 		 pool name
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param conn
	 * 		The maximum number of database connections--1000
	 */
	public void createPostgreSqlConntion(String name,String database,String user,String pwd,int conn){
		String url = DBConfigConstants.POSTGRESQL_URL.replace("port", "5432").replace("database", database).replace("database", database);
		createDbConntion(DBConfigConstants.POSTGRESQL_TYPE, DBConfigConstants.POSTGRESQL_CONNON_DRIVER, url, user, pwd, conn, name);
	
	}
	/**
	 * Create a MYSQL database connection
	 * @param path
	 * 		Database connection pool configuration file path---D:/JavaProject/Java_4.3/MyUtil/bin/org/me/pool/ds.config.xml
	 * 		String path = "ds.config.xml";
	 * 		String rpath = this.getClass().getResource("").getPath().substring(1)
	 * 		+ path;
	 * @param name
	 * 		 pool name
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param conn
	 * 		The maximum number of database connections--1000
	 */
	public void createSimpleMySqlConntion(String name,String database,String user,String pwd,int conn){
		String url = DBConfigConstants.MYSQL_URL.replace("port", "3306").replace("database", database);
		createDbConntion(DBConfigConstants.MYSQL_TYPE, DBConfigConstants.MYSQL_CONNON_DRIVER, url, user, pwd, conn, name);
	}
	
	/**
	 * Create a MYSQL database connection
	 * @param path
	 * 		Database connection pool configuration file path---D:/JavaProject/Java_4.3/MyUtil/bin/org/me/pool/ds.config.xml
	 * 		String path = "ds.config.xml";
	 * 		String rpath = this.getClass().getResource("").getPath().substring(1)
	 * 		+ path;
	 * @param name
	 * 		 pool name
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param conn
	 * 		The maximum number of database connections--1000
	 */
	public void createMySqlConntion(String name,String host,String port,String database,String user,String pwd,int conn){
		String url = DBConfigConstants.MYSQL_URL.replace("database", database).replace("localhost", host).replace("port", port);
		createDbConntion(DBConfigConstants.MYSQL_TYPE, DBConfigConstants.MYSQL_CONNON_DRIVER,url, user, pwd, conn, name);
	}
	
	
	/**
	 * Create a MYSQL database connection
	 * @param driver
	 * 		database driver
	 * @param url
	 * 		connection url
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param name
	 * 		 pool name
	 * @param conn
	 * 		The maximum number of database connections--1000
	 * @param path
	 * 		Database connection pool configuration file path---D:/JavaProject/Java_4.3/MyUtil/bin/org/me/pool/ds.config.xml
	 * 		String path = "ds.config.xml";
	 * 		String rpath = this.getClass().getResource("").getPath().substring(1)
	 */
	public void createMySqlConntion(String driver,String url,String user,String pwd,String name,int conn){
		createDbConntion(DBConfigConstants.MYSQL_TYPE, driver, url, user, pwd, conn, name);
	}
	/**
	 * Create a  database connection
	 * @param type
	 * 		Database Type[mysql,oracle,db2,postgresql,sqlserver]
	 * @param driver
	 * 		database driver
	 * @param url
	 * 		connection url
	 * @param user
	 * 		database user
	 * @param pwd
	 * 		database password
	 * @param conn
	 * 		The maximum number of database connections--1000
	 * @param name
	 * 		 pool name
	 * @param path
	 * 		Database connection pool configuration file path
	 */
	public void createDbConntion(String type,String driver,String url,String user,String pwd,int conn,String name){
		DSConfigBean dsb = new DSConfigBean();
		if(!holdDatabaseType(type)){
			logger.info(type.toUpperCase()+" database currently does not support,supported database"+holdedType());
			return;
		}
		dsb.setType(type);
		dsb.setName(name);
		dsb.setDriver(driver);
		dsb.setUrl(url);
		dsb.setUsername(user);
		dsb.setPassword(pwd);
		dsb.setMaxconn(conn);
		this.addConfig(dsb);
		logger.info("Add--"+name+"--"+type.toUpperCase()+" connection pool completed:");
	}
	
	public void addConfig(DSConfigBean dsb){
		ConnectionlConfigManager.addConfigInfo(dsb);
	}
	
	public void delConfig(String name){
		ConnectionlConfigManager.delConfigInfo(name);
	}
	
	private boolean holdDatabaseType(String type){
		for(String str:holdType){
			if(type.equalsIgnoreCase(str)){
				return true;
			}
		}
		return false;
		
	}
	private String holdedType(){
		String string = "[";
		for(String str:holdType){
			string = string+str+",";
		}
		string = string.substring(0, string.length()-1);
		string = string+"]";
		return string;
		
	}
}
