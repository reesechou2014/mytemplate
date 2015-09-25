package com.gohuinuo.common.utils;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class SysConstant {
	private static Resource resource = new ClassPathResource("resources.properties");
	private static Properties props = null;
	static{
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Properties getConfigPropertis() {
		return props;
	}
	public static String getValue(String key) {
		if(null!=key&&!"".equals(key)&&props.containsKey(key)){
			return (String) props.get(key);
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(SysConstant.getValue("mybatis.basePackage"));
	}

}
