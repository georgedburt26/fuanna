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
import com.fuanna.h5.buy.model.Role;
import com.fuanna.h5.buy.model.RoleResource;


public interface AdminMapper {

	@Select({ "select * from f_admin where username = #{0} and password = #{1}"})
	public Admin adminLogin(String username, String password);
	
	@Insert({ "insert into f_admin_login_log(adminId, username, name, mobilePhone, email, ip, loginTime) values(#{adminId},#{username},#{name},#{mobilePhone},#{email},#{ip},#{loginTime})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addLoginLog(AdminLoginLog adminLoginLog);
	
	@Insert({ "insert into f_admin(username, password, name, mobilePhone, email, headImg, createTime, role) values(#{username},#{password},#{name},#{mobilePhone},#{email},#{headImg},#{createTime},#{role})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addAdmin(Admin admin);
	
	@Select({ "select * from f_admin where id = #{0}"})
	public Admin queryAdminById(long id);
	
	@Select({"<script>" +  
		     "select admin.*, GROUP_CONCAT(role.`name`) as roleName from f_admin as admin left join f_role as role on instr(admin.role, role.id) where 1 = 1 " + 
			  "<if test='name != null'>" +
			  " and admin.name like CONCAT('%',#{name},'%') " +
		      "</if>" +
			  "<if test='mobilePhone != null'>" +
			  " and mobilePhone = #{mobilePhone} " +
		      "</if>" +
			  "<if test='username != null'>" +
			  " and username like CONCAT('%',#{username},'%') " +
		      "</if>" +
		      " group by admin.id " +
			  " order by id desc " +
			  "<if test='offset != null and limit != null'>" +
			  " limit #{offset}, #{limit} " +
		      "</if>" +
		     "</script>"})
	public List<Admin> listAdmin(@Param("name")String name, @Param("mobilePhone")String mobilePhone, @Param("username")String username, @Param("offset")Integer offset, @Param("limit")Integer limit);
	
	@Select({ "<script>" + 
		      "select count(id) from f_admin where 1 = 1 " +
			  "<if test='name != null'>" +
			  " and name like CONCAT('%',#{name},'%') " +
		      "</if>" +
			  "<if test='mobilePhone != null'>" +
			  " and mobilePhone = #{mobilePhone} " +
		      "</if>" +
			  "<if test='username != null'>" +
			  " and username like CONCAT('%',#{username},'%') " +
		      "</if>" +
		      "</script>"})
	public int countAdmin(@Param("name")String name, @Param("mobilePhone")String mobilePhone, @Param("username")String username);
	
	@Delete("<script>" +
	        " delete from f_admin where id in " +
			"<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>" +  
            "#{item} " + 
            "</foreach> " +
	        "</script>")
	public int deleteAdmin(@Param("ids")List<Long> ids);
	
	@Select({"<script>" +  
		     "select * from f_role where 1 = 1 " + 
			  "<if test='offset != null and limit != null'>" +
			  " limit #{offset}, #{limit} " +
		      "</if>" +
		     "</script>"})
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listRoles(@Param("offset")Integer offset, @Param("limit")Integer limit);
	
	@Select({ "select count(id) from f_role"})
	public int countRoles();
	
	@Delete("<script>" +
	        " delete from f_role where id in " +
			"<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>" +  
            "#{item} " + 
            "</foreach> " +
	        "</script>")
	public int deleteRole(@Param("ids")List<Long> ids);
	
	@Delete("<script>" +
	        " delete from f_role_resource where roleId in " +
			"<foreach collection='roleIds' index='index' item='item' open='(' separator=',' close=')'>" +  
            "#{item} " + 
            "</foreach> " +
	        "</script>")
	public int deleteRoleResource(@Param("roleIds")List<Long> roleIds); 
	
	@Insert({ "insert into f_role(name, description, createTime) values(#{name},#{description},#{createTime})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addRole(Role role);
	
	@Insert({ "insert into f_role_resource(roleId, resourceId) values(#{roleId},#{resourceId})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int addRoleResource(RoleResource roleResource);
}
