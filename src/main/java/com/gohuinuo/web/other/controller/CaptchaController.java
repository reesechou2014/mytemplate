package com.gohuinuo.web.other.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gohuinuo.common.captcha.ValidateCode;


@Controller
public class CaptchaController {

	@RequestMapping("captcha")
	public void codeImage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "120") int width,
			@RequestParam(defaultValue = "40") int height) throws IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession();

		ValidateCode vCode = new ValidateCode(width, height, 4, 100);
		session.setAttribute("code", vCode.getCode());
		vCode.write(response.getOutputStream());
	}

}
