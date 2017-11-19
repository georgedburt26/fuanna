package com.fuanna.h5.buy.controller.admin;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fuanna.h5.buy.base.BaseController;

@Controller
public class LoginController extends BaseController{

	private static final Logger logger = Logger
			.getLogger(AdminController.class);
	
	@RequestMapping("/login.do")
	public String login() {
		if (session().getAttribute("admin") != null) {
			return "redirect:/index.do";
		}
		return "/login";
	}
	
	@RequestMapping("/index.do")
	public String index() {
		return "/index";
	}
	
	@RequestMapping("/logout.do")
	public String logout() {
		session().removeAttribute("admin");
		return "redirect:/login.do";
	}
}
