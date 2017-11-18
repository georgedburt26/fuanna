package com.fuanna.h5.buy.controller.admin;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.base.BaseController;
import com.fuanna.h5.buy.enumeration.ErrorCode;
import com.fuanna.h5.buy.enumeration.FuannaConstraints;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.mapper.AdminMapper;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.util.MD5;

@Controller
public class AdminController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(AdminController.class);

	@Autowired
	AdminMapper adminMapper;

	@RequestMapping("/admin/login.do")
	public String login() {
		return "/admin/login";
	}
	
	@RequestMapping("/admin/index.do")
	public String index() {
		return "/admin/index";
	}

	@RequestMapping("/admin/adminLogin.do")
	public String adminLogin(@RequestParam Map<String, String> map,
			RedirectAttributes model) throws Exception {
		String url = "redirect:/admin/login.do";//redirectUrl
		String username = map.get("username");
		if (StringUtils.isBlank(username)) {
			errorSendRedirectUrl("用户名不能为空", url);
		}
		String password = map.get("password");
		if (StringUtils.isBlank(password)) {
			errorSendRedirectUrl("密码不能为空", url);
		}
		String imageCode = map.get("imageCode");
		boolean isMatch = Pattern.matches("^(\\w){4}$", imageCode);
		if (StringUtils.isBlank(imageCode) || !isMatch) {
			errorSendRedirectUrl("验证码格式不对", url);
		}
		if (!imageCode.equals(session().getAttribute("admin_imageCode"))) {
			errorSendRedirectUrl("请输入正确验证码", url);
		}
		Admin admin = adminMapper.adminLogin(username, MD5.encrypt(password));
		if (admin == null) {
			errorSendRedirectUrl("用户名或密码错误", url);
		}
		model.addAttribute("");
		session().setAttribute("admin", admin);
		url = "redirect:/admin/index.do";
		return url;
	}
}
