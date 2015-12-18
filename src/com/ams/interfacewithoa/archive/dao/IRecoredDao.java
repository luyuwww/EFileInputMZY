package com.ams.interfacewithoa.archive.dao;

import java.util.List;
import java.util.Map;

import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;

/**
 * 锟斤拷锟斤拷锟侥匡拷志没锟斤拷涌锟�
 * 
 * @author Administrator
 *
 */
public interface IRecoredDao {
	List<Map<String, Object>> getRecord(String tableName, String fields, String limit, JdbcConfig jdbcConfig);

	void saveRecord(String tableName, Map<String, Object> record, JdbcConfig jdbcConfig);

	void update(JdbcConfig jdbcConfig, String tableName, String string);

	String getPrjsys(String tableName, String prjcode, JdbcConfig jdbcConfig);

	void saveSql(JdbcConfig jdbcConfig, String etableName, String efilesyscode, String createtime, String abpath,
			String filename, String dfilesyscode, long filesize);
}
