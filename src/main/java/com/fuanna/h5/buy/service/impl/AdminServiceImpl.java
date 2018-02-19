package com.fuanna.h5.buy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fuanna.h5.buy.base.BaseConfig;
import com.fuanna.h5.buy.mapper.AdminMapper;
import com.fuanna.h5.buy.mapper.ResourceMapper;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.AdminLoginLog;
import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.model.Role;
import com.fuanna.h5.buy.model.RoleResource;
import com.fuanna.h5.buy.service.AdminService;
import com.fuanna.h5.buy.util.MD5;

@Component
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = Logger.getLogger(AdminServiceImpl.class);

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	ResourceMapper resourceMapper;

	@Override
	public List<Map<String, Object>> listCompany() {
		return adminMapper.listCompany();
	}

	@Override
	@Transactional
	public Admin adminLogin(String username, String password, String company, String ip, String location,
			String terminal, Date loginTime) {
		long companyId = Long.parseLong(company);
		Map<String, Object> map = adminMapper.queryCompanyById(companyId);
		Admin admin = adminMapper.adminLogin(username, MD5.encrypt(password), companyId);
		if (admin != null) {
			AdminLoginLog adminLoginLog = new AdminLoginLog();
			adminLoginLog.setAdminId(admin.getId());
			adminLoginLog.setUsername(admin.getUsername());
			adminLoginLog.setName(admin.getName());
			adminLoginLog.setMobilePhone(admin.getMobilePhone());
			adminLoginLog.setEmail(admin.getEmail());
			adminLoginLog.setIp(ip);
			adminLoginLog.setLoginTime(loginTime);
			adminLoginLog.setCompanyId(companyId);
			adminLoginLog.setCompanyName(map != null && !map.isEmpty() ? map.get("name") + "" : "");
			adminLoginLog.setTerminal(terminal);
			adminLoginLog.setLocation(location);
			adminMapper.addLoginLog(adminLoginLog);
		}
		return admin;
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public long addAdmin(Admin admin) {
		return adminMapper.addAdmin(admin);
	}

	@Override
	public List<Resource> queryResourcesByAdminId(long adminId) {
		List<Resource> topResources = resourceMapper.queryResourceByAdminId(adminId, null, true, 1);
		for (Resource topResource : topResources) {
			List<Resource> resources = resourceMapper.queryResourceByAdminId(adminId, topResource.getId(), false, 1);
			topResource.setResources(resources);
		}
		return topResources;
	}

	@Override
	public List<Resource> queryResources() {
		List<Resource> topResources = resourceMapper.queryResourceParent();
		for (Resource topResource : topResources) {
			findResources(topResource);
		}
		return topResources;
	}

	@Override
	public List<Resource> queryResourcesByAdminIdType(long adminId, Integer type) {
		return resourceMapper.queryResourceByAdminId(adminId, null, false, type);
	}

	@Override
	public int countAdmin(String name, String mobilePhone, String username, Long companyId) {
		return adminMapper.countAdmin(name, mobilePhone, username, companyId);
	}

	@Override
	public List<Admin> listAdmin(String name, String mobilePhone, String username, Long companyId, Integer offset,
			Integer limit) {
		return adminMapper.listAdmin(name, mobilePhone, username, companyId, offset, limit);
	}

	@Override
	public List<AdminLoginLog> listAdminLoginLog(String name, String mobilePhone, String username, Long companyId,
			Integer offset, Integer limit) {
		return adminMapper.listAdminLoginLog(name, mobilePhone, username, companyId, offset, limit);
	}

	@Override
	public int countAdminLoginLog(String name, String mobilePhone, String username, Long companyId) {
		return adminMapper.countAdminLoginLog(name, mobilePhone, username, companyId);
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public int deleteAdmin(List<Long> ids) {
		return adminMapper.deleteAdmin(ids);
	}

	@Override
	public List<Map<String, Object>> listRoles(Long companyId, Integer offset, Integer limit) {
		return adminMapper.listRoles(companyId, offset, limit);
	}

	@Override
	public int countRoles(Long companyId) {
		return adminMapper.countRoles(companyId);
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public int deleteRole(List<Long> ids) {
		int rtn = adminMapper.deleteRole(ids);
		rtn = adminMapper.deleteRoleResource(ids);
		return rtn;
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public long addRole(String name, String description, Long companyId, String[] resources) {
		Role role = new Role(name, description, new Date(), companyId);
		long rtn = adminMapper.addRole(role);
		for (String resourceId : resources) {
			adminMapper.addRoleResource(new RoleResource(role.getId(), Long.parseLong(resourceId)));
		}
		return rtn;
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public long updateRole(long id, String name, String description, Long companyId, String[] resources) {
		long rtn = 0;
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		role.setDescription(description);
		rtn = adminMapper.updateRole(role);
		rtn = adminMapper.deleteRoleResource(ids);
		for (String resourceId : resources) {
			adminMapper.addRoleResource(new RoleResource(role.getId(), Long.parseLong(resourceId)));
		}
		// 更新对应角色session权限信息
		for (Entry<String, HttpSession> entry : BaseConfig.getSessionMap(companyId).entrySet()) {
			boolean needUpdate = false;
			HttpSession session = entry.getValue();
			Admin admin = (Admin) session.getAttribute("admin");
			for (String roleId : admin.getRole().split(",")) {
				if ((id + "").equals(roleId)) {
					needUpdate = true;
					break;
				}
			}
			if (needUpdate) {
				List<Resource> allResources = resourceMapper.queryResourceByAdminId(admin.getId(), null, false, null);
				session.setAttribute("allResources", allResources);
			}
		}
		return rtn;
	}

	@Override
	public Role queryRoleById(long roleId) {
		return adminMapper.queryRoleById(roleId);
	}

	@Override
	public Admin queryAdminById(long adminId) {
		return adminMapper.queryAdminById(adminId);
	}

	@Override
	public long updateAdmin(Admin admin) {
		return adminMapper.updateAdmin(admin);
	}

	@Override
	public long publicNotice(String content, Long companyId) {
		return adminMapper.publishNotice(content, companyId);
	}

	@Override
	public Map<String, Object> getTotal() {
		return adminMapper.getTotal();
	}

	@Override
	public List<Map<String, Object>> listByContracts() {
		return adminMapper.listByContracts();
	}

	@Override
	public List<Map<String, Object>> listByPersons() {
		return adminMapper.listByPersons();
	}

	@Override
	public List<Map<String, Object>> listByPersonsDetail() {
		return adminMapper.listByPersonsDetail();
	}

	@Override
	public Map<String, Object> queryNoticeByCompanyId(Long companyId) {
		return adminMapper.queryNoticeByCompanyId(companyId);
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

	@Override
	public List<Map<String, Object>> listAdminOnline(Long companyId, Integer offset, Integer limit) {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		Map<String, HttpSession> sessionMap = BaseConfig.getSessionMap(companyId);
		if (sessionMap != null) {
			for (Entry<String, HttpSession> entry : sessionMap.entrySet()) {
				String sessionId = entry.getKey();
				Admin admin = (Admin) entry.getValue().getAttribute("admin");
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("sessionId", sessionId);
				row.put("id", admin.getId());
				row.put("name", admin.getName());
				row.put("username", admin.getUsername());
				row.put("mobilePhone", admin.getMobilePhone());
				row.put("email", admin.getEmail());
				row.put("ip", admin.getIp());
				row.put("location", admin.getLocation());
				row.put("terminal", admin.getTerminal());
				row.put("loginTime", admin.getLoginTime());
				rows.add(row);
			}
			Collections.sort(rows, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					Long i = ((Date) o2.get("loginTime")).getTime() - ((Date) o1.get("loginTime")).getTime();
					return i.intValue();
				}
			});
			if (rows.size() - offset < limit) {
				limit = rows.size() - offset;
			}
		}
		return rows == null || rows.isEmpty() ? null : rows.subList(offset, limit);
	}

	@Override
	public int countAdminOnline(Long companyId) {
		int size = 0;
		if (BaseConfig.getSessionMap(companyId) != null && !BaseConfig.getSessionMap(companyId).isEmpty()) {
			size = BaseConfig.getSessionMap(companyId).size();
		}
		return size;
	}
}
