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
import org.apache.ibatis.annotations.Update;

import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.AdminLoginLog;
import com.fuanna.h5.buy.model.Role;
import com.fuanna.h5.buy.model.RoleResource;

public interface AdminMapper {

	@Select({"select * from f_company order by convert(name USING gbk) asc"})
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listCompany();
	
	@Select({"select * from f_company where id = #{0}"})
	@ResultType(LinkedHashMap.class)
	public Map<String, Object> queryCompanyById(long id);

	@Select({ "select * from f_admin where username = #{0} and password = #{1} and companyId = #{2}" })
	public Admin adminLogin(String username, String password, long company);

	@Insert({
			"insert into f_admin_login_log(adminId, username, name, mobilePhone, email, ip, loginTime, companyId, companyName, location, terminal) values(#{adminId},#{username},#{name},#{mobilePhone},#{email},#{ip},#{loginTime},#{companyId},#{companyName},#{location},#{terminal})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addLoginLog(AdminLoginLog adminLoginLog);

	@Insert({
			"insert into f_admin(username, password, name, mobilePhone, email, headImg, createTime, role, companyId) values(#{username},#{password},#{name},#{mobilePhone},#{email},#{headImg},#{createTime},#{role},#{companyId})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addAdmin(Admin admin);

	@Update("update f_admin set username=#{username},name=#{name},mobilePhone=#{mobilePhone},email=#{email},headImg=#{headImg},role=#{role} where id=#{id}")
	public int updateAdmin(Admin admin);

	@Select({
			"select admin.*, GROUP_CONCAT(role.`name`) as roleName from f_admin as admin left join f_role as role on instr(admin.role, role.id) where admin.id = #{0}" })
	public Admin queryAdminById(long id);

	@Select({ "<script>"
			+ "select admin.*, GROUP_CONCAT(role.`name`) as roleName from f_admin as admin left join f_role as role on instr(admin.role, role.id) where 1 = 1 "
			+ "<if test='name != null'>" + " and admin.name like CONCAT('%',#{name},'%') " + "</if>"
			+ "<if test='mobilePhone != null'>" + " and mobilePhone = #{mobilePhone} " + "</if>"
			+ "<if test='username != null'>" + " and username like CONCAT('%',#{username},'%') " + "</if>"
			+ "<if test='companyId != null'>" + " and admin.companyId = #{companyId} " + "</if>"
			+ " group by admin.id " + " order by id desc " + "<if test='offset != null and limit != null'>"
			+ " limit #{offset}, #{limit} " + "</if>" + "</script>" })
	public List<Admin> listAdmin(@Param("name") String name, @Param("mobilePhone") String mobilePhone,
			@Param("username") String username, @Param("companyId") Long companyId, @Param("offset") Integer offset, @Param("limit") Integer limit);

	@Select({ "<script>" + "select count(id) from f_admin as admin where 1 = 1 " + "<if test='name != null'>"
			+ " and name like CONCAT('%',#{name},'%') " + "</if>" + "<if test='mobilePhone != null'>"
			+ " and mobilePhone = #{mobilePhone} " + "</if>" + "<if test='username != null'>" 
			+ " and username like CONCAT('%',#{username},'%') " + "</if>" 
			+ "<if test='companyId != null'>" + " and admin.companyId = #{companyId} " + "</if>"
			+ "</script>" })
	public int countAdmin(@Param("name") String name, @Param("mobilePhone") String mobilePhone,
			@Param("username") String username, @Param("companyId") Long companyId);
	
	@Select({ "<script>"
			+ "select * from f_admin_login_log where 1 = 1 "
			+ "<if test='name != null'>" + " and name like CONCAT('%',#{name},'%') " + "</if>"
			+ "<if test='mobilePhone != null'>" + " and mobilePhone = #{mobilePhone} " + "</if>"
			+ "<if test='username != null'>" + " and username like CONCAT('%',#{username},'%') " + "</if>"
			+ "<if test='companyId != null'>" + " and companyId = #{companyId} " + "</if>"
			+ " order by loginTime desc " + "<if test='offset != null and limit != null'>"
			+ " limit #{offset}, #{limit} " + "</if>" + "</script>" })
	public List<AdminLoginLog> listAdminLoginLog(@Param("name") String name, @Param("mobilePhone") String mobilePhone,
			@Param("username") String username, @Param("companyId") Long companyId, @Param("offset") Integer offset, @Param("limit") Integer limit);
	
	@Select({ "<script>" + "select count(id) from f_admin_login_log where 1 = 1 " + "<if test='name != null'>"
			+ " and name like CONCAT('%',#{name},'%') " + "</if>" + "<if test='mobilePhone != null'>"
			+ " and mobilePhone = #{mobilePhone} " + "</if>" + "<if test='username != null'>" 
			+ " and username like CONCAT('%',#{username},'%') " + "</if>" 
			+ "<if test='companyId != null'>" + " and companyId = #{companyId} " + "</if>"
			+ "</script>" })
	public int countAdminLoginLog(@Param("name") String name, @Param("mobilePhone") String mobilePhone,
			@Param("username") String username, @Param("companyId") Long companyId);

	@Delete("<script>" + " delete from f_admin where id in "
			+ "<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>" + "#{item} "
			+ "</foreach> " + "</script>")
	public int deleteAdmin(@Param("ids") List<Long> ids);

	@Select({ "<script>" + "select * from f_role where 1 = 1 " + "<if test='offset != null and limit != null'>"
			+ "<if test='companyId != null'>" + " and companyId = #{companyId} " + "</if>"
			+ " limit #{offset}, #{limit} " + "</if>" + "</script>" })
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listRoles(@Param("companyId") Long companyId, @Param("offset") Integer offset, @Param("limit") Integer limit);

	@Select({ "<script>" +
		      "select count(id) from f_role where 1 = 1 " +
			  "<if test='companyId != null'>" + " and companyId = #{companyId} " + "</if>" +
			  "</script>"})
	public int countRoles(@Param("companyId") Long companyId);

	@Delete("<script>" + " delete from f_role where id in "
			+ "<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>" + "#{item} "
			+ "</foreach> " + "</script>")
	public int deleteRole(@Param("ids") List<Long> ids);

	@Delete("<script>" + " delete from f_role_resource where roleId in "
			+ "<foreach collection='roleIds' index='index' item='item' open='(' separator=',' close=')'>" + "#{item} "
			+ "</foreach> " + "</script>")
	public int deleteRoleResource(@Param("roleIds") List<Long> roleIds);

	@Insert({ "insert into f_role(name, description, createTime, companyId) values(#{name},#{description},#{createTime},#{companyId})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addRole(Role role);

	@Update("update f_role set name=#{name},description=#{description} where id=#{id}")
	public int updateRole(Role role);

	@Insert({ "insert into f_role_resource(roleId, resourceId) values(#{roleId},#{resourceId})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addRoleResource(RoleResource roleResource);

	@Select({
			"select role.name, role.description, role.createTime, GROUP_CONCAT(resource.resourceId) as resources from f_role as role left join f_role_resource as resource on resource.roleId = role.id where role.id = #{0} group by role.id" })
	public Role queryRoleById(long roleId);
	
	@Select({"select content, DATE_FORMAT(publishTime,'%Y年%m月%d日') as publishTime from f_notice where companyId=#{0}" })
	public Map<String, Object> queryNoticeByCompanyId(Long companyId);
	
	@Update({"INSERT INTO f_notice (content, publishTime, companyId) VALUES (#{0}, now(), #{1}) ON DUPLICATE KEY UPDATE content = #{0}, publishTime=now()"})
	public long publishNotice(String content, Long companyId);

	@Select({
			"select count(id) as count, sum(amount) as totalAmount, sum(realAmount) as totalRealAmount,sum(hascashAmount) as totalHascashAmount, sum(uncashAmount) as totalUncashAmount from gs_contract " })
	@ResultType(LinkedHashMap.class)
	public Map<String, Object> getTotal();

	@Select({
			"select CONCAT(p.id,'-',if(@lastId = p.id, @index := @index + 1, @index := 1)) as num, @lastId := p.id,p.id,p.realName,DATE_FORMAT(p.createDate,'%Y/%m/%d') as createDate,p.idNo,p.cellPhone,p.nativeAddress,c.amount,c.realAmount,c.hascashAmount,c.uncashAmount,p.remark,c.midName "
					+ "from gs_contract as c left join gs_person as p on c.uid = p.id, (select @lastId := @lastId, @index := 1) a order by c.uid,c.id" })
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listByContracts();

	@Select({
			"select p.id,p.realName,DATE_FORMAT(p.createDate,'%Y/%m/%d') as createDate,p.idNo,p.cellPhone,p.nativeAddress,count(c.id) as count,sum(c.amount) as amount,sum(c.realAmount) as realAmount,sum(c.hascashAmount) as hascashAmount,sum(c.uncashAmount) as uncashAmount from gs_person as p left join gs_contract as c on c.uid = p.id group by p.id " })
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listByPersons();

	@Select({
			"select p.id,p.realName,DATE_FORMAT(p.createDate,'%Y/%m/%d') as createDate,p.idNo,p.cellPhone,p.nativeAddress,GROUP_CONCAT(CONCAT(p.id,'-',if(@lastId = p.id, @index := @index + 1, @index := 1))) as contracts,@lastId := p.id,sum(c.uncashAmount) as uncashAmount "
					+ "from gs_person as p left join gs_contract as c on c.uid = p.id,(select @lastId := @lastId, @index := 1) a group by p.id order by p.id,c.id" })
	@ResultType(LinkedHashMap.class)
	public List<Map<String, Object>> listByPersonsDetail();
}
