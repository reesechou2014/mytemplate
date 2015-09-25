package com.gohuinuo.web.monitor.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("monitor/jvm")
public class JvmMonitorController{

    @RequestMapping
    public String jvm(Model model) {
    	ThreadMXBean tm = ManagementFactory.getThreadMXBean();
    	tm.setThreadContentionMonitoringEnabled(true);
    	long [] tid = tm.getAllThreadIds();
    	ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);
    	long [][] threadArray = new long[tia.length][2];

    	for (int i = 0; i < tia.length; i++) {          
    	    long threadId = tia[i].getThreadId();

    	    long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
    	    threadArray[i][0] = threadId;
    	    threadArray[i][1] = cpuTime;
    	}

    	long [] temp = new long[2];
    	for (int j = 0; j < threadArray.length - 1; j ++){
    		for (int k = j + 1; k < threadArray.length; k++ )
    	    if (threadArray[j][1] < threadArray[k][1]){
    	        temp = threadArray[j];
    	        threadArray[j] = threadArray[k];
    	        threadArray[k] = temp;  
    	    }
    	}
    	List<Map<String, Object>> list = Lists.newArrayList();
    	for(int i=0;i<threadArray.length;i++){
    		ThreadInfo ti = tm.getThreadInfo(threadArray[i][0], Integer.MAX_VALUE);
    		if(ti == null) continue;
    		Map<String, Object> map = Maps.newHashMap();
    		map.put("id", threadArray[i][0]);
    		map.put("threadName", ti.getThreadName());
    		map.put("threadState", ti.getThreadState());
    		map.put("lockName", ti.getLockName());
    		map.put("lockOwnerName", ti.getLockOwnerName());
    		map.put("cpuTime", threadArray[i][1]);
    		map.put("depth", ti.getStackTrace().length);
    		map.put("stes", ti.getStackTrace());
    		list.add(map);
    	}
    	model.addAttribute("tm", tm).addAttribute("list", list);
    	
        return "sys/monitor/jvm/jvm";
    }


}
