//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.service;

import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.web.sys.mapper.SysAreaMapper;
import com.gohuinuo.web.sys.mapper.SysOfficeMapper;
import com.gohuinuo.web.sys.model.SysArea;
import com.gohuinuo.web.sys.model.SysOffice;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

@Service("sysAreaService")
@CacheConfig(cacheNames="sysArea_cache")
public class SysAreaService extends ServiceMybatis<SysArea>{

	@Resource
	private SysAreaMapper sysAreaMapper;
	
	@Resource
	private SysOfficeMapper sysOfficeMapper;
	
	/**
	 *新增or更新SysArea
	 */
	
	@CacheEvict(allEntries=true)
	public int saveSysArea(SysArea sysArea){
		int count = 0;
		//新的parentIds
		sysArea.setParentIds(sysArea.getParentIds()+sysArea.getParentId()+","); 
		if(null == sysArea.getId()){
			count = this.insertSelective(sysArea);
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(sysArea); 
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(sysArea.getOldParentIds(), sysArea.getParentIds())){
				sysAreaMapper.updateParentIds(sysArea); //批量更新子节点的parentIds
			}
		}
		return count;
	}
	
	/**
	 * 根据父id删除自身已经所有子节点
	* @param id
	* @return
	 */
	@CacheEvict(allEntries=true)	
	public int deleteAreaByRootId(Long id){
		int count = this.beforeDeleteTreeStructure(id,"areaId",SysOffice.class,SysArea.class);
		return count==-1?-1:sysAreaMapper.deleteAreaByRootId(id);
	}
	
	/**
	 * 区域管理分页显示筛选查询
	 * 
	 * @param params
	 *            {"name":"区域名字","id":"区域id"}
	 * @return
	 */
	public List<SysArea> findSysAreaList(Map<String, Object> params){
		List<SysArea> list = sysAreaMapper.findSysAreaList(params);
		return list;
	}
	
	
	/**
	 * 查询全部的区域
	* @return
	 */
	@Cacheable(key="'area_all'")
	public List<SysArea> findAllArea(){
		return this.select(new SysArea());
	}

}
