package com.gohuinuo.web.sys.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gohuinuo.common.base.BaseEntity;

/**
 * 
 * @author 
 */

@SuppressWarnings({ "unused"})
@Table(name="sys_log")
public class SysLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	
    private String description; //description <>

	
    private String exception; //exception <异常信息>

	
    private String method; //method <操作方式>

	
    private String params; //params <操作提交的数据>

	
    private String remoteAddr; //remote_addr <操作IP地址>

	
    private String requestUri; //request_uri <请求URI>

	
    private String type; //type <日志类型>

	
    private String userAgent; //user_agent <用户代理>
    
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>

    @Transient
    public static final String TYPE_ACCESS = "1"; //操作日志
    @Transient
	public static final String TYPE_EXCEPTION = "2"; //异常日志

	public String getDescription() {
		return this.getString("description");
    }
   
    public void setDescription(String description) {
		this.set("description", description);
    }

	public String getException() {
		return this.getString("exception");
    }
   
    public void setException(String exception) {
		this.set("exception", exception);
    }

	public String getMethod() {
		return this.getString("method");
    }
   
    public void setMethod(String method) {
		this.set("method", method);
    }

	public String getParams() {
		return this.getString("params");
    }
   
    public void setParams(String params) {
		this.set("params", params);
    }

	public String getRemoteAddr() {
		return this.getString("remoteAddr");
    }
   
    public void setRemoteAddr(String remoteAddr) {
		this.set("remoteAddr", remoteAddr);
    }

	public String getRequestUri() {
		return this.getString("requestUri");
    }
   
    public void setRequestUri(String requestUri) {
		this.set("requestUri", requestUri);
    }

	public String getType() {
		return this.getString("type");
    }
   
    public void setType(String type) {
		this.set("type", type);
    }

	public String getUserAgent() {
		return this.getString("userAgent");
    }
   
    public void setUserAgent(String userAgent) {
		this.set("userAgent", userAgent);
    }

    public String getCreateBy() {
		return this.getString("createBy");
    }
   
    public void setCreateBy(String createBy) {
		this.set("createBy", createBy);
    }

	public Date getCreateDate() {
		return this.getDate("createDate");
    }
   
    public void setCreateDate(Date createDate) {
		this.set("createDate", createDate);
    }

}
