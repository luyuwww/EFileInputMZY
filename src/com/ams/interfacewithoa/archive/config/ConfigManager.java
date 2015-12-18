package com.ams.interfacewithoa.archive.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ams.interfacewithoa.archive.dao.impl.RecordDaoImpl;

/**
 * 配置文件管理类
 * @author Administrator
 *
 */
public class ConfigManager {
	private static Logger logger = Logger.getLogger(RecordDaoImpl.class);
	private static final Properties properties = new Properties();
	public static ConfigManager config = null;
	private ConfigManager() throws FileNotFoundException, IOException {
		properties.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));
	}
	public static ConfigManager getInstance() {
		if(config == null){
			synchronized(ConfigManager.class){
				try {
					config = new ConfigManager();
				} catch (FileNotFoundException e) {
					logger.error("获取配置文件出错，找不到配置文件：" + e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.error("读取配置文件出错：" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return config;
	}
	public String getProperty(String key){
		return properties.getProperty(key);
	}
}
