package com.ams.interfacewithoa.archive.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.ams.interfacewithoa.archive.dao.IRecoredDao;
import com.ams.interfacewithoa.archive.jdbc.ConnectionManager;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;

public class RecordDaoImpl implements IRecoredDao {
	private static Logger logger = Logger.getLogger(RecordDaoImpl.class);
	@Override
	public List<Map<String, Object>> getRecord(String tableName, String fields,
			String limit, JdbcConfig jdbcConfig) {
		List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append(fields).append(" FROM ").append(tableName).append(" WHERE ").append(limit);
		Connection conn = ConnectionManager.getInstance().getConnection(jdbcConfig);
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, Object> record = null;
		try {
			if(conn != null){
				stmt = conn.createStatement();
				rs = stmt.executeQuery(new String(sql));
				ResultSetMetaData meta = rs.getMetaData();
				while(rs.next()){
					record = new HashMap<String, Object>();
					for(int i = 1, size = meta.getColumnCount(); i <= size; i++){
						record.put(meta.getColumnName(i), rs.getObject(meta.getColumnName(i)));
					}
					records.add(record);
				}
			}
		} catch (SQLException e) {
			logger.error("��ȡ���ݼ�¼����sql-----" + new String(sql));
			logger.error("��ȡ���ݼ�¼����" + e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				ConnectionManager.getInstance().closeConnection(conn, stmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	@Override
	public void saveRecord(String tableName, Map<String, Object> record, JdbcConfig jdbcConfig) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ").append(tableName);
		List<Object> values = new ArrayList<Object>();
		sql.append("(");
		for(String key:record.keySet()){
			if("JKZT".equals(key) || "JKBZ".equals(key)){
				continue;
			}
			sql.append(key).append(",");
			values.add(record.get(key));
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") values(");
		for(int j = 0, size = values.size(); j < size; j++){
			sql.append("?").append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Connection conn = ConnectionManager.getInstance().getConnection(jdbcConfig);
		PreparedStatement stmt;
		try {
			if(conn != null){
				stmt = conn.prepareStatement(new String(sql));
				for(int i = 0, size = values.size(); i < size; i++){
					stmt.setObject(i+1, values.get(i));
				}
				stmt.execute();
			}
		} catch (SQLException e) {
			logger.error("�������ݼ�¼����sql-----" + new String(sql));
			logger.error("�������ݼ�¼����" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void update(JdbcConfig jdbcConfig, String tableName, String setString) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(tableName).append(" SET ").append(setString);
		Connection conn = ConnectionManager.getInstance().getConnection(jdbcConfig);
		Statement stmt;
		try {
			if(conn != null){
				stmt = conn.createStatement();
				stmt.execute(new String(sql));
			}
		} catch (SQLException e) {
			logger.error("�������ݼ�¼����sql-----" + new String(sql));
			logger.error("�������ݼ�¼����" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public String getPrjsys(String tableName, String prjcode, JdbcConfig jdbcConfig) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SYSCODE FROM " + tableName + " WHERE PRJCODE='" + prjcode + "'");
		Connection conn = ConnectionManager.getInstance().getConnection(jdbcConfig);
		Statement stmt = null;
		ResultSet rs = null;
		String prjsys = "";
		try {
			if(conn != null){
				stmt = conn.createStatement();
				rs = stmt.executeQuery(new String(sql));
				while(rs.next()){
					prjsys = rs.getString("SYSCODE");
				}
			}
		} catch (SQLException e) {
			logger.error("��ȡPRJSYS����sql-----" + new String(sql));
			logger.error("��ȡPRJSYS����" + e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				ConnectionManager.getInstance().closeConnection(conn, stmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return prjsys;
	}

	@Override
	public void saveSql(JdbcConfig jdbcConfig ,String etableName,String efilesyscode,String createtime,String abpath,
			String filename,String dfilesyscode,long filesize) {
		Connection conn = ConnectionManager.getInstance().getConnection(jdbcConfig);
		PreparedStatement stmt;
		try {
			conn.setAutoCommit(false);
			
			if(conn != null){
				String sql = "insert into "+etableName+" (syscode,createtime,filepath,filename,psyscode,filetype,filesize)"
						+ " values( '" + efilesyscode + "','" + createtime + "','" + abpath
						+ "','" + filename + "','" + dfilesyscode + "','1' ,"+ filesize +")";
				stmt = conn.prepareStatement(new String(sql));
				stmt.execute();
				conn.commit();
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
