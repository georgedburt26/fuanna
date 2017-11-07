package com.fuanna.h5.buy.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface JdbcTestMapper {

	@Select({ "select id from f_test_jdbc where id = 1" })
	public int test();
}
