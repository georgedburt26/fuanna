package com.fuanna.h5.buy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.fuanna.h5.buy.model.Resource;

public interface ResourceMapper {

	@Select({ "<script>" + 
			  "select DISTINCT(resourceId) from f_role_resource where 1 = 1 " + 
				"<if test=' roles != null '>" +
				"and roleId in " +
				"<foreach collection='roles' index='index' item='item' open='(' separator=',' close=')'>" +  
	            "#{item} " + 
	            "</foreach> " +
	            "</if>" +
	            "</script>"
			})
	public List<Long> listResourceIdByRoles(@Param("roles")List<Long> roles);
	
	@Select({"select * from f_resource where id = #{0} and parentId = #{}"})
	public Resource queryResourceById(long id, int isTop);
}
