package com.gohuinuo.common.exception;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.gohuinuo.common.utils.LogUtils;

@SuppressWarnings("unused")
public class CustomSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		
		LogUtils.logPageError(request);
		e.printStackTrace();
		
		return getModelAndView("error/error", e, request);
	}
	/**
	 * 判断是否是Json异步请求
	 */
	private boolean isAjaxJson(HttpServletRequest request){
		return request.getHeader("accept").indexOf("application/json") > -1;
	}
	
	/**
	 * 判断是否是XML异步请求
	 */
	private boolean isAjaxXml(HttpServletRequest request){
		return  request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1;
	}

}
