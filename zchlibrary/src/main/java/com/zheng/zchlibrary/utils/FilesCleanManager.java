package com.zheng.zchlibrary.utils;

import android.content.Context;

/**
 * 附件数据清理管理器
 */
public class FilesCleanManager {

	/**
	 * 获取应用附件缓存大小
	 * @return
	 */
	public static long getAppFilesSize(){

		long result = 0;

//		result+=FileUtils.size(AppConstants.APP_FILE_DOWNLOAD_FOLDER);
		
		return result;
	} 
	
	/**
	 * 清除应用缓存
	 */
	public static void cleanAppCache(Context context){

//		FileUtils.delete(AppConstants.APP_FILE_DOWNLOAD_FOLDER);
//
//		//清空附件数据库
//		FilesInfoTableHelper mHelper=new FilesInfoTableHelper(context);
//		mHelper.deleteAllFiles();

	}
	
}
