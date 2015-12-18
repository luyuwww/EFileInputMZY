package com.ams.interfacewithoa.archive.service;

import java.util.List;
import java.util.Map;

import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;

/**
 * 文件条目服务接口
 * @author Administrator
 *
 */
public interface IFileService {
	List<Map<String,Object>> getFileData(JdbcConfig jdbcConfig);
	void saveFile(Map<String,Object> file, JdbcConfig jdbcConfig);
	void updateState(JdbcConfig jdbcConfig, String syscode);
	void updateDate(JdbcConfig jdbcConfig, String syscode);
	String getPrjsysByPrjcode(String string, JdbcConfig jdbcConfig);
}
