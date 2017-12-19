package com.fuanna.h5.buy.controller.admin;

import java.util.ArrayList;
import java.util.Date;
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
import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.service.AdminService;
import com.fuanna.h5.buy.util.MD5;

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
		String username = StringUtils.isBlank(request().getParameter("username")) ? null
				: request().getParameter("username");
		String name = StringUtils.isBlank(request().getParameter("name")) ? null : request().getParameter("name");
		String mobilePhone = StringUtils.isBlank(request().getParameter("mobilePhone")) ? null
				: request().getParameter("mobilePhone");
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
			List<Admin> rows = adminService.listAdmin(name, mobilePhone, username, iDisplayStart, iDisplayLength);
			int count = adminService.countAdmin(name, mobilePhone, username);
			DataTable dataTable = new DataTable(sEcho + 1, rows.size(), count, rows);
			rstResult = new RstResult(ErrorCode.CG, "获取列表成功", dataTable);
		}
		return rstResult;
	}

	@RequestMapping("/deleteAdmin.do")
	public @ResponseBody RstResult deleteAdmin() throws Exception {
		List<Long> idlist = new ArrayList<Long>();
		String ids = request().getParameter("ids");
		if (StringUtils.isBlank(ids)) {
			error("参数不能为空");
		}
		for (String id : ids.split(",")) {
			idlist.add(Long.parseLong(id));
		}
		return adminService.deleteAdmin(idlist) > 0 ? new RstResult(ErrorCode.CG, "删除成功")
				: new RstResult(ErrorCode.SB, "删除失败");
	}

	@RequestMapping("/addAdminIndex.do")
	public String addAdminIndex() {
		String type = request().getParameter("type");
		if ("2".equals(type)) {// 查看

		}
		if ("3".equals(type)) {// 修改

		}
		return "/admin/admin_index";
	}

	@RequestMapping("/addAdmin.do")
	public @ResponseBody RstResult addAdmin(@RequestParam Map<String, String> params) throws Exception {
		String username = params.get("username");
		if (StringUtils.isBlank(username)) {
			error("用户名不能为空");
		}
		String password = params.get("password");
		if (StringUtils.isBlank(password)
				|| !Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", password)) {
			error("请输入8到16位的数字字母组合密码");
		}
		String confirmpassword = params.get("confirmpassword");
		if (StringUtils.isBlank(confirmpassword)) {
			error("确认密码不能为空");
		}
		if (!password.equals(confirmpassword)) {
			error("密码输入不一致");
		}
		String name = params.get("name");
		if (StringUtils.isBlank(name)) {
			error("姓名不能为空");
		}
		String mobilePhone = params.get("mobilePhone");
		if (StringUtils.isBlank(mobilePhone) || !Pattern.matches("^[1][3,4,5,7,8][0-9]{9}$", mobilePhone)) {
			error("请输入正确的手机格式");
		}
		String email = params.get("email");
		if (StringUtils.isBlank(email)) {
			error("手机号不能为空");
		}
		String role = params.get("role");
		if (StringUtils.isBlank(role)) {
			error("角色不能为空");
		}
		String headImg = params.get("headImg");
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(MD5.encrypt(password));
		admin.setName(name);
		admin.setMobilePhone(mobilePhone);
		admin.setEmail(email);
		admin.setHeadImg(headImg);
		admin.setRole(role);
		admin.setCreateTime(new Date());
		return adminService.addAdmin(admin) > 0 ? new RstResult(ErrorCode.CG, "保存成功")
				: new RstResult(ErrorCode.SB, "保存失败");
	}

	@RequestMapping("/listRoles.do")
	public @ResponseBody RstResult listRoles() {
		List<Map<String, Object>> roles = adminService.listRoles(null, null);
		return new RstResult(ErrorCode.CG, "查询成功", roles);
	}

	@RequestMapping("/roleManage.do")
	public String roleManage() {
		return "/admin/role_manage";
	}

	@RequestMapping("/roleManageList.do")
	public @ResponseBody RstResult roleManageList() throws Exception {
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
			List<Map<String, Object>> rows = adminService.listRoles(iDisplayStart, iDisplayLength);
			int count = adminService.countRoles();
			DataTable dataTable = new DataTable(sEcho + 1, rows.size(), count, rows);
			rstResult = new RstResult(ErrorCode.CG, "获取列表成功", dataTable);
		}
		return rstResult;
	}

	@RequestMapping("/addRoleIndex.do")
	public String addRoleIndex() {
		String type = request().getParameter("type");
		String id = request().getParameter("id");
		if ("2".equals(type) || "3".equals(type)) {// 查看
			request().setAttribute("role", adminService.queryRoleById(Long.parseLong(id)));
		}
		request().setAttribute("type", type);
		request().setAttribute("id", id);
		return "/admin/role_index";
	}

	@RequestMapping("/addRole.do")
	public @ResponseBody RstResult addRole(@RequestParam Map<String, String> params) throws Exception {
		String name = params.get("name");
		if (StringUtils.isBlank(name)) {
			error("角色名不能为空");
		}
		String description = params.get("description");
		if (StringUtils.isBlank(description)) {
			error("描述不能为空");
		}
		String resources = params.get("resources");
		if (StringUtils.isBlank(resources)) {
			error("权限模块不能为空");
		}
		String type = params.get("type");
		String id = params.get("id");
		long rtn = 0;
		if (type.equals("1")) {
			rtn = adminService.addRole(name, description, resources.split(","));
		}
		if (type.equals("3")) {
			rtn = adminService.updateRole(Long.parseLong(id), name, description, resources.split(","));
		}
		return rtn > 0 ? new RstResult(ErrorCode.CG, type.equals(1) ? "添加角色成功" : "修改角色成功") : new RstResult(ErrorCode.SB, type.equals(1) ? "添加角色失败" : "修改角色失败");
	}

	@RequestMapping("/deleteRole.do")
	public @ResponseBody RstResult deleteRole() throws Exception {
		List<Long> idlist = new ArrayList<Long>();
		String ids = request().getParameter("ids");
		if (StringUtils.isBlank(ids)) {
			error("参数不能为空");
		}
		for (String id : ids.split(",")) {
			idlist.add(Long.parseLong(id));
		}
		return adminService.deleteRole(idlist) > 0 ? new RstResult(ErrorCode.CG, "删除成功")
				: new RstResult(ErrorCode.SB, "删除失败");
	}

	@RequestMapping("/listResources.do")
	public @ResponseBody RstResult listResources() {
		List<Resource> resources = adminService.queryResources();
		return new RstResult(ErrorCode.CG, "查询成功", resources);
	}
}
