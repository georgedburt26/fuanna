package com.fuanna.h5.buy.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.base.BaseController;
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
		Admin admin = admin();
		if (admin != null) {
			return "redirect:/admin/index.do";
		}
		return "/admin/login";
	}

	@RequestMapping("/index.do")
	public String index() {
		Admin admin = admin();
		Map<String, Object> noticeMap = adminService.queryNoticeByCompanyId(admin.getCompanyId());
		if (noticeMap != null && !noticeMap.isEmpty()) {
			session().setAttribute("notice", noticeMap);
		}
		List<Resource> leftResources = adminService.queryResourcesByAdminId(admin.getId());
		List<Resource> allResources = adminService.queryResourcesByAdminIdType(admin.getId(), null);
		List<Resource> topResources = new ArrayList<Resource>();
		List<Resource> btnResources = new ArrayList<Resource>();
		for (Resource resource : allResources) {
			if (resource.getType() == 2) {
				topResources.add(resource);
			}
			if (resource.getType() == 3) {
				btnResources.add(resource);
			}
		}
		session().setAttribute("leftResources", leftResources);
		session().setAttribute("topResource", topResources);
		session().setAttribute("btnResources", btnResources);
		session().setAttribute("allResources", allResources);
		return "/admin/index";
	}

	@RequestMapping("/logout.do")
	public String logout() {
		session().removeAttribute("admin");
		session().removeAttribute("leftResources");
		session().removeAttribute("topResource");
		session().removeAttribute("btnResources");
		session().removeAttribute("allResources");
		return "redirect:/admin/login.do";
	}
	
	@RequestMapping("/noPermission.do")
	public String noPermission(RedirectAttributes model) {
		model.addFlashAttribute("errorCode", "9999");
		model.addFlashAttribute("errorMsg", "没有权限访问");
		return "redirect:/admin/index.do";
	}
}
