package com.gohuinuo.common.constant;

import com.gohuinuo.common.utils.DateUtils;
import com.gohuinuo.common.utils.SysConstant;

public class Global {
	
	/**
	 *  下载的路径
	* @param groupFile 分组文件夹名
	 */
	public static String getDownloadRootPath(String groupFile){
		String dateFile = DateUtils.getDate();
		StringBuilder builder = new StringBuilder();
		builder.append(SysConstant.getValue("download.rootPath")+"/");
		builder.append(groupFile+"/");
		builder.append(dateFile+"/");
		return builder.toString();
	}
	
	public static String getUploadRootPath(String groupFile){
		String dateFile = DateUtils.getDate();
		StringBuilder builder = new StringBuilder();
		builder.append(SysConstant.getValue("upload.rootPath")+"/");
		builder.append(groupFile+"/");
		builder.append(dateFile+"/");
		return builder.toString();
	}
	
}
