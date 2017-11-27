package com.fuanna.h5.buy.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuanna.h5.buy.mapper.AdminMapper;
import com.fuanna.h5.buy.mapper.ResourceMapper;
import com.fuanna.h5.buy.model.Admin;
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
	public Admin adminLogin(String username, String password) {
		return adminMapper.adminLogin(username, MD5.encrypt(password));
	}
	
	@Override
	public List<Resource> queryResourcesByAdminId(long adminId) {
		List<Resource> topResources = resourceMapper.queryResourceByAdminId(adminId, true, 1);
		for (Resource topResource : topResources) {
			findResources(topResource);
		}
		return topResources;
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
	public int countAdmin(String name, String mobilePhone, String email) {
		return adminMapper.countAdmin(name, mobilePhone, email);
	}

	@Override
	public List<Admin> listAdmin(String name, String mobilePhone, String email, Integer offset, Integer limit) {
		return adminMapper.listAdmin(name, mobilePhone, email, offset, limit);
	}

}
