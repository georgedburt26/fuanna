package com.fuanna.h5.buy.mapper;

import com.fuanna.h5.buy.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	@Insert({ "insert into f_user(userName, mobilePhone, email) values(#{userName},#{mobilePhone},#{email})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public long addUser(User user);

	@Select({ "select * from f_user where id = #{id}" })
	public User getUserById(long id);
}
