package com.fuanna.h5.buy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fuanna.h5.buy.mapper.UserMapper;
import com.fuanna.h5.buy.model.User;
import com.fuanna.h5.buy.service.UserService;

@Component
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper userMapper;
	

	public long addUser(User user) {
		return userMapper.addUser(user);
	}

	public User getUserById(long id) {
		return null;
	}
}
