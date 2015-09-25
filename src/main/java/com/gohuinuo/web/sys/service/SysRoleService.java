//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.web.sys.mapper.SysRoleMapper;
import com.gohuinuo.web.sys.model.SysResource;
import com.gohuinuo.web.sys.model.SysRole;
import com.gohuinuo.web.sys.model.SysUser;
import com.gohuinuo.web.sys.utils.SysUserUtils;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

@Service("sysRoleService")
@CacheConfig(cacheNames="sysRole")
public class SysRoleService extends ServiceMybatis<SysRole> {

	@Resource
	private SysRoleMapper sysRoleMapper;
	
	/**
	 *新增或更新SysRole
	 */
	public int saveSysRole(SysRole sysRole){
		int count = 0;
		if(null == sysRole.getId()){
			count = this.insertSelective(sysRole);
		}else{
			sysRoleMapper.deleteRoleResourceByRoleId(sysRole.getId());
			sysRoleMapper.deleteRoleOfficeByRoleId(sysRole.getId());
			count = this.updateByPrimaryKeySelective(sysRole);
			//清除缓存
			List<Long> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
			SysUserUtils.clearAllCachedAuthorizationInfo(userIds);
		}
		if(sysRole.getResourceIds().length>0){
			sysRoleMapper.insertRoleResource(sysRole);
		}
		if(sysRole.getOfficeIds().length>0 && ("9").equals(sysRole.getDataScope())){
			sysRoleMapper.insertRoleOffice(sysRole);
		}
	    return count;
	}
	
	/**
	 * 删除角色
	* @param id
	 */
	public int deleteSysRole(Long id){
		List<Long> userIds = sysRoleMapper.findUserIdsByRoleId(id);
		sysRoleMapper.deleteUserRoleByRoleId(id);
		sysRoleMapper.deleteRoleOfficeByRoleId(id);
		sysRoleMapper.deleteRoleResourceByRoleId(id);
		int count = this.deleteByPrimaryKey(id);
		//清除缓存
		SysUserUtils.clearAllCachedAuthorizationInfo(userIds);
		return count;
	}
	
	/**
	 * 添加角色绑定的人员
	* @param sysRole
	* @return
	 */
	public int saveUserRole(SysRole sysRole){
		//旧的绑定的人员id
		List<Long> userIds = sysRoleMapper.findUserIdsByRoleId(sysRole.getId());
		//当前的要绑定的人员id
		List<Long> curUserIds = Lists.newArrayList(sysRole.getUserIds());
		userIds.addAll(curUserIds);
		ImmutableList<Long> mergeList = ImmutableSet.copyOf(userIds).asList();
		
		sysRoleMapper.deleteUserRoleByRoleId(sysRole.getId());
		if(sysRole.getUserIds().length>0) {
			sysRoleMapper.insertUserRoleByRoleId(sysRole);
		}
		//清除缓存
		SysUserUtils.clearAllCachedAuthorizationInfo(mergeList);
		return 1;
	}
	
	
	/**
	 * 根据条件分页查询SysRole列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public PageInfo<SysRole> findPageInfo(Map<String,Object> params) {
		params.put(Constant.CACHE_USER_DATASCOPE, 
				SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
        PageHelper.startPage(params);
        List<SysRole> list = sysRoleMapper.findPageInfo(params); 
        return new PageInfo<SysRole>(list);
	}
	
	/**
	 * 根据角色id查询拥有的资源id集合
	* @param roleId
	* @return
	 */
	public List<Long> findResourceIdsByRoleId(Long roleId){
		return sysRoleMapper.findResourceIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的机构id集合
	* @param roleId
	* @return
	 */
	public List<Long> findOfficeIdsByRoleId(Long roleId){
		return sysRoleMapper.findOfficeIdsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有的资源 
	* @param roleId
	* @return
	 */
	public List<SysResource> findResourceByRoleId(Long roleId){
		return sysRoleMapper.findResourceByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询拥有此角色的用户
	* @param roleId
	* @return
	 */
	public List<SysUser> findUserByRoleId(Long roleId){
		return sysRoleMapper.findUserByRoleId(roleId);
	}
	
	/**
	 * 当前登录用户的可见的角色
	 */
	public List<SysRole> findCurUserRoleList(){
		Map<String, Object> params = Maps.newHashMap();
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", "sur","user_id"));
		return sysRoleMapper.findPageInfo(params);
	}
	
	/**
	 * 当前登录用户的可见的角色map形式 
	 */
	public Map<Long, SysRole> findCurUserRoleMap(){
		List<SysRole> list = this.findCurUserRoleList();
		Map<Long, SysRole> map = Maps.uniqueIndex(list, new Function<SysRole, Long>() {
			@Override
			public Long apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return map;
	}
	
	
	/**
	 * 用户的角色List列表
	* @param userId
	* @return userRoleList
	 */
	public List<SysRole> findUserRoleListByUserId(Long userId){
		List<SysRole> userRoles = sysRoleMapper.findUserRoleListByUserId(userId);
		return userRoles;
	}
	
	/**
	 * 用户的角色Map
	* @param userId
	* @return userRoleMap
	 */
	public Map<Long, SysRole> findUserRoleMapByUserId(Long userId){
		List<SysRole> roleList = this.findUserRoleListByUserId(userId);
		Map<Long, SysRole> userRoleMap = Maps.uniqueIndex(roleList, new Function<SysRole, Long>() {
			@Override
			public Long apply(SysRole sysRole) {
				return sysRole.getId();
			}
		});
		return userRoleMap;
	}
	
}
