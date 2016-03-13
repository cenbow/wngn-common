package com.zzia.wngn.connection.pool;

public class DBConfigConstants {

	public final static String MYSQL_CONNON_DRIVER = "com.mysql.jdbc.Driver";
	public final static String MYSQL_TYPE = "mysql";
	public final static String MYSQL_URL = "jdbc:mysql://localhost:port/database?useUnicode=true&characterEncoding=utf-8";

	public final static String DB2_CONNON_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public final static String DB2_TYPE = "db2";
	public final static String DB2_URL = "jdbc:db2://localhost:port/database";

	public final static String ORECLE_CONNON_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public final static String ORECLE_TYPE = "oracle";
	public final static String ORECLE_URL = "jdbc:oracle:thin:@localhost:port:XE";

	public final static String ORECLE_XE_CONNON_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public final static String ORECLE_XE_TYPE = "oracle";
	public final static String ORECLE_XE_URL = "jdbc:oracle:thin:@localhost:port:XE";

	public final static String POSTGRESQL_CONNON_DRIVER = "org.postgresql.Driver";
	public final static String POSTGRESQL_TYPE = "postgreSql";
	public final static String POSTGRESQL_URL = "jdbc:postgresql://localhost:port/database?useUnicode=true&characterEncoding=utf-8";

}
