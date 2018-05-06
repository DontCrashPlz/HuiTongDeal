package com.zheng.zchlibrary.utils;

import java.math.BigDecimal;
import android.text.TextUtils;

/**
 * 一般工具类
 * @author 碧空
 *
 */
public class CommonUtils {

	/**
	 * 转义sql语句
	 * @param sql
	 * @return
	 */
	public static String sqliteEscape(String sql){
		if (TextUtils.isEmpty(sql)) {
			return sql;
		}
		return sql.replaceAll("'", "''"); 
	}

	
	/**
	 * 默认的分钟格式化语句
	 */
	private static String DefaultMinuteFormat = "%1$02d:%2$02d";


	/**
	 * 获取格式化时间
	 * @param millisec 毫秒数
	 * @return
	 */
	public static String formatTimeMinute(int millisec) {
		
		if (millisec <= 0) {
			return String.format(DefaultMinuteFormat, 0, 0);
		}
		
		int minute, second;
		minute = ((millisec/1000) / 60) % 60;
		second = (millisec/1000) % 60;
		
		return String.format(DefaultMinuteFormat, minute, second);
	}

	/**
	 * 格式化单位
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "B";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
}
