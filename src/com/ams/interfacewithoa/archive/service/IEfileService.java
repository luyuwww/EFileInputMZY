package com.ams.interfacewithoa.archive.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.ams.interfacewithoa.archive.ftp.FtpConfig;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;
/**
 *  电子文件条目服务接口
 * @author Administrator
 *
 */
public interface IEfileService {
	List<Map<String,Object>> getEfileData(String psyscode, JdbcConfig jdbcConfig);
	void saveEfile(Map<String,Object> efile, JdbcConfig jdbcConfig);
	String move(FtpConfig ftpConfig, String psyscode, String filepath, FtpConfig amsFtpConfig);
	void updateState(JdbcConfig jdbcConfig, String syscode);
	void insertEfile(JdbcConfig jdbcConfig,File file,String dfilesyscode);
}
