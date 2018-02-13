package com.fuanna.h5.buy.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.base.BaseConfig;
import com.fuanna.h5.buy.base.BaseController;
import com.fuanna.h5.buy.base.HttpsClient;
import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.DataTable;
import com.fuanna.h5.buy.model.ProductSku;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.model.Weather;
import com.fuanna.h5.buy.service.AdminService;
import com.fuanna.h5.buy.service.ProductService;
import com.fuanna.h5.buy.util.MD5;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	private static final Logger logger = Logger.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;
	
	@Autowired
	ProductService productService;

	@RequestMapping("/listCompany.do")
	public @ResponseBody RstResult listCompany() throws Exception {
		return new RstResult(ErrorCode.CG, "", adminService.listCompany());
	}
	
	@RequestMapping("/adminLogin.do")
	public String adminLogin(@RequestParam Map<String, String> params, RedirectAttributes model) throws Exception {
		String url = "redirect:/admin/login.do";// redirectUrl
		String company = params.get("company");
		if (StringUtils.isBlank(company)) {
			error("地区代理不能为空", url);
		}
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
		Admin admin = adminService.adminLogin(username, password, company);
		if (admin == null) {
			error("用户名或密码错误", url);
		}
		if (admin.getEnable() != 1) {
			error("用户被禁用", url);
		}
		admin.setIp(request().getRemoteAddr());
		admin.setLocation(params.get("location").equals("全国") ? null : params.get("location"));
		admin.setTerminal(params.get("terminal"));
		session().setAttribute("admin", admin);
		session().removeAttribute("admin_imageCode");
		url = "redirect:/admin/index.do";// 登陆成功
		return url;
	}
	/******** 商品管理*********/
	@RequestMapping("/productManage.do")
	public String productManage() {
		return "/admin/product_manage";
	}
	
	@RequestMapping("/productManageList.do")
	public @ResponseBody RstResult productManageList() throws Exception {
		Long companyId = admin().getCompanyId();
		RstResult rstResult = null;
		String data = request().getParameter("rows");
		String barcode = StringUtils.isBlank(request().getParameter("barcode")) ? null
				: request().getParameter("barcode");
		String name = StringUtils.isBlank(request().getParameter("name")) ? null : request().getParameter("name");
		String category = StringUtils.isBlank(request().getParameter("category")) ? null
				: request().getParameter("category");
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
			List<Map<String, Object>> rows = productService.listProductSkuByBarcode(barcode, name, category, companyId, iDisplayStart, iDisplayLength);
			int count = productService.countProductSkuByBarcode(barcode, name, category, companyId);
			DataTable dataTable = new DataTable(sEcho + 1, rows.size(), count, rows);
			rstResult = new RstResult(ErrorCode.CG, "获取列表成功", dataTable);
		}
		return rstResult;
	}
	
	@RequestMapping("/updateProductIndex.do")
	public String productIndex() {
		return "/admin/product_index";
	}
	
	/********公告管理*********/
	@RequestMapping("/noticeManage.do")
	public String noticeManage() {
		return "/admin/notice_manage";
	}
	
	@RequestMapping("/publicNotice.do")
	public @ResponseBody RstResult publicNotice(@RequestParam Map<String, String> params) throws Exception {
		String content = params.get("content");
		RstResult rstResult = null;
		if (StringUtils.isBlank(content)) {
			error("发布内容不能为空");
		}
		Long companyId = admin().getCompanyId();
		long rtn = adminService.publicNotice(content, companyId);
		if (rtn > 0) {
			Map<String, String> map = new HashMap<String, String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			map.put("content", content);
			map.put("publishTime", sdf.format(new Date()));
			rstResult = new RstResult(ErrorCode.CG, "发布成功", map);
		}
		else {
			rstResult = new RstResult(ErrorCode.SB, "发布失败", "");
		}
		return rstResult;
	}
	
	/******** 库存管理*********/
	@RequestMapping("/stockinManage.do")
	public String stockinManage() {
		return "/admin/stockin_manage";
	}
	@RequestMapping("/stockIn.do")
	public @ResponseBody RstResult stockIn(@RequestParam Map<String, String> params) throws Exception {
		if (StringUtils.isBlank(params.get("data"))) {
			error("入库数据不能为空");
		}
		RstResult rstResult = null;
		Long companyId = admin().getCompanyId();
		JSONArray array = JSONArray.fromObject(params.get("data"));
		Iterator<JSONObject> itr = array.iterator();
		List<ProductSku> productSkus = new ArrayList<ProductSku>();
		while (itr.hasNext()) {
			JSONObject object = itr.next();
			String barcode = object.getString("barcode");
			Integer num = Integer.parseInt(object.getString("num"));
			String productName = object.getString("productName");
			String category = object.getString("category");
			if (num > 0) {
				ProductSku productSku = new ProductSku();
				productSku.setBarcode(barcode);
				productSku.setInventory(num);
				productSku.setCompanyId(companyId);
				productSku.setProductName(productName);
				productSku.setCategory(category);
				productSkus.add(productSku);
			}
		}
		List<ProductSku> fails = productService.stockIn(productSkus);
		if (fails.isEmpty()) {
			rstResult = new RstResult(ErrorCode.CG, "入库成功", "");
		}
		else {
			String msg = productSkus.size() == fails.size() ? "入库失败" : "部分入库失败，失败为列表中数据";
			rstResult = new RstResult(ErrorCode.CG, msg, fails);
		}
		return rstResult;
	}
	@RequestMapping("/stockoutManage.do")
	public String stockoutManage() {
		return "/admin/stockout_manage";
	}
	
	@RequestMapping("/findProductByBarCode.do")
	public @ResponseBody RstResult findProductByBarCode(@RequestParam Map<String, String> params) throws Exception {
		String barcode = params.get("barcode");
		if (StringUtils.isBlank("barcode")) {
			error("条形码不能为空");
		}
		Long companyId = admin().getCompanyId();
		Map<String, String> productMap = productService.findProductByBarCode(barcode, companyId);
		return new RstResult(ErrorCode.CG, "", productMap);
	}

	/******** 权限管理 *********/
	@RequestMapping("/adminManage.do")
	public String adminManage() {
		return "/admin/admin_manage";
	}

	@RequestMapping("/adminManageList.do")
	public @ResponseBody RstResult adminManageList() throws Exception {
		Long companyId = admin().getCompanyId();
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
			List<Admin> rows = adminService.listAdmin(name, mobilePhone, username, companyId, iDisplayStart, iDisplayLength);
			int count = adminService.countAdmin(name, mobilePhone, username, companyId);
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
		request().setAttribute("type", "1");
		return "/admin/admin_index";
	}
	
	@RequestMapping("/readAdminIndex.do")
	public String readAdminIndex() {
		String id = request().getParameter("id");
		request().setAttribute("admin", adminService.queryAdminById(Long.parseLong(id)));
		request().setAttribute("type", "2");
		request().setAttribute("id", id);
		return "/admin/admin_index";
	}
	
	@RequestMapping("/updateAdminIndex.do")
	public String updateAdminIndex() {
		String id = request().getParameter("id");
		request().setAttribute("admin", adminService.queryAdminById(Long.parseLong(id)));
		request().setAttribute("type", "3");
		request().setAttribute("id", id);
		return "/admin/admin_index";
	}

	@RequestMapping("/addAdmin.do")
	public @ResponseBody RstResult addAdmin(@RequestParam Map<String, String> params) throws Exception {
		Long companyId = admin().getCompanyId();
		String username = params.get("username");
		if (StringUtils.isBlank(username)) {
			error("用户名不能为空");
		}
		String password = null;
		if ("1".equals(params.get("type"))) {
		password = params.get("password");
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
		if ("1".equals(params.get("type"))) {
		admin.setPassword(MD5.encrypt(password));
		}
		if (StringUtils.isNotBlank(params.get("adminId"))) {
		admin.setId(Long.parseLong(params.get("adminId")));
		}
		admin.setName(name);
		admin.setMobilePhone(mobilePhone);
		admin.setEmail(email);
		admin.setHeadImg(headImg);
		admin.setRole(role);
		admin.setCreateTime(new Date());
		admin.setCompanyId(companyId);
		if ("1".equals(params.get("type"))) {
			return adminService.addAdmin(admin) > 0 ? new RstResult(ErrorCode.CG, "保存成功")
					: new RstResult(ErrorCode.SB, "保存失败");
		}
		else {
			return adminService.updateAdmin(admin) > 0 ? new RstResult(ErrorCode.CG, "保存成功")
					: new RstResult(ErrorCode.SB, "保存失败");
		}
	}

	@RequestMapping("/listRoles.do")
	public @ResponseBody RstResult listRoles() {
		Long companyId = admin().getCompanyId();
		List<Map<String, Object>> roles = adminService.listRoles(companyId, null, null);
		return new RstResult(ErrorCode.CG, "查询成功", roles);
	}

	@RequestMapping("/roleManage.do")
	public String roleManage() {
		return "/admin/role_manage";
	}

	@RequestMapping("/roleManageList.do")
	public @ResponseBody RstResult roleManageList() throws Exception {
		Long companyId = admin().getCompanyId();
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
			List<Map<String, Object>> rows = adminService.listRoles(companyId, iDisplayStart, iDisplayLength);
			int count = adminService.countRoles(companyId);
			DataTable dataTable = new DataTable(sEcho + 1, rows.size(), count, rows);
			rstResult = new RstResult(ErrorCode.CG, "获取列表成功", dataTable);
		}
		return rstResult;
	}

	@RequestMapping("/addRoleIndex.do")
	public String addRoleIndex() {
		request().setAttribute("type", "1");
		return "/admin/role_index";
	}
	
	@RequestMapping("/readRoleIndex.do")
	public String readRoleIndex() {
		String id = request().getParameter("id");
		request().setAttribute("role", adminService.queryRoleById(Long.parseLong(id)));
		request().setAttribute("treeResources", JSONArray.fromObject(BaseConfig.getTreeResources()));
		request().setAttribute("type", 2);
		request().setAttribute("id", id);
		return "/admin/role_index";
	}
	
	@RequestMapping("/updateRoleIndex.do")
	public String updateRoleIndex() {
		String id = request().getParameter("id");
		request().setAttribute("role", adminService.queryRoleById(Long.parseLong(id)));
		request().setAttribute("treeResources", JSONArray.fromObject(BaseConfig.getTreeResources()));
		request().setAttribute("type", "3");
		request().setAttribute("id", id);
		return "/admin/role_index";
	}

	@RequestMapping("/addRole.do")
	public @ResponseBody RstResult addRole(@RequestParam Map<String, String> params) throws Exception {
		Long companyId = admin().getCompanyId();
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
			rtn = adminService.addRole(name, description, companyId, resources.split(","));
		}
		if (type.equals("3")) {
			rtn = adminService.updateRole(Long.parseLong(id), name, description, companyId, resources.split(","));
		}
		return rtn > 0 ? new RstResult(ErrorCode.CG, type.equals("1") ? "添加角色成功" : "修改角色成功") : new RstResult(ErrorCode.SB, type.equals("1") ? "添加角色失败" : "修改角色失败");
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
		return new RstResult(ErrorCode.CG, "查询成功", BaseConfig.getTreeResources());
	}
	
	@RequestMapping("/getLocationInfo.do")
	public @ResponseBody RstResult getLocationInfo() throws Exception {
		RstResult rstResult = null;
		String city = admin().getLocation();
		if (StringUtils.isBlank(city)) {
		   rstResult = new RstResult(ErrorCode.SB, "无法定位获取到天气信息");
		}
		ConcurrentHashMap<String, Map<String, Weather>> map = BaseConfig.getWeatherMap();
		Map<String, Weather> weatherMap = map.get(BaseConfig.sdf.format(new Date()));
		if (weatherMap != null && weatherMap.get(city) != null) {
			rstResult = new RstResult(ErrorCode.CG, "", weatherMap.get(city));
		}
		else {
			Weather weatherObject = new Weather();
			Map<String, String> nameValuePair = new HashMap<String, String>();
			nameValuePair.put("location", city);
			nameValuePair.put("ak", BaseConfig.getBaseConfig("baidu_ak"));
			nameValuePair.put("output", "json");
			StringBuffer urlBuffer = new StringBuffer();
			urlBuffer.append(BaseConfig.getBaseConfig("baidu_weather_url"))
			         .append("?")
			         .append("ak=" + BaseConfig.getBaseConfig("baidu_ak"))
			         .append("&")
			         .append("output=json")
			         .append("&")
			         .append("location=")
			         .append(city);
			String weatherString = HttpsClient.get(urlBuffer.toString(), null, "application/json;charset=utf-8");
				JSONObject weatherJson = JSONObject.fromObject(weatherString);
				if (!weatherJson.getString("error").equals("0")) {
					rstResult = new RstResult(ErrorCode.SB, "获取天气信息失败");
				}
				else {
					JSONObject result = weatherJson.getJSONArray("results").getJSONObject(0);
					String pm25 = result.getString("pm25");
					JSONObject weatherData = result.getJSONArray("weather_data").getJSONObject(0);
					String weather = weatherData.getString("weather");
					String wind = weatherData.getString("win");
					String temperature = weatherData.getString("temperature");
					String date = weatherJson.getString("date");
					weatherObject.setLocation(city);
					weatherObject.setPm25(pm25);
					weatherObject.setWeatherInfo(weather);
					weatherObject.setDate(date);
					weatherObject.setWind(wind);
					weatherObject.setTemperature(temperature);
					BaseConfig.putWeatherMap(date, city, weatherObject);
					rstResult = new RstResult(ErrorCode.CG, "", weatherObject);
				}	
		}
		return rstResult;
	}
	
