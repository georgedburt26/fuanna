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

@Controller
public class LoginController extends BaseController {

	private static final Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	ResourceMapper resourceMapper;

	@RequestMapping("/login.do")
	public String login() {
		return "/login";
	}

	@RequestMapping("/index.do")
	public String index() {
		Admin admin = (Admin) session().getAttribute("admin");
		List<Resource> topResources = resourceMapper.queryResourceByAdminId(admin.getId(), true, 1);
		for (Resource topResource : topResources) {
			findResources(topResource);
		}
		return "/index";
	}

	@RequestMapping("/logout.do")
	public String logout() {
		session().removeAttribute("admin");
		return "redirect:/login.do";
	}

	private void findResources(Resource topResource) {
		List<Resource> resources = resourceMapper.queryResourceByParentId(topResource.getId());
		if (resources != null && !resources.isEmpty()) {
			topResource.setResources(resources);
			for (Resource resource : resources) {
				findResources(resource);
			}
		}
	}
}
