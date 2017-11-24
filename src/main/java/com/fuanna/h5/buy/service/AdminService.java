package com.fuanna.h5.buy.service;

import java.util.List;

import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;

public interface AdminService {

	
	public Admin adminLogin(String username, String password);
	
	public List<Resource> queryResourcesByAdminId(long adminId);
}
