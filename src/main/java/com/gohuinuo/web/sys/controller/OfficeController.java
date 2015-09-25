//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.base.BaseController;
import com.gohuinuo.web.sys.model.SysArea;
import com.gohuinuo.web.sys.model.SysOffice;
import com.gohuinuo.web.sys.service.SysAreaService;
import com.gohuinuo.web.sys.service.SysOfficeService;
import com.gohuinuo.web.sys.utils.SysUserUtils;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 
 */

@Controller
@RequestMapping("office")
public class OfficeController extends BaseController {
	
	
	@Resource
	private SysOfficeService sysOfficeService;
	@Resource
	private SysAreaService sysAreaService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysOffice(Model model){
		model.addAttribute("treeList", JSON.toJSONString(SysUserUtils.getUserOffice()));
		return "sys/office/office";
	}
	
	@RequestMapping(value="tree",method = RequestMethod.POST)
	public @ResponseBody List<SysOffice> getOfficeTreeList(@ModelAttribute SysOffice sysOffice){
		return SysUserUtils.getUserOffice();
	}
	
	/**
	 * 分页显示
	* @param params
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		PageInfo<SysOffice> page = sysOfficeService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/office/office-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysOffice sysOffice){
		return sysOfficeService.saveSysOffice(sysOffice);
	}
	
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysOfficeService.deleteOfficeByRootId(id);
	}
	
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,Long parentId,@PathVariable("mode") String mode, Model model){
		SysOffice office = null, pOffice = null;
		SysArea area = null;
		if(StringUtils.equalsIgnoreCase(mode, "add")){
			pOffice = sysOfficeService.selectByPrimaryKey(parentId);
		}else if(StringUtils.equalsIgnoreCase(mode, "edit")){
			office = sysOfficeService.selectByPrimaryKey(id);
			pOffice = sysOfficeService.selectByPrimaryKey(parentId);
			area = sysAreaService.selectByPrimaryKey(office.getAreaId());
		}else if(StringUtils.equalsIgnoreCase(mode, "detail")){
			office = sysOfficeService.selectByPrimaryKey(id);
			pOffice = sysOfficeService.selectByPrimaryKey(office.getParentId());
			area = sysAreaService.selectByPrimaryKey(office.getAreaId());
		}
		model.addAttribute("pOffice", pOffice)
			.addAttribute("office", office)
			.addAttribute("area", area);
		return mode.equals("detail")?"sys/office/office-detail":"sys/office/office-save";
	}
	

}
