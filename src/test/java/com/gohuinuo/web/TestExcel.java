package com.gohuinuo.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.junit.Test;

import com.gohuinuo.common.excel.ExcelUtils;
import com.gohuinuo.common.excel.template.PoiTemplate;
import com.gohuinuo.common.excel.template.utils.PoiUtil;
import com.gohuinuo.web.sys.model.SysOffice;
import com.gohuinuo.web.sys.model.SysUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/*@RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations = {
 })*/
public class TestExcel {

	@Test
	public void testExportExcel() throws IOException {
		String temp = "E:/develop_software/eclipse64/workspace/mytemplate/src/main/webapp/testExcel/test.xlsx"; // 输入path
		String outPath = "E:/develop_software/eclipse64/workspace/mytemplate/src/main/webapp/testExcel/test1.xlsx"; // 输出path
		PoiTemplate poiTemplate = new PoiTemplate(temp, outPath);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			list.add("test" + i);
		}
		poiTemplate.addValue("username", "张三");
		poiTemplate.addValue("name", "韩流");
		poiTemplate.addValue("list", list);
		poiTemplate.writeExcel();
	}

	@Test
	public void test1() throws FileNotFoundException {
		String out = "E:/develop_software/eclipse64/workspace/mytemplate/src/main/webapp/testExcel/test2.xls";
		Map<String, String> map = new HashMap<String, String>();
		map.put("姓名", "name");
		map.put("年龄", "age");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", "张三");
		data.put("age", "34");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(data);

		PoiUtil.writeExcel(list, new FileOutputStream(new File(out)), map);

	}

	@Test
	public void test2() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		List<SysUser> list = Lists.newArrayList();
		SysUser user1 = new SysUser();
		user1.setName("李四");
		user1.setPhone("123456");
		SysUser user2 = new SysUser();
		user2.setName("张三");
		user2.setPhone("433232");
		list.add(user1);
		list.add(user2);
		map.put("综合部", list);
		
		List<SysUser> list2 = Lists.newArrayList();
		SysUser user3 = new SysUser();
		user3.setName("王五");
		user3.setPhone("343");
		SysUser user4 = new SysUser();
		user4.setName("韩七");
		user4.setPhone("53434");
		list2.add(user3);
		list2.add(user4);
		map.put("财务部", list2);

		String templatePath = "C:/Users/lenovo/Desktop/1.xls";
		String outPath = "C:/Users/lenovo/Desktop/test.xls";
		PoiTemplate poiTemplate = new PoiTemplate(templatePath, outPath);
		poiTemplate.addValue("map", map);

		poiTemplate.writeExcel();
	}

	@Test
	public void test3() throws Exception {
		TemplateExportParams params = new TemplateExportParams(
	            "C:/Users/lenovo/Desktop/1.xls");
		
		Map<String, Object> map = Maps.newHashMap();
		List<SysUser> list1 = Lists.newArrayList();
		SysUser user1 = new SysUser();
		user1.setName("李四");
		user1.setPhone("123456");
		SysUser user2 = new SysUser();
		user2.setName("张三");
		user2.setPhone("433232");
		list1.add(user1);
		list1.add(user2);
		map.put("综合部", list1);
		
		List<SysUser> list2 = Lists.newArrayList();
		SysUser user3 = new SysUser();
		user3.setName("王五");
		user3.setPhone("343");
		SysUser user4 = new SysUser();
		user4.setName("韩七");
		user4.setPhone("53434");
		list2.add(user3);
		list2.add(user4);
		map.put("财务部", list2);
		
		List<Map<String, Object>> list = Lists.newArrayList();
		list.add(map);
		
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
	    entity.add(new ExcelExportEntity("部门", "org"));
	    entity.add(new ExcelExportEntity("姓名", "name"));
	    entity.add(new ExcelExportEntity("电话", "phone"));
	    
	    
	    ExportParams exportParams = new ExportParams("测试", "测试");
        CourseHanlder hanlder = new CourseHanlder();
        hanlder.setNeedHandlerFields(new String[] { "部门" });
        exportParams.setDataHanlder(hanlder);
		
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entity, list);
		FileOutputStream fos = new FileOutputStream("C:/Users/lenovo/Desktop/test.xls");
		workbook.write(fos);
		fos.close();
	}
	
	@Test
	public void test4() throws Exception{
		TemplateExportParams params = new TemplateExportParams(
	            "C:/Users/lenovo/Desktop/1.xls");
		
		Map<String, Object> map = Maps.newHashMap();
		List<SysUser> list = Lists.newArrayList();
		SysUser user1 = new SysUser();
		user1.setName("李四");
		user1.setPhone("123456");
		user1.set("org", "财务部");
		SysUser user2 = new SysUser();
		user2.setName("张三");
		user2.setPhone("433232");
		user2.set("org", "市场部");
		SysUser user3 = new SysUser();
		user3.setName("王五");
		user3.setPhone("343");
		user3.set("org", "财务部");
		SysUser user4 = new SysUser();
		user4.setName("韩七");
		user4.setPhone("53434");
		user4.set("org", "市场部");
		
		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);
		
		
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
	    entity.add(new ExcelExportEntity("部门", "org"));
	    entity.add(new ExcelExportEntity("姓名", "name"));
	    entity.add(new ExcelExportEntity("电话", "phone"));
	    
	    ExportParams exportParams = new ExportParams("测试", "测试");
        CourseHanlder hanlder = new CourseHanlder();
        hanlder.setNeedHandlerFields(new String[] { "部门" });
        exportParams.setDataHanlder(hanlder);
		
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entity, list);
		FileOutputStream fos = new FileOutputStream("C:/Users/lenovo/Desktop/test.xls");
		workbook.write(fos);
		fos.close();
	}

}

class CourseHanlder extends ExcelDataHandlerDefaultImpl {

    @Override
    public Object exportHandler(Object obj, String name, Object value) {
        if (name.equals("部门")) {
            return String.valueOf(value);
        }
        return super.exportHandler(obj, name, value);
    }

    @Override
    public Object importHandler(Object obj, String name, Object value) {
        return super.importHandler(obj, name, value);
    }

}

