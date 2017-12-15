package com.fuanna.h5.buy.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.AdminLoginLog;


public interface AdminMapper {

	@Select({ "select * from f_admin where username = #{0} and password = #{1}"})
	public Admin adminLogin(String username, String password);
	
	@Insert({ "insert into f_admin_login_log(adminId, username, name, mobilePhone, email, ip, loginTime) values(#{adminId},#{username},#{name},#{mobilePhone},#{email},#{ip},#{loginTime})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addLoginLog(AdminLoginLog adminLoginLog);
	
	@Insert({ "insert into f_admin(username, password, name, mobilePhone, email, headImg, createTime, role) values(#{username},#{password},#{name},#{mobilePhone},#{email},#{headImg},#{createTime},#{role})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addAdmin(Admin admin);
	
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
	
	
	@Select({ "select * from f_role" })
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listRoles();
}
