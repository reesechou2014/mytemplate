package com.gohuinuo.web.monitor.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.common.utils.DateUtils;
import com.gohuinuo.common.utils.PrettyMemoryUtils;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("monitor/ehcache")
public class EhcacheMonitorController {

	@Resource
	private CacheManager ehcacheManager;

	@RequestMapping
	public String toEhcache(Model model) {
		model.addAttribute("ehcacheManager", ehcacheManager);
		return "sys/monitor/ehcache/ehcache";
	}

	@RequestMapping("{cacheName}/detail")
	public String cacheNameDetail(
			@PathVariable("cacheName") String cacheName,
			@RequestParam(value = "searchKey", required = false, defaultValue = "") String searchKey,
			Model model) {
		@SuppressWarnings("unchecked")
		List<Object> nonExpiryKeys = ehcacheManager.getCache(cacheName)
				.getKeys();
		List<Object> showKeys = Lists.newArrayList();
		for (Object key : nonExpiryKeys) {
			if (key.toString().contains(searchKey)) {
				showKeys.add(key);
			}
		}
		model.addAttribute("keys", showKeys);
		return "sys/monitor/ehcache/ehcache-detail";
	}

	@RequestMapping("{cacheName}/{key}/detail")
	public String keyDetail(@PathVariable("cacheName") String cacheName,
			@PathVariable("key") String key, Model model) {
		Element element = ehcacheManager.getCache(cacheName).get(key);
		model.addAttribute("objectValue", element.getObjectValue().toString());
		model.addAttribute("size",
				PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
		model.addAttribute("hitCount", element.getHitCount());
		Date latestOfCreationAndUpdateTime = new Date(
				element.getLatestOfCreationAndUpdateTime());
		model.addAttribute("latestOfCreationAndUpdateTime",
				DateUtils.formatDateTime(latestOfCreationAndUpdateTime));
		Date lastAccessTime = new Date(element.getLastAccessTime());
		model.addAttribute("lastAccessTime",
				DateUtils.formatDateTime(lastAccessTime));
		if(element.getExpirationTime() == Long.MAX_VALUE) {
			model.addAttribute("expirationTime", "不过期");
        } else {
            Date expirationTime = new Date(element.getExpirationTime());
            model.addAttribute("expirationTime", DateUtils.formatDateTime(expirationTime));
        }
		model.addAttribute("timeToIdle", element.getTimeToIdle());
		model.addAttribute("timeToLive", element.getTimeToLive());
		model.addAttribute("version", element.getVersion());
		return "sys/monitor/ehcache/object-detail";
	}
	
	@RequestMapping("{cacheName}/{key}/delete")
    public @ResponseBody Integer delete(
            @PathVariable("cacheName") String cacheName,
            @PathVariable("key") String key) {
        Cache cache = ehcacheManager.getCache(cacheName);
        cache.remove(key);
        return Constant.SUCCESS;
    }
	
	@RequestMapping("{cacheName}/clear")
    public @ResponseBody Integer clear(@PathVariable("cacheName") String cacheName) {
        Cache cache = ehcacheManager.getCache(cacheName);
        cache.removeAll();
        return Constant.SUCCESS;
    }
	
}
