package com.fuanna.h5.buy.service;

import java.util.List;
import java.util.Map;

import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;
import com.fuanna.h5.buy.model.Role;

public interface AdminService {

	
	public Admin adminLogin(String username, String password);
	
	public long addAdmin(Admin admin);
	
	public long updateAdmin(Admin admin);
	
	public Admin queryAdminById(long adminId);
	
	public List<Resource> queryResourcesByAdminId(long adminId);
	
	public List<Resource> queryResources();
	
	public int countAdmin(String name, String mobilePhone, String username);
	
	public List<Admin> listAdmin(String name, String mobilePhone, String username, Integer offset, Integer limit);
	
	public int deleteAdmin(List<Long> ids);
	
	public List<Map<String, Object>> listRoles(Integer offset, Integer limit);
	
	public int countRoles();
	
	public int deleteRole(List<Long> ids);
	
	public long addRole(String name, String description, String[] resources);
	
	public long updateRole(long id, String name, String description, String[] resources);
	
	public Role queryRoleById(long roleId);
	
	public Map<String, Object> getTotal();
	
	public List<Map<String, Object>> listByContracts();
	
	public List<Map<String, Object>> listByPersons();
	
	public List<Map<String, Object>> listByPersonsDetail();
}
