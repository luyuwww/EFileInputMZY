package com.ams.interfacewithoa.archive.service.impl;

import java.util.List;
import java.util.Map;

import com.ams.interfacewithoa.archive.config.ConfigManager;
import com.ams.interfacewithoa.archive.dao.IRecoredDao;
import com.ams.interfacewithoa.archive.dao.impl.RecordDaoImpl;
import com.ams.interfacewithoa.archive.jdbc.JdbcConfig;
import com.ams.interfacewithoa.archive.service.IFileService;

public class FileServiceImpl implements IFileService {
	private String tableName = ConfigManager.getInstance().getProperty("file.tableName");
	private String unitsys = ConfigManager.getInstance().getProperty("unitsys");
	private String libcode = ConfigManager.getInstance().getProperty("libcode");
	private String fields = ConfigManager.getInstance().getProperty("file.fields");
	IRecoredDao recordDao = new RecordDaoImpl();
	@Override
	public List<Map<String,Object>> getFileData(JdbcConfig jdbcConfig) {
		List<Map<String,Object>> list = recordDao.getRecord(tableName, fields, "1=1", jdbcConfig);
		
		return list;
	}

	@Override
	public void saveFile(Map<String, Object> file, JdbcConfig jdbcConfig) {
		String tableNameF = "D_FILE" + libcode + "_" + unitsys;
		file.put("UNITSYS", unitsys);
		this.recordDao.saveRecord(tableNameF, file, jdbcConfig);
	}

	@Override
	public void updateState(JdbcConfig jdbcConfig, String syscode) {
		this.recordDao.update(jdbcConfig, tableName, "JKZT = 1 WHERE SYSCODE='" + syscode + "'");
	}
	@Override
	public void updateDate(JdbcConfig jdbcConfig, String syscode) {
		this.recordDao.update(jdbcConfig, tableName, "DQSJ = SYSDATE WHERE SYSCODE='" + syscode + "'");
	}

	@Override
	public String getPrjsysByPrjcode(String prjcode, JdbcConfig jdbcConfig) {
		String tableNameP = "D_PRJ" + libcode + "_" + unitsys;
		return this.recordDao.getPrjsys(tableNameP,prjcode, jdbcConfig);
	}

}
