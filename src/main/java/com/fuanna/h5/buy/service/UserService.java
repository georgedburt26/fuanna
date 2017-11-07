package com.fuanna.h5.buy.service;

import com.fuanna.h5.buy.model.User;

public interface UserService
{
  public long addUser(User user);
  
  public User getUserById(long id);
}
