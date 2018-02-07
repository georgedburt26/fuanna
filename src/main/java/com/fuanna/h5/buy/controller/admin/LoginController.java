package com.fuanna.h5.buy.controller.admin;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/admin")
public class LoginController extends BaseController {

	@Autowired
	AdminService adminService;
	
	@RequestMapping("/login.do")
	public String login() {
		Admin admin = (Admin) session().getAttribute("admin");
		if (admin != null) {
			return "redirect:/admin/index.do";
		}
		return "/admin/login";
	}

	@RequestMapping("/index.do")
	public String index() {
		Admin admin = (Admin) session().getAttribute("admin");
		List<Resource> resources = adminService.queryResourcesByAdminId(admin.getId());
		Long companyId = admin().getCompanyId();
		Map<String, Object> noticeMap = adminService.queryNoticeByCompanyId(companyId);
		if (noticeMap != null && !noticeMap.isEmpty()) {
			session().setAttribute("notice", noticeMap);
		}
		session().setAttribute("resources", resources);
		return "/admin/index";
	}

	@RequestMapping("/logout.do")
	public String logout() {
		session().removeAttribute("admin");
		return "redirect:/admin/login.do";
	}
}
