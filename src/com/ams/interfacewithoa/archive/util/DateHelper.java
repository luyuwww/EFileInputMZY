/*
 * 创建日期 2007-2-3
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.ams.interfacewithoa.archive.util;

import java.text.SimpleDateFormat;
import java.util.Date;

//import com.platform.fileManipulation.FileManiConstant;


public class DateHelper {
	
	public static String generateTime(){
		String timeStr = new String();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		timeStr = sdf.format(time);
		
		return timeStr;
	}
}
