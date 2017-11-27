package com.fuanna.h5.buy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Admin;

public interface AdminMapper {

	@Select({ "select * from f_admin where username = #{0} and password = #{1}"})
	public Admin adminLogin(String username, String password);
	
	@Select({ "select * from f_admin where id = #{0}"})
	public Admin queryAdminById(long id);
	
	@Select({ "select * from f_admin where id = #{0}"})
	public List<Admin> listAdmin();
	
	@Select({ "<script>" + 
		      "select count(id) from f_admin where name = #{0}" +
		      "</script>"})
	public int countAdmin(String name, String mobilePhone, String email);
}
