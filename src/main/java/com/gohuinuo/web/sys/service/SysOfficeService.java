//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.web.sys.mapper.SysOfficeMapper;
import com.gohuinuo.web.sys.mapper.SysRoleMapper;
import com.gohuinuo.web.sys.model.SysOffice;
import com.gohuinuo.web.sys.model.SysRole;
import com.gohuinuo.web.sys.model.SysUser;
import com.gohuinuo.web.sys.utils.SysUserUtils;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

@Service("sysOfficeService")
@CacheConfig(cacheNames="sysOffice_cache")
public class SysOfficeService extends ServiceMybatis<SysOffice> {

	@Resource
	private SysOfficeMapper sysOfficeMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	/**
	 *新增或更新SysOffice
	 */
	@CacheEvict(key="'allOffice'")
	public int saveSysOffice(SysOffice sysOffice){
		int count = 0;
		//新的parentIds
		sysOffice.setParentIds(sysOffice.getParentIds()+sysOffice.getParentId()+","); 
		int grade = sysOffice.getParentIds().split(",").length;
		sysOffice.setGrade(String.valueOf(grade));
		if(null == sysOffice.getId()){
			count = this.insertSelective(sysOffice);
			//自动赋权
			Long roleId = SysUserUtils.autoAddOfficeToRole();
			if(roleId != null){
				SysRole sysRole = new SysRole();
				sysRole.setId(roleId);
				sysRole.setOfficeIds(new Long[]{sysOffice.getId()});
				sysRoleMapper.insertRoleOffice(sysRole);
			}
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(sysOffice); 
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(sysOffice.getOldParentIds(), sysOffice.getParentIds())){
				sysOfficeMapper.updateParentIds(sysOffice); //批量更新子节点的parentIds
			}
		}
		SysUserUtils.clearCacheOffice(Lists.newArrayList(SysUserUtils.getCacheLoginUser().getId()));
		return count;
	}
	
	@CacheEvict(key="'allOffice'")
	public int deleteOfficeByRootId(Long id){
		int roleCount = this.beforeDeleteTreeStructure(id, "officeId", SysRole.class,SysOffice.class);
		if(roleCount<0) return -1;
		int userOfficeCount = this.beforeDeleteTreeStructure(id, "officeId", SysUser.class,SysOffice.class);
		int userCompanyCount = this.beforeDeleteTreeStructure(id, "companyId",  SysUser.class,SysOffice.class);
		if(userOfficeCount+userCompanyCount<0) return -1;
		SysUserUtils.clearCacheOffice(Lists.newArrayList(SysUserUtils.getCacheLoginUser().getId()));
		return sysOfficeMapper.deleteOfficeByRootId(id);
	}
	
	/**
	 * 根据用户id查询用户的数据范围
	 */
	public List<Long> findUserDataScopeByUserId(Long userId){
		return sysOfficeMapper.findUserDataScopeByUserId(userId);
	}
	
	/**
	 * 根据根节点查询自身及其子孙节点
	 */
	public List<Long> findOfficeIdsByRootId(Long rootId){
		return sysOfficeMapper.findOfficeIdsByRootId(rootId);
	}
	
	
	/**
	 * 根据条件分页查询SysOffice列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public PageInfo<SysOffice> findPageInfo(Map<String,Object> params) {
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("t1", null));
        PageHelper.startPage(params);
        List<SysOffice> list=sysOfficeMapper.findPageInfo(params); 
        return new PageInfo<SysOffice>(list);
	}
	
}
