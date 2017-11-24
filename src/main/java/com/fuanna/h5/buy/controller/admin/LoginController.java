package com.fuanna.h5.buy.controller.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fuanna.h5.buy.base.BaseController;
import com.fuanna.h5.buy.mapper.ResourceMapper;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.service.AdminService;

@Controller
public class LoginController extends BaseController {

	@Autowired
	AdminService adminService;
	
	@RequestMapping("/login.do")
	public String login() {
		return "/login";
	}

	@RequestMapping("/index.do")
	public String index() {
		Admin admin = (Admin) session().getAttribute("admin");
		List<Resource> resources = adminService.queryResourcesByAdminId(admin.getId());
		session().setAttribute("resources", resources);
		return "/index";
	}

	@RequestMapping("/logout.do")
	public String logout() {
		session().removeAttribute("admin");
		return "redirect:/login.do";
	}
}
