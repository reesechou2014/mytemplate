package com.gohuinuo.common.spring.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gohuinuo.common.spring.annotation.Log;
import com.gohuinuo.common.utils.IPUtils;
import com.gohuinuo.web.sys.model.SysLog;
import com.gohuinuo.web.sys.service.SysLogService;
import com.gohuinuo.web.sys.utils.SysUserUtils;

@Aspect
@Component
@Order(0)
public class SystemLogAspect {

	@Resource
	private SysLogService sysLogService;

	// 本地异常日志记录对象
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SystemLogAspect.class);

	@Pointcut("@annotation(com.gohuinuo.common.spring.annotation.Log)")
	public void accessAspect() {
	}
	
	@Pointcut("execution(* com.gohuinuo.web..*Service.*(..))")
	public void throwingAspect(){
	}

	@AfterReturning(value = "accessAspect()", returning = "rtv")
	public void doAfterReturning(JoinPoint joinPoint, Object rtv) {
		saveLog(joinPoint, null);
	}
	
	@AfterThrowing(value="throwingAspect()",throwing="e")
	public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
		saveLog(joinPoint, e);
	}
	
	protected void saveLog(JoinPoint joinPoint,Throwable e){
		try {
			HttpServletRequest request = SysUserUtils.getCurRequest();
			SysLog log = new SysLog();
			// 判断参数
			if (joinPoint.getArgs() != null) {
				StringBuffer rs = new StringBuffer();
				for (int i = 0, len = joinPoint.getArgs().length; i < len; i++) {
					Object info = joinPoint.getArgs()[i];
					if (info != null) {
						String paramType = info.getClass().getSimpleName();
						rs.append("[参数" + (i + 1) + "，类型:" + paramType + "，值:"
								+ info.toString() + "]");
					} else {
						rs.append("[参数" + (i + 1) + "，值:null]");
					}
				}
				log.setParams(rs.toString());
			}
			log.setRemoteAddr(IPUtils.getClientAddress(request));
			log.setRequestUri(request.getRequestURI());
			log.setMethod(request.getMethod());
			log.setUserAgent(request.getHeader("user-agent"));
			log.setException(e==null?null:e.toString());
			log.setType(e==null?SysLog.TYPE_ACCESS:SysLog.TYPE_EXCEPTION);
			Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();
			Log sclog = m.getAnnotation(Log.class);
			if (sclog != null) log.setDescription(sclog.description());
			//log保存到数据库
			sysLogService.saveSysLog(log);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

}
