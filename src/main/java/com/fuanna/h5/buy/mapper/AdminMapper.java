package com.fuanna.h5.buy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Admin;

public interface AdminMapper {

	@Select({ "select * from f_admin where username = #{0} and password = #{1}"})
	public Admin adminLogin(String username, String password);
	
	@Select({ "select * from f_admin where id = #{0}"})
	public Admin queryAdminById(long id);
	
	@Select({"<script>" +  
		     "select admin.*, GROUP_CONCAT(role.`name`) as roleName from f_admin as admin left join f_role as role on instr(admin.role, role.id) where 1 = 1 " + 
			  "<if test='name != null'>" +
			  " and name = '%'#{name}'%' " +
		      "</if>" +
			  "<if test='mobilePhone != null'>" +
			  " and mobilePhone = #{mobilePhone} " +
		      "</if>" +
			  "<if test='email != null'>" +
			  " and email = #{email} " +
		      "</if>" +
		      " group by admin.id " +
			  " order by id desc " +
			  "<if test='offset != null and limit != null'>" +
			  " limit #{offset}, #{limit} " +
		      "</if>" +
		     "</script>"})
	public List<Admin> listAdmin(@Param("name")String name, @Param("mobilePhone")String mobilePhone, @Param("email")String email, @Param("offset")Integer offset, @Param("limit")Integer limit);
	
	@Select({ "<script>" + 
		      "select count(id) from f_admin where 1 = 1 " +
			  "<if test='name != null'>" +
			  " and name = '%'#{name}'%' " +
		      "</if>" +
			  "<if test='mobilePhone != null'>" +
			  " and mobilePhone = #{mobilePhone} " +
		      "</if>" +
			  "<if test='email != null'>" +
			  " and email = #{email} " +
		      "</if>" +
		      "</script>"})
	public int countAdmin(@Param("name")String name, @Param("mobilePhone")String mobilePhone, @Param("email")String email);
	
	@Delete("<script>" +
	        " delete from f_admin where id in " +
			"<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>" +  
            "#{item} " + 
            "</foreach> " +
	        "</script>")
	public int deleteAdmin(@Param("ids")List<Long> ids);
}
