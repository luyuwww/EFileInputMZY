package com.ams.interfacewithoa.archive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.ams.interfacewithoa.archive.config.ConfigManager;
import com.ams.interfacewithoa.archive.dao.impl.RecordDaoImpl;
/**
 * jdbc数据库连接管理类
 * @author Administrator
 *
 */
public class ConnectionManager {
	private static Logger logger = Logger.getLogger(RecordDaoImpl.class);
	private static ConnectionManager connManager = null;
	private static String driverClass = "oracle.jdbc.xa.client.OracleXADataSource";
	
	private ConnectionManager(){
	}
	public static ConnectionManager getInstance(){
		if(connManager == null){
			synchronized(ConnectionManager.class){
				connManager = new ConnectionManager();
			}
		}
		return connManager;
	}
	public Connection getConnection(JdbcConfig jdbcConfig){
		try {
			Class.forName(driverClass);
			return DriverManager.getConnection(jdbcConfig.getJdbc_url(), jdbcConfig.getJdbc_username(), jdbcConfig.getJdbc_password());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("获取数据库连接出错：" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException{
		if(stmt != null){
			stmt.close();
		}
	}
	public boolean testConnection(JdbcConfig jdbcConfig) {
		Connection conn = getConnection(jdbcConfig);
		if(conn == null){
			return false;
		}else{
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.execute("select 1 from dual");
				closeConnection(conn, stmt, null);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
}
