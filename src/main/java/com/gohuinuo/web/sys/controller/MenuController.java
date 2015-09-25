package com.gohuinuo.web.sys.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.base.BaseController;
import com.gohuinuo.web.sys.model.SysResource;
import com.gohuinuo.web.sys.service.SysResourceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * 
 * @ClassName: MenuController
 * @author
 * @date 2014年10月11日 上午11:38:28
 *
 */

@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {

	@Resource
	private SysResourceService sysResourceService;

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @param model
	 * @return 菜单管理模块html
	 */
	@RequestMapping
	public String toMenu(Model model) {
		model.addAttribute("treeList", JSON.toJSONString(sysResourceService.getMenuTree()));
		return "sys/menu/menu";
	}

	/**
	 * 菜单树
	 * 
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<SysResource> tree() {
		return sysResourceService.getMenuTree();
	}

	/**
	 * 分页显示菜单table
	 * 
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model) {
		PageInfo<SysResource> page = sysResourceService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/menu/menu-list";
	}

	/**
	 * 添加或更新菜单
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysResource sysResource) {
		return sysResourceService.saveSysResource(sysResource);
	}

	/**
	 * 删除菜单及其子菜单
	 * 
	 * @param resourceId
	 *            菜单id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Integer dels(Long id) {
		Integer count = 0;
		if (null != id) {
			count = sysResourceService.deleteResourceByRootId(id);
		}
		return count;
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
		SysResource resource = null, pResource = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pResource = sysResourceService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			resource = sysResourceService.selectByPrimaryKey(id);
			pResource = sysResourceService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			resource = sysResourceService.selectByPrimaryKey(id);
			pResource = sysResourceService.selectByPrimaryKey(resource
					.getParentId());
		}
		model.addAttribute("pResource", pResource).addAttribute("sysResource",
				resource);
		return mode.equals("detail") ? "sys/menu/menu-detail"
				: "sys/menu/menu-save";
	}

}
