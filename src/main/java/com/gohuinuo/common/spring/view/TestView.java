package com.gohuinuo.common.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class TestView extends AbstractTemplateView{
	
	private GroupTemplate groupTemplate = null;
	
	public TestView() {
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (groupTemplate == null){
			groupTemplate = getApplicationContext().getBean(GroupTemplate.class);
		}
		Template template = groupTemplate.getTemplate(getUrl());
		template.binding("testPath", request.getContextPath()+"/定义");
	}

}
