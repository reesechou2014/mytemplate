package com.gohuinuo.common.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gohuinuo.common.excel.template.PoiTemplate;
import com.gohuinuo.common.excel.template.utils.PoiUtil;
import com.gohuinuo.common.utils.FileUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExcelUtils {

	/**
	 * 导出excel,普通形式(流的形式直接下载)
	 * @param response
	 * @param fileName 下载的excel文件名
	 * @param data 数据
	 * @param titleMap (key:"列名",value = "属性名")
	 */
	public static void exportExcel(HttpServletResponse response,
			String fileName, List<?> data, Map titleMap) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PoiUtil.writeExcel(data, os, titleMap);
		os.flush();
		byte[] buf = os.toByteArray();
		InputStream is = new ByteArrayInputStream(buf, 0, buf.length);
		FileUtils.downloadFile(response, is, fileName);
		is.close();
		os.close();
	}

	/**
	 * 导出excel,普通形式(先保存一份到服务器，之后从服务器下载)
	 * @param response
	 * @param filePath 在服务器中导出的excel文件路径
	 * @param fileName 下载的excel名字
	 * @param data 数据
	 * @param titleMap (key:"列名",value = "属性名")
	 */
	public static void exportExcel(HttpServletResponse response,
			String filePath, String fileName, List<?> data, Map titleMap)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(filePath));
		PoiUtil.writeExcel(data, fos, titleMap);
		fos.close();
		FileUtils.downloadFile(response, filePath, fileName);
	}

	/**
	 * 导出excel,模板形式(流方式直接下载)
	 * @param response
	 * @param templatePath 模板excel的路径
	 * @param fileName 导出excel文件名
	 * @param data 数据map
	 */
	public static void exportExcel(HttpServletResponse response,
			String templatePath,String fileName, Map data) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();;
		PoiTemplate template = new PoiTemplate(templatePath,os);
		template.addMap(data);
		template.writeExcel();
		os.flush();
		byte[] buf = os.toByteArray();
		InputStream is = new ByteArrayInputStream(buf, 0, buf.length);
		FileUtils.downloadFile(response, is, fileName);
		is.close();
		os.close();
	}
	
	/**
	 * 导出excel,模板形式(先保存一份到服务器，之后从服务器下载)
	 * @param response
	 * @param templatePath 模板excel的路径
	 * @param outPath 保存到服务器的位置
	 * @param fileName 导出excel文件名
	 * @param data 数据map
	 */
	public static void exportExcel(HttpServletResponse response,
			String templatePath,String outPath,String fileName, Map data) throws Exception{
		PoiTemplate template = new PoiTemplate(templatePath,outPath);
		template.addMap(data);
		template.writeExcel();
		FileUtils.downloadFile(response, outPath, fileName);
	}

}
