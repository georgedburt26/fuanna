package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.fuanna.h5.buy.model.User;

public class BaseController {
	
	protected User getUser(HttpServletRequest request) {
		return (User)request.getSession().getAttribute("user");
	}
	
	protected HttpSession session(HttpServletRequest request) {
		return request.getSession();
	}
}
