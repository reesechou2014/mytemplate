package com.gohuinuo.web.sys.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gohuinuo.common.beetl.utils.BeetlUtils;
import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.common.spring.utils.SpringContextHolder;
import com.gohuinuo.common.utils.CacheUtils;
import com.gohuinuo.common.utils.TreeUtils;
import com.gohuinuo.web.sys.model.SysOffice;
import com.gohuinuo.web.sys.model.SysResource;
import com.gohuinuo.web.sys.model.SysRole;
import com.gohuinuo.web.sys.model.SysUser;
import com.gohuinuo.web.sys.service.SysOfficeService;
import com.gohuinuo.web.sys.service.SysResourceService;
import com.gohuinuo.web.sys.service.SysRoleService;
import com.gohuinuo.web.sys.service.SysUserService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;

/**
 * @ClassName:SysUserUtils
 * @date:2015年2月4日 下午8:12:41
 * @author  ?
 */
public class SysUserUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SysUserUtils.class);

	static SysResourceService sysResourceService = SpringContextHolder.getBean("sysResourceService");
	static SysUserService sysUserService = SpringContextHolder.getBean("sysUserService");
	static SysRoleService sysRoleService = SpringContextHolder.getBean("sysRoleService");
	static SysOfficeService sysOfficeService = SpringContextHolder.getBean("sysOfficeService");

	/**
	 * 设置用户的认证
	 */
	public static void setUserAuth() {
		//菜单树
		getUserMenus();
		//角色列表
		getUserRoles();
		//用户机构列表
		getUserOffice();
		//用户持有的数据范围
		getUserDataScope();
	}
	
	/**
	 * 登录用户持有的资源
	* @return
	 */
	public static Map<String, SysResource> getUserResources(){
		SysUser sysUser = getCacheLoginUser();
		Map<String, SysResource> userResources = CacheUtils.get(
				Constant.CACHE_SYS_RESOURCE, Constant.CACHE_USER_RESOURCE
						+ sysUser.getId());
		if (userResources == null) {
			if (sysUser.isAdmin()) {
				userResources = BeetlUtils.getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
			} else {
				List<SysResource> userRes = sysResourceService.findUserResourceByUserId(sysUser);
				userResources = new LinkedHashMap<String, SysResource>();
				for(SysResource res : userRes){
					if(StringUtils.isBlank(res.getUrl())){
						userResources.put(res.getId().toString(), res);
					}else{
						userResources.put(res.getUrl(), res);
					}
				}
			}
			CacheUtils.put(Constant.CACHE_SYS_RESOURCE,
					Constant.CACHE_USER_RESOURCE + sysUser.getId(),
					userResources);
		}
		return userResources;
	}
	
	/**
	 * 登录用户菜单
	 */
	public static List<SysResource> getUserMenus(){
		SysUser sysUser = getCacheLoginUser();
		List<SysResource> userMenus = CacheUtils.get(
				Constant.CACHE_SYS_RESOURCE,
				Constant.CACHE_USER_MENU + sysUser.getId());
		if (userMenus == null) {
			Map<String, SysResource> userResources = getUserResources();
			userMenus = Lists.newArrayList();
			for(SysResource res : userResources.values()){
				if(Constant.RESOURCE_TYPE_MENU.equals(res.getType())){
					userMenus.add(res);
				}
			}
			userMenus = TreeUtils.toTreeNodeList(userMenus,SysResource.class);
			CacheUtils.put(Constant.CACHE_SYS_RESOURCE,
					Constant.CACHE_USER_MENU + sysUser.getId(), userMenus);
		}
		return userMenus;
	}
	
	/**
	 * 登录用户的角色
	 */
	public static List<SysRole> getUserRoles(){
		SysUser sysUser = getCacheLoginUser();
		List<SysRole> userRoles = CacheUtils.get(
				Constant.CACHE_SYS_ROLE,
				Constant.CACHE_USER_ROLE + sysUser.getId());
		if(userRoles == null){
			if(sysUser.isAdmin()){
				userRoles = sysRoleService.select(new SysRole());
			}else{
				userRoles = sysRoleService.findUserRoleListByUserId(sysUser.getId());
			}
			CacheUtils.put(Constant.CACHE_SYS_ROLE,
						Constant.CACHE_USER_ROLE + sysUser.getId(), userRoles);
		}
		return userRoles;
	}
	

	/**
	 * 登录用户的角色map形式
	 */
	public static Map<Long, SysRole> getUserRolesMap(){
		List<SysRole> list = SysUserUtils.getUserRoles();
		Map<Long, SysRole> userRolesMap = Maps.uniqueIndex(list, new Function<SysRole, Long>() {
			@Override
			public Long apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return userRolesMap;
	}
	
	
	/**
	 * 登录用户的机构
	* @return
	 */ 
	public static List<SysOffice> getUserOffice(){
		SysUser sysUser = getCacheLoginUser();
		List<SysOffice> userOffices = CacheUtils.get(
				Constant.CACHE_SYS_OFFICE,
				Constant.CACHE_USER_OFFICE + sysUser.getId());
		if(userOffices == null){
			SysOffice office = new SysOffice();
			if(sysUser.isAdmin()){
				userOffices = sysOfficeService.select(office);
			}else{
				office.setUserDataScope(SysUserUtils.dataScopeFilterString(null, null));
				userOffices = sysOfficeService.findEntityListByDataScope(office);
			}
			CacheUtils.put(Constant.CACHE_SYS_OFFICE,
					Constant.CACHE_USER_OFFICE + sysUser.getId(), userOffices);
		}
		return userOffices;
	}
	
	/**
	 * 用户持有的数据范围,包含数据范围小的
	 */
	public static List<String> getUserDataScope(){
		SysUser sysUser = getCacheLoginUser();
		List<String> dataScope = CacheUtils.get(Constant.CACHE_SYS_OFFICE, 
				Constant.CACHE_USER_DATASCOPE+sysUser.getId());
		if(dataScope == null){
			dataScope = Lists.newArrayList();
			if(!sysUser.isAdmin()){
				List<Integer> dc = Lists.transform(getUserRoles(), new Function<SysRole, Integer>() {
					@Override
					public Integer apply(SysRole sysRole) {
						return Integer.parseInt(sysRole.getDataScope());
					}
				});
				int[] dataScopes = Ints.toArray(dc);
				if(dataScopes.length == 0) return dataScope;
				int min = Ints.min(dataScopes);
				for(int i = min,len = Integer.parseInt(Constant.DATA_SCOPE_CUSTOM);i<=len;i++){
					dataScope.add(i+"");
				}
			}else{
				dataScope = Constant.DATA_SCOPE_ADMIN;
			}
			CacheUtils.put(Constant.CACHE_SYS_OFFICE, 
				Constant.CACHE_USER_DATASCOPE+sysUser.getId(), dataScope);
		}
		return dataScope;
	}
	
	//针对按明细设置自动赋权机构,仅且只有按明细设置的角色
	public static Long autoAddOfficeToRole(){
		List<String> userScope = getUserDataScope();
		int count = 0 ;
		for(String s : userScope){
			if(StringUtils.equals(s, Constant.DATA_SCOPE_CUSTOM)){
				count++;
			}
		}
		return count == userScope.size()?getUserRoles().get(0).getId():null;
	} 
	
	/**
	 * 数据范围过滤
	 * @param user 当前用户对象
	 * @param officeAlias 机构表别名
	 * @param userAlias 用户表别名，传递空，忽略此参数
	 * @param field field[0] 用户表id字段名称 为了减少中间表连接
	 * @return (so.office id=... or .. or)
	 */
	public static String dataScopeFilterString(String officeAlias, String userAlias,String... field){
		SysUser sysUser = getCacheLoginUser();
		if(StringUtils.isBlank(officeAlias)) officeAlias = "sys_office";
		//用户持有的角色
		List<SysRole> userRoles = getUserRoles();
		//临时sql保存
		StringBuilder tempSql = new StringBuilder();
		//最终生成的sql
		String dataScopeSql = "";
		if(!sysUser.isAdmin()){
			for(SysRole sr : userRoles){
				if(StringUtils.isNotBlank(officeAlias)){
					boolean isDataScopeAll = false;
					if (Constant.DATA_SCOPE_ALL.equals(sr.getDataScope())){
						isDataScopeAll = true;
					}
					else if (Constant.DATA_SCOPE_COMPANY_AND_CHILD.equals(sr.getDataScope())){
						//so.id=1 or so.parentIds like '0,1,%'
						tempSql.append(" or "+officeAlias+".id="+sysUser.getCompanyId());
						SysOffice sysOffice = sysOfficeService.selectByPrimaryKey(sysUser.getCompanyId());
						tempSql.append(" or "+officeAlias+".parent_ids like '"+sysOffice.getParentIds()+sysOffice.getId()+",%'");
					}
					else if (Constant.DATA_SCOPE_COMPANY.equals(sr.getDataScope())){
						//or so.id=1 or (so.parent_id=1 and so.type=2)
						tempSql.append(" or "+officeAlias+".id="+sysUser.getCompanyId());
						tempSql.append(" or ("+officeAlias+".parent_id="+sysUser.getCompanyId());
						tempSql.append(" and "+officeAlias+".type=2)");
					}
					else if (Constant.DATA_SCOPE_OFFICE_AND_CHILD.equals(sr.getDataScope())){
						//or so.id=5 or so.parentIds like '0,1,5,%'
						tempSql.append(" or "+officeAlias+".id="+sysUser.getOfficeId());
						SysOffice sysOffice = sysOfficeService.selectByPrimaryKey(sysUser.getOfficeId());
						tempSql.append(" or "+officeAlias+".parent_ids like '"+sysOffice.getParentIds()+sysOffice.getId()+",%'");
					}
					else if (Constant.DATA_SCOPE_OFFICE.equals(sr.getDataScope())){
						//or so.id=5
						tempSql.append(" or "+officeAlias+".id="+sysUser.getOfficeId());
					}
					else if (Constant.DATA_SCOPE_CUSTOM.equals(sr.getDataScope())){
						//or so.id in (1,2,3,4,5)
						List<Long> offices = sysOfficeService.findUserDataScopeByUserId(sysUser.getId());
						if(offices.size() == 0) offices.add(-1L);
						tempSql.append(" or "+officeAlias+".id in ("+StringUtils.join(offices, ",")+")");
					}
					if (!isDataScopeAll){
						if (StringUtils.isNotBlank(userAlias)){
							// or su.id=22
							if(field==null || field.length==0) field[0] = "id";
							tempSql.append(" or "+userAlias+"."+field[0]+"="+sysUser.getId());
						}else {
							tempSql.append(" or "+officeAlias+".id is null");
						}
					}else{
						// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
						tempSql.delete(0, tempSql.length());
						break;
					}
				}
			}// for end
			
			if(StringUtils.isNotBlank(tempSql)){
				dataScopeSql = "("+tempSql.substring(tempSql.indexOf("or")+2, tempSql.length())+")";
			}
		}
		return dataScopeSql;
	}
	
	/**
	 * 清除缓存中用户认证
	 */
	public static void clearAllCachedAuthorizationInfo(List<Long> userIds) {
		if(CollectionUtils.isNotEmpty(userIds)){
			for (Long userId : userIds) {
				boolean evictRes = CacheUtils.remove(Constant.CACHE_SYS_RESOURCE,
						Constant.CACHE_USER_RESOURCE + userId);
				
				boolean evictMenu = CacheUtils.remove(Constant.CACHE_SYS_RESOURCE,
						Constant.CACHE_USER_MENU + userId);
				
				boolean evictRole = CacheUtils.remove(Constant.CACHE_SYS_ROLE,
						Constant.CACHE_USER_ROLE + userId);
				
				boolean evictOffice = CacheUtils.remove(Constant.CACHE_SYS_OFFICE,
						Constant.CACHE_USER_OFFICE + userId);
				
				boolean evictScope = CacheUtils.remove(Constant.CACHE_SYS_OFFICE, 
						Constant.CACHE_USER_DATASCOPE+userId);
				
				if(evictRes&&evictMenu&&evictRole&&evictOffice&&evictScope){
					logger.debug("用户"+userId+"的菜单、资源、角色、机构、数据范围缓存全部删除");
				}
			}
		}
	}
	
	public static void clearCacheResource(){
		CacheUtils.clear(Constant.CACHE_SYS_RESOURCE);
	}
	
	/**
	 * 清除缓存中的用户
	 */
	public static void clearCacheUser(Long userId){
		CacheUtils.evict(Constant.CACHE_SYS_USER,userId.toString());
	}
	
	/**
	 * 清除用户机构
	 */
	public static void clearCacheOffice(List<Long> userIds){
		for (Long userId : userIds) {
			CacheUtils.evict(Constant.CACHE_SYS_OFFICE,
					Constant.CACHE_USER_OFFICE + userId);
		}
	}
	
	/**
	 * 缓存登录用户,默认设置过期时间为20分钟,与session存活时间的同步
	 */
	public static void cacheLoginUser(SysUser sysUser){
		CacheUtils.put(Constant.CACHE_SYS_USER, sysUser.getId().toString(), 
				sysUser,getSession().getMaxInactiveInterval());
	}
	
	/**
	 * 从缓存中取登录的用户
	 */
	public static SysUser getCacheLoginUser(){
		try {
			if (getSessionLoginUser() != null) {
				return CacheUtils.get(Constant.CACHE_SYS_USER,
						getSessionLoginUser().getId().toString());
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 得到当前session
	 */
	public static HttpSession getSession() {
		HttpSession session = getCurRequest().getSession();
		return session;
	}
	
	/**
	 * session中的用户
	 */
	public static SysUser getSessionLoginUser(){
		return (SysUser) getSession().getAttribute(Constant.SESSION_LOGIN_USER);
	}
	
	/**
	 * @Title: getCurRequest
	 * @Description:(获得当前的request) 
	 * @param:@return 
	 * @return:HttpServletRequest
	 */
	public static HttpServletRequest getCurRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if(requestAttributes != null && requestAttributes instanceof ServletRequestAttributes){
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
			return servletRequestAttributes.getRequest();
		}
		return null;
	}

}
