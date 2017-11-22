package com.fuanna.h5.buy.mapper;

import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Admin;

public interface AdminMapper {

	@Select({ "select * from f_admin where username = #{0} and password = #{1}"})
	public Admin adminLogin(String username, String password);
	
	@Select({ "select * from f_admin where id = #{0}"})
	public Admin queryAdminById(long id);
}
