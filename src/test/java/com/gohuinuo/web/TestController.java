package com.gohuinuo.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gohuinuo.common.excel.ExcelUtils;
import com.gohuinuo.web.sys.model.SysUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
public class TestController {

	@RequestMapping("test")
	public String test(){
		return "test";
	}
	
	@RequestMapping(value="duallist")
	public @ResponseBody void test1(String[] duallist){
		System.out.println(duallist);
	}
	
	@RequestMapping(value = "export")
	public void importExcel(@RequestParam Map<String, Object> params,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = Maps.newHashMap();
		List<SysUser> list1 = Lists.newArrayList();
		SysUser user1 = new SysUser();
		user1.setName("李四");
		user1.setPhone("123456");
		list1.add(user1);
		
		List<SysUser> list2 = Lists.newArrayList();
		SysUser user2 = new SysUser();
		user2.setName("张三");
		user2.setPhone("1666666666");
		list2.add(user2);
		
		map.put("综合部", list1);
		map.put("财务部", list2);
		
		Map<String, Object> map1 = Maps.newHashMap();
		map1.put("map", map);
		
		String templatePath = "C:/Users/lenovo/Desktop/1.xls";
		ExcelUtils.exportExcel(response, templatePath, "员工通讯录.xlsx", map1);
	}
}
