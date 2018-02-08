package com.fuanna.h5.buy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
public class AdminServiceImpl implements AdminService{
	
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
	public Admin adminLogin(String username, String password, String company) {
		long companyId = Long.parseLong(company);
		Map<String, Object> map = adminMapper.queryCompanyById(companyId);
		Admin admin = adminMapper.adminLogin(username, MD5.encrypt(password), companyId);
		if (admin != null) {
			String ip = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
			AdminLoginLog adminLoginLog = new AdminLoginLog();
			adminLoginLog.setAdminId(admin.getId());
			adminLoginLog.setUsername(admin.getUsername());
			adminLoginLog.setName(admin.getName());
			adminLoginLog.setMobilePhone(admin.getMobilePhone());
			adminLoginLog.setEmail(admin.getEmail());
			adminLoginLog.setIp(ip);
			adminLoginLog.setLoginTime(new Date());
			adminLoginLog.setCompanyId(companyId);
			adminLoginLog.setCompanyName(map != null && !map.isEmpty() ? map.get("name") + "" : "");
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
		List<Resource> topResources = resourceMapper.queryResourceByAdminId(adminId, true, null);
		for (Resource topResource : topResources) {
			List<Resource> resources = resourceMapper.queryResourceByParentId(topResource.getId());
			topResource.setResources(resources);
		}
		return topResources;
	}
	
	@Override
	public List<Resource> queryResources() {
		List<Resource> topResources = resourceMapper.queryResourceParent();
		for (Resource topResource : topResources) {
			List<Resource> resources = resourceMapper.queryResourceByParentId(topResource.getId());
			topResource.setResources(resources);
		}
		return topResources;
	}
	
//	private void findResources(Resource topResource) {
//		List<Resource> resources = resourceMapper.queryResourceByParentId(topResource.getId());
//		if (resources != null && !resources.isEmpty()) {
//			topResource.setResources(resources);
//			for (Resource resource : resources) {
//				findResources(resource);
//			}
//		}
//	}

	@Override
	public int countAdmin(String name, String mobilePhone, String username, Long companyId) {
		return adminMapper.countAdmin(name, mobilePhone, username, companyId);
	}

	@Override
	public List<Admin> listAdmin(String name, String mobilePhone, String username, Long companyId, Integer offset, Integer limit) {
		return adminMapper.listAdmin(name, mobilePhone, username, companyId, offset, limit);
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

	@Override
	public long publicNotice(String content, Long companyId) {
		return adminMapper.publishNotice(content, companyId);
	}
}
