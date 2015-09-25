package com.gohuinuo.common.beetl.utils;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.gohuinuo.common.spring.utils.SpringContextHolder;


public class BeetlUtils {
	
	private static final BeetlGroupUtilConfiguration beetlConfig = SpringContextHolder.getBean("beetlConfig");
	private static final Map<String, Object> sharedVars = new HashMap<String, Object>();

	/**
	 * 获得beetl模板对象
	* @return
	 */
	public static final GroupTemplate getBeetlGroupTemplate(){
		return beetlConfig.getGroupTemplate();
	}
	
	
	/**
	 * 设置beetl共享变量
	* @param key 
	* @param value
	 */
	public static void addBeetlSharedVars(String key,Object value){
		sharedVars.put(key, value);
		getBeetlGroupTemplate().setSharedVars(sharedVars);
	}
	
	/**
	 * 获得beetl中的共享变量
	* @param key
	* @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBeetlSharedVars(String key){
		Map<String, Object> map = getBeetlGroupTemplate().getSharedVars();
		return (T)map.get(key);
	}
	
}
