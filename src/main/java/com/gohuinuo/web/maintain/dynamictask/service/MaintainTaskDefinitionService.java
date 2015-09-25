//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.maintain.dynamictask.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MethodInvoker;

import com.gohuinuo.common.base.ServiceMybatis;
import com.gohuinuo.web.maintain.dynamictask.exception.DynamicTaskException;
import com.gohuinuo.web.maintain.dynamictask.mapper.MaintainTaskDefinitionMapper;
import com.gohuinuo.web.maintain.dynamictask.model.MaintainTaskDefinition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author if
 */

@Service("maintainTaskDefinitionService")
public class MaintainTaskDefinitionService extends
		ServiceMybatis<MaintainTaskDefinition> {

	private final Logger logger = LoggerFactory.getLogger("sys-error");

	@Resource
	private MaintainTaskDefinitionMapper maintainTaskDefinitionMapper;
	
	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Long, ScheduledFuture<?>> taskMap = Maps.newConcurrentMap();

	public void initTask() { // 系统启动后，自动加载任务
		List<Long> ids = Lists.newArrayList();
		List<MaintainTaskDefinition> list = this.select(new MaintainTaskDefinition());
		for (MaintainTaskDefinition td : list) {
			if (Boolean.TRUE.equals(td.getIsStart())) {
				ids.add(td.getId());
			}
		}
		if (!CollectionUtils.isEmpty(ids)) {
			startTask(true, ids.toArray(new Long[0]));
		}
	}


    public int addTaskDefinition(MaintainTaskDefinition taskDefinition) {
    	return this.insertSelective(taskDefinition);
    }

    public int updateTaskDefinition(MaintainTaskDefinition taskDefinition) {
    	return this.updateByPrimaryKeySelective(taskDefinition);
    }

    public void deleteTaskDefinition(boolean forceTermination, Long... taskDefinitionIds) {
        stopTask(forceTermination, taskDefinitionIds);
        for(Long taskDefinitionId : taskDefinitionIds){
        	this.deleteByPrimaryKey(taskDefinitionId);
        }
    }
    
    public synchronized void startTask(Long... taskDefinitionIds) {
        startTask(false, taskDefinitionIds);
    }
    
    private synchronized void startTask(boolean forceStart,
			Long... taskDefinitionIds) {
		for (Long taskDefinitionId : taskDefinitionIds) {
			MaintainTaskDefinition td = this.selectByPrimaryKey(taskDefinitionId);
			if (td == null || (forceStart == false && Boolean.TRUE.equals(td.getIsStart()))) {
				return;
			}

			try {
				ScheduledFuture<?> future = taskScheduler.schedule(
						createTask(td), new CronTrigger(td.getCron()));
				taskMap.put(taskDefinitionId, future);
				td.setIsStart(Boolean.TRUE);
			} catch (Exception e) {
				logger.error("start task error, task id:" + taskDefinitionId, e);
				td.setDescription(e.getMessage());
			}
			this.updateByPrimaryKey(td);
		}
	}

	private Runnable createTask(MaintainTaskDefinition td) {
		final MethodInvoker methodInvoker = new MethodInvoker();
		final Long taskId = td.getId();
		try {
			methodInvoker.setTargetMethod(td.getMethodName());
			Object bean = null;
			if (StringUtils.isNotEmpty(td.getBeanName())) {
				bean = applicationContext.getBean(td.getBeanName());
			} else {
				bean = applicationContext.getAutowireCapableBeanFactory()
						.createBean(Class.forName(td.getBeanClass()));
			}
			methodInvoker.setTargetObject(bean);
			methodInvoker.prepare();
		} catch (Exception e) {
			throw new DynamicTaskException("create task runnable error, task id is : " + taskId, e);
		}
		return new Runnable() {
			@Override
			public void run() {
				try {
					methodInvoker.invoke();
				} catch (Exception e) {
					logger.error("run dynamic task error, task id is:" + taskId, e);
					throw new DynamicTaskException("run dynamic task error, task id is:" + taskId, e);
				}
			}
		};
	}
    
    public synchronized void stopTask(boolean forceTermination, Long... taskDefinitionIds) {
        for(Long taskDefinitionId : taskDefinitionIds) {
        	MaintainTaskDefinition td = this.selectByPrimaryKey(taskDefinitionId);

            if(td == null || Boolean.FALSE.equals(td.getIsStart())) {
                return;
            }

            try {
                ScheduledFuture<?> future = taskMap.get(taskDefinitionId);
                if(future != null) {
                    future.cancel(forceTermination);
                }
                td.setIsStart(Boolean.FALSE);
            } catch (Exception e) {
                logger.error("stop task error, task id:" + taskDefinitionId, e);
                td.setDescription(e.getMessage());
            }
            this.updateByPrimaryKey(td);
        }

    }

}
