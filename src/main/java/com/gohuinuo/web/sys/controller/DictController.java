package com.gohuinuo.web.sys.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gohuinuo.web.sys.model.SysDict;
import com.gohuinuo.web.sys.service.SysDictService;

@Controller
@RequestMapping("dict")
public class DictController {

	@Resource
	private SysDictService sysDictService;
	
	@RequestMapping
	public String toDict(Model model){
		return "sys/dict/dict";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysDict sysDict) {
		return sysDictService.saveSysdict(sysDict);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute SysDict sysDict){
		return sysDictService.deleteSysDict(sysDict);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute SysDict sysDict, Model model) {
		PageInfo<SysDict> page = sysDictService.selectPage(pageNum, pageSize, sysDict);
		model.addAttribute("page", page);
		return "sys/dict/dict-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		SysDict dict = sysDictService.selectByPrimaryKey(id);
		model.addAttribute("dict", dict);
		return "sys/dict/dict-save";
	}
	
}
