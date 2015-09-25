//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.gohuinuo.web.sys.model.SysOffice;

/**
 * 
 * @author 
 */

public interface SysOfficeMapper extends Mapper<SysOffice> {

	public List<SysOffice> findPageInfo(Map<String, Object> params);
	
	public int deleteOfficeByRootId(Long id);

	public int updateParentIds(SysOffice sysOffice);
	
	public SysOffice findOfficeCompanyIdByDepId(Long depId);
	
	//得到用户数据范围
	public List<Long> findUserDataScopeByUserId(Long userId);
	
	public List<Long> findOfficeIdsByRootId(Long rootId);
	
}
