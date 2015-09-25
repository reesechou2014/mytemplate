package com.gohuinuo.web.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.excel.ExcelUtils;
import com.gohuinuo.web.sys.model.SysArea;
import com.gohuinuo.web.sys.service.SysAreaService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("area")
public class AreaController {

	@Resource
	private SysAreaService sysAreaService;

	@RequestMapping
	public String toArea(Model model) {
		model.addAttribute("treeList", JSON.toJSONString(sysAreaService.findAllArea()));
		return "sys/area/area";
	}

	/**
	 * 区域树
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<SysArea> getAreaTreeList() {
		List<SysArea> list = sysAreaService.findAllArea();
		return list;
	}

	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysArea sysArea) {
		return sysAreaService.saveSysArea(sysArea);
	}

	/**
	 * 删除区域及其子区域
	 * 
	 * @param resourceId
	 *            区域id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Integer dels(Long id) {
		Integer count = 0;
		if (null != id) {
			count = sysAreaService.deleteAreaByRootId(id);
		}
		return count;
	}

	/**
	 * 分页显示区域table
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model) {
		PageHelper.startPage(params);
		List<SysArea> list = sysAreaService.findSysAreaList(params);
		model.addAttribute("page", new PageInfo<SysArea>(list));
		return "sys/area/area-list";
	}

	/**
	 * 弹窗
	 * 
	 * @param id
	 * @param parentId
	 *            父类id
	 * @param mode
	 *            模式(add,edit,detail)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		SysArea area = null, pArea = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pArea = sysAreaService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			area = sysAreaService.selectByPrimaryKey(id);
			pArea = sysAreaService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			area = sysAreaService.selectByPrimaryKey(id);
			pArea = sysAreaService.selectByPrimaryKey(area.getParentId());
		}
		model.addAttribute("pArea", pArea).addAttribute("area", area);
		return mode.equals("detail") ? "sys/area/area-detail"
				: "sys/area/area-save";
	}

	/**
	 * 导出execl (示例)
	 */
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params,
			HttpServletResponse response){

		List<SysArea> list = sysAreaService.findSysAreaList(params);
		
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("区域名称","name");
		titleMap.put("区域编码","code");
		titleMap.put("上级区域","pname");
		titleMap.put("父级编号","parentId");
		titleMap.put("所有父级编号","parentIds");
		titleMap.put("类型","type");
		titleMap.put("图标","icon");
		titleMap.put("状态","delFlag");
		titleMap.put("备注","remarks");
		titleMap.put("创建人","createBy");
		titleMap.put("创建时间","createDate");
		titleMap.put("更新者","updateBy");
		titleMap.put("更新时间","updateDate");
		
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "区域.xls", list, titleMap);
			
			//生成excel先存到服务器，之后客户端再下载
			/*String fileName = "区域.xls";
			String filePath = "E:/develop_software/eclipse64/workspace/mytemplate/src/main/webapp/testExcel/"+fileName;
			ExcelUtils.exportExcel(response, filePath, fileName, list, titleMap);*/
			
			//模板形式
			/*String fileName = "区域.xlsx";
			String templatePath = "E:/develop_software/eclipse64/workspace/mytemplate/src/main/webapp/testExcel/test.xlsx";
			String outPath = "E:/develop_software/eclipse64/workspace/mytemplate/src/main/webapp/testExcel/"+fileName;
			Map<String, Object> data = Maps.newHashMap();
			data.put("username", "张三");
			data.put("name", "韩流");
			//方式1
			ExcelUtils.exportExcel(response, templatePath, "区域.xlsx", data);
			//方式2
			ExcelUtils.exportExcel(response, templatePath, outPath, fileName, data);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * execl导入数据
	 * @throws IOException
	 */
	/*@RequestMapping(value = "import",method = RequestMethod.POST)
	public @ResponseBody void importFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ExcelConfig config = new ExcelConfig.Builder(Map.class)
				.sheetNum(0)
				.startRow(1)
				.separater(",")
				.mode(true)
				.addColumn("name", "code,java.lang.String", "pname",
						"parentId,java.lang.Long", "parentIds", "type", "icon",
						"delFlag", "remarks", "createBy",
						"createDate,java.util.Date", "updateBy",
						"updateDate,java.util.Date").build();
		int count = 0;
		try {
			List<SysArea> list = EasyXls.xls2List(config,
					FileUtils.uploadFile(request), SysArea.class);
			for (SysArea sysArea : list) {
				sysAreaService.insertSelective(sysArea);
				count++;
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("成功导入"+count + "条数据!");
		} catch (Exception e) {
			response.getWriter().write("导入失败");
			e.printStackTrace();
		}

	}*/

}
