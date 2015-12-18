package com.ams.interfacewithoa.archive.service.impl;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.ams.interfacewithoa.archive.config.ConfigManager;
import com.ams.interfacewithoa.archive.dao.IRecoredDao;
import com.ams.interfacewithoa.archive.dao.impl.RecordDaoImpl;
import com.ams.interfacewithoa.archive.ftp.FtpConfig;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;
import com.ams.interfacewithoa.archive.service.IEfileService;
import com.ams.interfacewithoa.archive.util.DateHelper;
import com.ams.interfacewithoa.archive.util.UUIDHexGenerator;

public class EfileServiceImpl implements IEfileService {
	private String tableName = ConfigManager.getInstance().getProperty("efile.tableName");
	private static String etableName = ConfigManager.getInstance().getProperty("efile.tableName");
	public static UUIDHexGenerator idGenerator = new UUIDHexGenerator();
	private String unitsys = ConfigManager.getInstance().getProperty("unitsys");
	private String libcode = ConfigManager.getInstance().getProperty("libcode");
	private String fields = ConfigManager.getInstance().getProperty("efile.fields");
	IRecoredDao recordDao = new RecordDaoImpl();
	@Override
	public List<Map<String, Object>> getEfileData(String psyscode, JdbcConfig jdbcConfig) {
		return recordDao.getRecord(tableName, fields, " JKBZ = 0 and PSYSCODE = '" + psyscode + "'", jdbcConfig);
	}

	@Override
	public void saveEfile(Map<String, Object> efile, JdbcConfig jdbcConfig) {
		String tableNameE = "D_EFILE" + libcode + "_" + unitsys;
		efile.put("UNITSYS", unitsys);
		this.recordDao.saveRecord(tableNameE, efile, jdbcConfig);
	}

	//电子文件已放到存储点，无需处理
	@Override
	public String move(FtpConfig ftpConfig, String psyscode, String filepath, FtpConfig amsFtpConfig) {
//		String fileType = filepath.substring(filepath.lastIndexOf(".")+1,filepath.length());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date today = new Date();
//		String dateStr = sdf.format(today);
//		String newfilepath = this.unitsys + File.separator + libcode + File.separator + dateStr.replace('-', File.separatorChar) + File.separator + psyscode;
//		String douRandom = Double.toString(Math.random());
//		String filename = douRandom.substring(douRandom.indexOf('.') + 1,
//				douRandom.length()) + "." + fileType;
//		String tempDir = System.getProperty("user.dir");
//		FtpManager.getInstance().downFile(ftpConfig, filepath, filename, tempDir + File.separator + newfilepath);
//		File file = new File(tempDir + File.separator + newfilepath);
//		try {
//			FtpManager.getInstance().uploadFile(amsFtpConfig, newfilepath, filename, new FileInputStream(file));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		return newfilepath;
		return null;
	}

	@Override
	public void updateState(JdbcConfig jdbcConfig, String syscode) {
		this.recordDao.update(jdbcConfig, tableName, "JKBZ = 1 WHERE SYSCODE='" + syscode + "'");
	}

	@Override
	public void insertEfile(JdbcConfig jdbcConfig,File file,String dfilesyscode) {
		String efilesyscode = idGenerator.generate();;
		String abpath = file.getAbsolutePath().toString().substring(3, file.getAbsolutePath().length()).replaceAll("\\\\", "/");
		String filename = file.getName();
		String createtime = DateHelper.generateTime();
		long filesize = FileUtils.sizeOf(file);
		this.recordDao.saveSql( jdbcConfig , etableName, efilesyscode, createtime, abpath, filename, dfilesyscode, filesize);
	}

}
