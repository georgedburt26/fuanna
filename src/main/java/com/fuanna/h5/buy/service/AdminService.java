package com.fuanna.h5.buy.service;

import java.util.List;
import java.util.Map;

import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;

public interface AdminService {

	
	public Admin adminLogin(String username, String password);
	
	public long addAdmin(Admin admin);
	
	public List<Resource> queryResourcesByAdminId(long adminId);
	
	public int countAdmin(String name, String mobilePhone, String username);
	
	public List<Admin> listAdmin(String name, String mobilePhone, String username, Integer offset, Integer limit);
	
	public int deleteAdmin(List<Long> ids);
	
	public List<Map<String, Object>> listRoles(Integer offset, Integer limit);
	
	public int countRoles();
	
	public int deleteRole(List<Long> ids);
}
