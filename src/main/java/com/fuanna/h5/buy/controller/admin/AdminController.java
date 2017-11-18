package com.fuanna.h5.buy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping({"/admin/login.do"})
	public String login() {
		
		return "/admin/login";
	}
}
