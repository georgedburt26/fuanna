package com.fuanna.h5.buy.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.base.BaseController;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.service.AdminService;

@Controller
public class AdminController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@RequestMapping("/adminLogin.do")
	public String adminLogin(@RequestParam Map<String, String> map, RedirectAttributes model) throws Exception {
		String url = "redirect:/login.do";//redirectUrl
		String username = map.get("username");
		if (StringUtils.isBlank(username)) {
			error("用户名不能为空", url);
		}
		String password = map.get("password");
		if (StringUtils.isBlank(password)) {
			error("密码不能为空", url);
		}
		String imageCode = map.get("imageCode");
		boolean isMatch = Pattern.matches("^(\\w){4}$", imageCode);
		if (StringUtils.isBlank(imageCode) || !isMatch) {
			error("验证码格式不对", url);
		}
		if (!imageCode.equals(session().getAttribute("admin_imageCode"))) {
			error("请输入正确验证码", url);
		}
		Admin admin = adminService.adminLogin(username, password);
		if (admin == null) {
			error("用户名或密码错误", url);
		}
		session().setAttribute("admin", admin);
		url = "redirect:/index.do";//登陆成功
		return url;
	}
}
