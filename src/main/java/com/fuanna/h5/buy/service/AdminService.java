package com.fuanna.h5.buy.service;

import java.util.List;
import java.util.Map;

import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;

public interface AdminService {

	
	public Admin adminLogin(String username, String password);
	
	public long addAdmin(Admin admin);
	
	public List<Resource> queryResourcesByAdminId(long adminId);
	
	public int countAdmin(String name, String mobilePhone, String email);
	
	public List<Admin> listAdmin(String name, String mobilePhone, String email, Integer offset, Integer limit);
	
	public int deleteAdmin(List<Long> ids);
	
	public List<Map<String, Object>> listRoles();
}
