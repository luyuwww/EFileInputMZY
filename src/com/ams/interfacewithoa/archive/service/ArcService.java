package com.ams.interfacewithoa.archive.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ams.interfacewithoa.archive.config.ConfigManager;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;
import com.ams.interfacewithoa.archive.service.IEfileService;
import com.ams.interfacewithoa.archive.service.IFileService;
import com.ams.interfacewithoa.archive.service.impl.EfileServiceImpl;
import com.ams.interfacewithoa.archive.service.impl.FileServiceImpl;
import com.ams.interfacewithoa.archive.util.UUIDHexGenerator;

/**
 * 归档接口主程序类
 * 
 *
 */
public class ArcService {
	private static Logger logger = Logger.getLogger(ArcService.class);
	public static IFileService fileService = new FileServiceImpl();
	public static IEfileService efileService = new EfileServiceImpl();
	public static UUIDHexGenerator idGenerator = new UUIDHexGenerator();
	private static String jdbc_url = ConfigManager.getInstance().getProperty("jdbc.url");
	private static String jdbc_username = ConfigManager.getInstance().getProperty("jdbc.username");
	private static String jdbc_password = ConfigManager.getInstance().getProperty("jdbc.password");
	private static String pathname  = ConfigManager.getInstance().getProperty("efile.path");

	public static void main(String[] args) {
		PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
		JdbcConfig jdbcConfig = new JdbcConfig(jdbc_url, jdbc_username, jdbc_password);

		if (ConfigManager.getInstance() == null) {
			logger.error("读取配置文件出错，请检查配置文件！");
			return;
		}

		String dfilesyscode = "";
		String keyword = "";
		// 获取文件条目
		List<Map<String, Object>> dfiles = fileService.getFileData(jdbcConfig);
		if (dfiles == null) {
			logger.error("获取Dfile为空。");
			return;
		} else {
			for (Map<String, Object> dfile : dfiles) {
				// 获取Dfile的 SYSCODE KEYWORD
				dfilesyscode = (String) dfile.get("SYSCODE");
				keyword = (String) dfile.get("KEYWORD");
				
				if (null == keyword){
					logger.error("有keyword为空,检查DFile");
					return;
				}
				//档号命名文件夹下的文件
				File efilesecond = new File(pathname + keyword);
				if (efilesecond.exists() && efilesecond.isDirectory()) {
					File[] tempList = efilesecond.listFiles();
					for (File file : tempList) {
						efileService.insertEfile(jdbcConfig, file, dfilesyscode);
					}
				}
				//以档号命名的文件
				File efilefirst = new File(pathname);
				File[] tempList = efilefirst.listFiles();

				if (null == tempList) {
					logger.error("获取文件为空,检查文件路径");
					return;
				}
				for (File file : tempList) {
					if (!file.isDirectory()) {
						if (file.getName().contains(keyword)) {
							efileService.insertEfile(jdbcConfig, file, dfilesyscode);
						}
					}
				}
			}
		}
		logger.info("归档接口执行完成。");
		System.exit(0);
	}

}
