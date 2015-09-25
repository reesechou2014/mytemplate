//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.web.sys.mapper.SysAreaMapper;
import com.gohuinuo.web.sys.mapper.SysDictMapper;
import com.gohuinuo.web.sys.model.SysArea;
import com.gohuinuo.web.sys.model.SysDict;
import com.gohuinuo.web.sys.model.SysOffice;
import com.gohuinuo.web.sys.model.SysRole;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

/**
 * 
 * @author
 */

@Service("sysDictService")
@CacheConfig(cacheNames = "sysDict_cache")
public class SysDictService extends ServiceMybatis<SysDict> {

	@Resource
	private SysDictMapper sysDictMapper;

	@Resource
	private SysAreaMapper sysAreaMapper;

	/**
	 * 保存或更新
	 * 
	 * @param sysDict
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int saveSysdict(SysDict sysDict) {
		return this.save(sysDict);
	}

	/**
	 * 删除
	* @param sysDict
	* @return
	 */
	@CacheEvict(allEntries = true)
	public int deleteSysDict(SysDict sysDict) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", sysDict.getValue());
		if(sysDict.getType().equals("sys_area_type")){
			int areaCount = this.beforeDelete(SysArea.class,params);
			if(areaCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_office_type")){
			int officeCount = this.beforeDelete(SysOffice.class,params);
			if(officeCount<0) return -1;
		}
		if(sysDict.getType().equals("sys_data_scope")){
			int roleCount = this.beforeDelete(SysRole.class, params);
			if(roleCount<0) return -1;
		}
		return this.updateDelFlagToDelStatusById(SysDict.class, sysDict.getId());
	}

	@Cacheable(key="'allDictTable'")
	public Table<String,String, SysDict> findAllDictTable(){
		List<SysDict> dictList = this.select(new SysDict());
		Table<String,String, SysDict> tableDicts = HashBasedTable.create();
		for(SysDict dict : dictList){
			tableDicts.put(dict.getType(), dict.getValue(), dict);
		}
		return tableDicts;
	}
	
	@Cacheable(key="'allDictMultimap'")
	public Multimap<String, SysDict> findAllMultimap(){
		List<SysDict> dictList = this.select(new SysDict());
		Multimap<String, SysDict> multimap = ArrayListMultimap.create();
		for(SysDict dict : dictList){
			multimap.put(dict.getType(), dict);
		}
		return multimap;
	}

}
