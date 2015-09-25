<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.MyActionEnter"
	import="org.springframework.context.support.ClassPathXmlApplicationContext"
	import="org.springframework.context.ApplicationContext"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	ApplicationContext appContext = new ClassPathXmlApplicationContext();
	
	out.write( new MyActionEnter( request, appContext.getResource(
			"classpath:/ueditor.config.json").getInputStream() ).exec() );
	
%>