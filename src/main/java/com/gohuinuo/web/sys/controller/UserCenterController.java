package com.gohuinuo.web.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gohuinuo.web.sys.model.SysUser;
import com.gohuinuo.web.sys.service.SysUserCenterService;
import com.gohuinuo.web.sys.utils.SysUserUtils;

@Controller
@RequestMapping("userCenter")
public class UserCenterController {

	@Resource
	private SysUserCenterService sysUserCenterService;

	@RequestMapping
	public String viewInfo(Model model) {
		SysUser sysUser = sysUserCenterService.getSysUserInfo();
		model.addAttribute("sysUser", sysUser);
		return "sys/userCenter/userCenter";
	}
	
	/**
	 * 更新用户信息
	 * @param sysUser
s	 */
	@RequestMapping(value = "updateInfo",method = RequestMethod.POST)
	public @ResponseBody Integer updateSysuserInfo(@ModelAttribute SysUser sysUser){
		Integer count = sysUserCenterService.updateSysuserInfo(sysUser);
		if(count>0){
			SysUserUtils.clearCacheUser(SysUserUtils.getCacheLoginUser().getId());
			SysUserUtils.getSession().invalidate();
		}
		return count;
	}
	
	@RequestMapping("conversation")
	public String conversation(){
		return "sys/userCenter/conversation";
	}

}
