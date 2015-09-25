package com.gohuinuo.common.spring.annotation;

public enum LogType {
	
	SELECT("查询");
	
	private final String value;
	
	LogType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
