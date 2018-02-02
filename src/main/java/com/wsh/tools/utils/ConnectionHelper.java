package com.wsh.tools.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConnectionHelper {
	
	private static final Log LOG=LogFactory.getLog(ConnectionHelper.class);
	
	private static final String MYSQL_DRIVER = SystemConfig.getString("jdbc.driver");
	private static final String MYSQL_URL = SystemConfig.getString("jdbc.url");
	private static final String MYSQL_USERNAME = SystemConfig.getString("jdbc.username");
	private static final String MYSQL_PASSWORD = SystemConfig.getString("jdbc.password");

	static{
		DriverManager.setLoginTimeout(10);
	}
	
	public static Connection createConnection() throws ClassNotFoundException, SQLException{
		Connection conn=null;
		Class.forName(MYSQL_DRIVER);
		conn=DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		return conn;
	}
	
	public static boolean testConnection(){
		boolean result=true;
		try {
			Connection connection=createConnection();
			connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			LOG.info(e);
			result=false;
		}
		return result;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Connection conn=ConnectionHelper.createConnection();
		System.out.println(conn);
		conn.close();
	}

}
