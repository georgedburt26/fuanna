package com.fuanna.h5.buy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Resource;

public interface ResourceMapper {

//	@Select({ "<script>" + 
//			  "select DISTINCT(resourceId) from f_role_resource where 1 = 1 " + 
//				"<if test=' roles != null '>" +
//				"and roleId in " +
//				"<foreach collection='roles' index='index' item='item' open='(' separator=',' close=')'>" +  
//	            "#{item} " + 
//	            "</foreach> " +
//	            "</if>" +
//	            "</script>"
//			})
//	public List<Long> listResourceIdByAdminId(long adminId);
	
	@Select({"<script>" + 
		     "select * from f_resource as r where id in (select DISTINCT(resourceId) from f_role_resource where 1 = 1 and roleId in (select role from f_admin where id = #{adminId})) " +
			 "<if test='isTop'>" +
			 " and r.parentId is null " +
	         "</if>" +
			 "<if test='type != null'>" +
			 " and type = #{type} " +
	         "</if>" + 
			 " order by r.index " + 
			 "</script>"})
	public List<Resource> queryResourceByAdminId(@Param("adminId")long adminId, @Param("isTop")boolean isTop, @Param("type")Integer type);

	@Select({ "<script>" +
			  "select * from f_resource as r where 1 = 1 " + 
			  "<if test='#{0} != null'>" +
			  " and parentId = #{0} " +
		      "</if>" +
			  " order by r.index " + 
			  "</script>"})
	public List<Resource> queryResourceByParentId(Long parentId);
	
	@Select({ "select * from f_resource where parentId is null order by type" })
	public List<Resource> queryResourceParent();
	
	@Select({ "select * from f_resource" })
	public List<Resource> queryAllResources();
}
