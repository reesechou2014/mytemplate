//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.service;

import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.web.sys.mapper.SysLogMapper;
import com.gohuinuo.web.sys.model.SysLog;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogService extends ServiceMybatis<SysLog> {

	@Resource
	private SysLogMapper sysLogMapper;
	
	/**
	 *新增或更新SysLog
	 */
	public int saveSysLog(SysLog sysLog){
		int count = 0;
		if(null == sysLog.getId()){
			count = this.insertSelective(sysLog);
		}else{
			count = this.updateByPrimaryKeySelective(sysLog);
		}
	    return count;
	}
	
	/**
	 * 根据条件分页查询SysLog列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public List<SysLog> findSysLogList(Map<String,Object> params) {
        List<SysLog> list = sysLogMapper.findSysLogList(params);
        return list;
	}
	

}
