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
import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.DataTable;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.service.AdminService;
import com.fuanna.h5.buy.util.JsonUtils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	private static final Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@RequestMapping("/adminLogin.do")
	public String adminLogin(@RequestParam Map<String, String> params, RedirectAttributes model) throws Exception {
		String url = "redirect:/admin/login.do";// redirectUrl
		String username = params.get("username");
		if (StringUtils.isBlank(username)) {
			error("用户名不能为空", url);
		}
		String password = params.get("password");
		if (StringUtils.isBlank(password)) {
			error("密码不能为空", url);
		}
		String imageCode = params.get("imageCode");
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
		url = "redirect:/admin/index.do";// 登陆成功
		return url;
	}

	/******** 权限管理 *********/
	@RequestMapping("/adminManage.do")
	public String adminManage() {
		return "/admin/admin_manage";
	}

	@RequestMapping("/adminManageList.do")
	public @ResponseBody RstResult adminManageList() throws Exception {
		RstResult rstResult = null;
		String data = request().getParameter("rows");
		if (StringUtils.isNotBlank(data)) {
			Integer sEcho = null, iDisplayStart = null, iDisplayLength = null;
			JSONArray json = JSONArray.fromObject(data);
			for (int i = 0; i < json.size(); i++) {
				if (json.getJSONObject(i).getString("name").equals("sEcho")) {
					sEcho = Integer.parseInt(json.getJSONObject(i).getString("value"));
				}
				if (json.getJSONObject(i).getString("name").equals("iDisplayStart")) {
					iDisplayStart = Integer.parseInt(json.getJSONObject(i).getString("value"));
				}
				if (json.getJSONObject(i).getString("name").equals("iDisplayLength")) {
					iDisplayLength = Integer.parseInt(json.getJSONObject(i).getString("value"));
				}
			}
			List<Admin> rows = adminService.listAdmin(null, null, null, iDisplayStart, iDisplayLength);
			int count = adminService.countAdmin(null, null, null);
			if (sEcho != null) {
			DataTable dataTable = new DataTable(sEcho + 1, rows.size(), count, rows);
			rstResult = new RstResult(ErrorCode.CG, "获取列表成功", dataTable);
			}
		}
		return rstResult;
	}
}
