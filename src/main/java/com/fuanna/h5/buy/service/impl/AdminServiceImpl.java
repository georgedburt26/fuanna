package com.fuanna.h5.buy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	@Transactional
	public Admin adminLogin(String username, String password) {
		Admin admin = adminMapper.adminLogin(username, MD5.encrypt(password));
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
		List<Resource> topResources = resourceMapper.queryResourceByAdminId(adminId, true, 1);
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
	public int countAdmin(String name, String mobilePhone, String username) {
		return adminMapper.countAdmin(name, mobilePhone, username);
	}

	@Override
	public List<Admin> listAdmin(String name, String mobilePhone, String username, Integer offset, Integer limit) {
		return adminMapper.listAdmin(name, mobilePhone, username, offset, limit);
	}

	@Override
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public int deleteAdmin(List<Long> ids) {
		return adminMapper.deleteAdmin(ids);
	}

	@Override
	public List<Map<String, Object>> listRoles() {
		return adminMapper.listRoles();
	}

}
