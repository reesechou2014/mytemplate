//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.gohuinuo.web.sys.model.SysResource;
import com.gohuinuo.web.sys.model.SysRole;
import com.gohuinuo.web.sys.model.SysUser;

/**
 * 
 * @author
 */

public interface SysRoleMapper extends Mapper<SysRole> {

	public int insertRoleOffice(SysRole sysRole);

	public int insertRoleResource(SysRole sysRole);

	public int insertUserRoleByRoleId(SysRole sysRole);

	public int insertUserRoleByUserId(SysUser sysUser);

	public int deleteRoleOfficeByRoleId(Long roleId);

	public int deleteRoleResourceByRoleId(Long roleId);

	public int deleteUserRoleByRoleId(Long roleId);

	public int deleteUserRoleByUserId(Long userId);

	public List<Long> findOfficeIdsByRoleId(Long roleId);

	public List<Long> findResourceIdsByRoleId(Long roleId);
	
	public List<Long> findUserIdsByRoleId(Long userId);

	public List<SysResource> findResourceByRoleId(Long roleId);

	public List<SysUser> findUserByRoleId(Long roleId);

	public List<SysRole> findUserRoleListByUserId(Long userId);

	public List<SysRole> findPageInfo(Map<String, Object> params);

}