//	@RequestMapping("/exportExcel.do")
//	// @Prev(module = "Registration", oprator = "add")
//	public void exportExcel(HttpServletResponse response) throws Exception {
//		try {
//			final CountDownLatch countDownLatch = new CountDownLatch(3);
//			final Map<String, Object> total = adminService.getTotal();
//			final List<Map<String, Object>> byContracts = adminService.listByContracts();
//			final List<Map<String, Object>> byPersons = adminService.listByPersons();
//			final List<Map<String, Object>> byPersonsDetail = adminService.listByPersonsDetail();
//			OutputStream out = null;
//			String realPath = session().getServletContext().getRealPath("/");
//			String filename = "登记数据.xlsx";
//			String filepath = realPath + "/template/template.xlsx";
//			final XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filepath));
//			final XSSFCellStyle style = wb.createCellStyle();
//			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//			//底稿
//			new Thread(){
//				public void run() {
//					XSSFSheet sheet1 = wb.getSheetAt(0);
//					sheet1.getRow(1).getCell(7).setCellValue(total.get("count") + "");
//					sheet1.getRow(1).getCell(8).setCellValue(total.get("totalAmount") + "");
//					sheet1.getRow(1).getCell(9).setCellValue(total.get("totalRealAmount") + "");
//					sheet1.getRow(1).getCell(10).setCellValue(total.get("totalHascashAmount") + "");
//					sheet1.getRow(1).getCell(11).setCellValue(total.get("totalUncashAmount") + "");
//
//					int rowIndex = 2;
//					int i = 0;
//					//nextNum != null && num.split("-")[0].equals(nextNum.split("-")[0]) ? num : num.split("-")[0]
//					for (Map<String, Object> byContract : byContracts) {
//						List<Object> list = new ArrayList<Object>();
//						System.out.println(i + "," + byContracts.size());
//						String num = byContract.get("num") == null ? "" : byContract.get("num").toString();
//						int nextRowIndex = ((i + 1) == byContracts.size()) ? i : i + 1; 
//						String nextNum = byContracts.get(nextRowIndex) != null && byContracts.get(nextRowIndex).get("num") != null ? byContracts.get(nextRowIndex).get("num").toString() : "";
//						list.add(StringUtils.isNotBlank(nextNum) && num.split("-")[0].equals(nextNum.split("-")[0]) ? num : num.split("-")[0]);
//						list.add(byContract.get("realName") == null ? "" : byContract.get("realName"));
//						list.add(byContract.get("createDate") == null ? "" : byContract.get("createDate").toString());
//						list.add(byContract.get("idNo") == null ? "" : byContract.get("idNo"));
//						list.add(byContract.get("cellPhone") == null ? "" : byContract.get("cellPhone"));
//						list.add(byContract.get("nativeAddress") == null ? "" : byContract.get("nativeAddress"));
//						list.add("");
//						list.add("1");
//						list.add(byContract.get("amount"));
//						list.add(byContract.get("realAmount"));
//						list.add(byContract.get("hascashAmount"));
//						list.add(byContract.get("uncashAmount"));
//						list.add(byContract.get("remark") == null ? "" : byContract.get("remark"));
//						list.add(byContract.get("midName"));
//						XSSFRow row = sheet1.createRow(rowIndex);
//						int columnIndex = 0;
//						for (Object object : list) {
//							XSSFCell cell = row.createCell(columnIndex);
//							cell.setCellStyle(style);
//							cell.setCellValue(object.toString());
//							columnIndex ++;
//						}
//						rowIndex ++;
//						i++;
//					}
//					countDownLatch.countDown();
//				};
//			}.start();
//			//吸收公众存款明细表
//			new Thread() {
//				public void run() {
//					XSSFSheet sheet2 = wb.getSheetAt(1);
//					sheet2.getRow(1).getCell(7).setCellValue(total.get("count") + "");
//					sheet2.getRow(1).getCell(8).setCellValue(total.get("totalAmount") + "");
//					sheet2.getRow(1).getCell(9).setCellValue(total.get("totalRealAmount") + "");
//					sheet2.getRow(1).getCell(10).setCellValue(total.get("totalHascashAmount") + "");
//					sheet2.getRow(1).getCell(11).setCellValue(total.get("totalUncashAmount") + "");
//
//					int rowIndex = 2;
//					for (Map<String, Object> byPerson : byPersons) {
//						List<Object> list = new ArrayList<Object>();
//						list.add(byPerson.get("id") == null ? "" : byPerson.get("id"));
//						list.add(byPerson.get("realName") == null ? "" : byPerson.get("realName"));
//						list.add(byPerson.get("createDate") == null ? "" : byPerson.get("createDate").toString());
//						list.add(byPerson.get("idNo") == null ? "" : byPerson.get("idNo"));
//						list.add(byPerson.get("cellPhone") == null ? "" : byPerson.get("cellPhone"));
//						list.add(byPerson.get("nativeAddress") == null ? "" : byPerson.get("nativeAddress"));
//						list.add("");
//						list.add(byPerson.get("count") == null ? "" : byPerson.get("count"));
//						list.add(byPerson.get("amount") == null ? "" : byPerson.get("amount"));
//						list.add(byPerson.get("realAmount") == null ? "" : byPerson.get("realAmount"));
//						list.add(byPerson.get("hascashAmount") == null ? "" : byPerson.get("hascashAmount"));
//						list.add(byPerson.get("uncashAmount") == null ? "" : byPerson.get("uncashAmount"));
//						list.add(byPerson.get("remark") == null ? "" : byPerson.get("remark"));
//						list.add(byPerson.get("midName") == null ? "" : byPerson.get("midName"));
//						XSSFRow row = sheet2.createRow(rowIndex);
//						int columnIndex = 0;
//						for (Object object : list) {
//							XSSFCell cell = row.createCell(columnIndex);
//							cell.setCellStyle(style);
//							cell.setCellValue(object.toString());
//							columnIndex ++;
//						}
//						rowIndex ++;
//					}
//					countDownLatch.countDown();
//				};
//			}.start();
//			//兑付情况明细表
//			new Thread(){
//				public void run() {
//					XSSFSheet sheet3 = wb.getSheetAt(2);
//					sheet3.getRow(2).getCell(8).setCellValue(total.get("totalUncashAmount") + "");
//					System.out.println(sheet3.getRow(2).getCell(0).getStringCellValue());
//					int rowIndex = 3;
//					for (Map<String, Object> byPersonDetail : byPersonsDetail) {
//						List<Object> list = new ArrayList<Object>();
//						list.add(byPersonDetail.get("id") == null ? "" : byPersonDetail.get("id"));
//						list.add(byPersonDetail.get("realName") == null ? "" : byPersonDetail.get("realName"));
//						list.add(byPersonDetail.get("idNo") == null ? "" : byPersonDetail.get("idNo"));
//						list.add(byPersonDetail.get("cellPhone") == null ? "" : byPersonDetail.get("cellPhone"));
//						list.add(byPersonDetail.get("nativeAddress") == null ? "" : byPersonDetail.get("nativeAddress"));
//						list.add("");
//						list.add(byPersonDetail.get("contracts").toString().split(",").length > 1 ? byPersonDetail.get("contracts").toString() : byPersonDetail.get("contracts").toString().replaceAll("-1", ""));
//						list.add("");
//						list.add(byPersonDetail.get("uncashAmount") == null ? "" : byPersonDetail.get("uncashAmount"));
//						XSSFRow row = sheet3.createRow(rowIndex);
//						int columnIndex = 0;
//						for (Object object : list) {
//							XSSFCell cell = row.createCell(columnIndex);
//							cell.setCellStyle(style);
//							cell.setCellValue(object.toString());
//							columnIndex ++;
//						}
//						rowIndex ++;
//					}
//					countDownLatch.countDown();
//				};
//			}.start();
//			countDownLatch.await();
//			// 下载excel
//			response.setContentType("application/msexcel");
//			// response.setContentType("multipart/form-data");
//			response.setHeader("Content-disposition",
//					"attachment;filename=" + new String(filename.toString().getBytes("utf-8"), "ISO8859-1"));
//			out = response.getOutputStream();
//			wb.write(out);
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			logger.error("Registration error", e);
//			throw e;
//		}
//	}
}
