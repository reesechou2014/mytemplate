//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.common.beetl.utils.BeetlUtils;
import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.common.utils.TreeUtils;
import com.gohuinuo.web.sys.mapper.SysResourceMapper;
import com.gohuinuo.web.sys.model.SysResource;
import com.gohuinuo.web.sys.model.SysUser;
import com.gohuinuo.web.sys.utils.SysUserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author
 */

@Service("sysResourceService")
public class SysResourceService extends ServiceMybatis<SysResource> {

	@Resource
	private SysResourceMapper sysResourceMapper;

	/**
	 * 新增or更新SysResource
	 */
	public int saveSysResource(SysResource sysResource) {
		int count = 0;
		// 新的parentIds
		sysResource.setParentIds(sysResource.getParentIds()
				+ sysResource.getParentId() + ",");
		if (null == sysResource.getId()) {
			count = this.insertSelective(sysResource);
		} else {
			// getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			// 先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(sysResource);
			// 不移动节点不更新子节点的pids
			if (!StringUtils.equals(sysResource.getOldParentIds(),
					sysResource.getParentIds())) {
				sysResourceMapper.updateParentIds(sysResource); // 批量更新子节点的parentIds
			}
		}
		if (count > 0) {
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_RESOURCE,
					this.getAllResourcesMap());
			SysUserUtils.clearCacheResource();
		}
		return count;
	}

	/**
	 * 根据父id删除自身已经所有子节点
	 * 
	 * @param id
	 * @return
	 */
	public int deleteResourceByRootId(Long id) {
		int count = sysResourceMapper.beforeDeleteResource(id);
		if (count > 0)
			return -1;
		int delCount = sysResourceMapper.deleteIdsByRootId(id);
		if (delCount > 0) {
			// 重新查找全部资源放入缓存(为了开发时候用)
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_RESOURCE,
					this.getAllResourcesMap());
			SysUserUtils.clearCacheResource();
		}

		return delCount;
	}

	/**
	 * 根据用户id得到用户持有的资源
	 * @param userId
	 * @return
	 */
	public List<SysResource> findUserResourceByUserId(SysUser sysUser) {
		return sysResourceMapper.findUserResourceByUserId(sysUser.getId());
	}

	/**
	 * 菜单管理分页显示筛选查询
	 * @param params {"name":"菜单名字","id":"菜单id"}
	 * @return
	 */
	public PageInfo<SysResource> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SysResource> list = sysResourceMapper.findPageInfo(params);
		return new PageInfo<SysResource>(list);
	}

	/**
	 * 获取全部资源map形式
	 * @return
	 */
	public LinkedHashMap<String, SysResource> getAllResourcesMap() {
		// 读取全部资源
		List<SysResource> resList = this.select(new SysResource(), "sort");
		LinkedHashMap<String, SysResource> AllResourceMap = new LinkedHashMap<String, SysResource>();
		for (SysResource res : resList) {
			if (StringUtils.isBlank(res.getUrl())) {
				AllResourceMap.put(res.getId().toString(), res);
			} else {
				AllResourceMap.put(res.getUrl(), res);
			}
		}
		return AllResourceMap;
	}

	/**
	 * 获取全部资源list形式
	 * @return
	 */
	public List<SysResource> getAllResourcesList() {
		LinkedHashMap<String, SysResource> allRes = BeetlUtils
				.getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
		List<SysResource> resList = new ArrayList<SysResource>(allRes.values());
		return resList;
	}
	
	/**
	 * 获取菜单树
	 */
	public List<SysResource> getMenuTree(){
		return TreeUtils.toTreeNodeList(getAllResourcesList(),SysResource.class);
	}

}
