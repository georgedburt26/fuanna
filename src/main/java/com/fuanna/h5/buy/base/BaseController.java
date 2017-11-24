package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.model.User;
import com.fuanna.h5.buy.util.JsonUtils;

public class BaseController {

	protected void error(String errorMsg) throws FuannaErrorException {
		throw new FuannaErrorException(ErrorCode.SB.toString(), errorMsg, null);
	}
	
	protected void error(String errorMsg, String redirect) throws FuannaErrorException {
		throw new FuannaErrorException(ErrorCode.SB.toString(), errorMsg, redirect);
	}
	
	protected HttpSession session() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	protected HttpServletRequest request() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	protected Admin getAdmin() {
		return (Admin)session().getAttribute("admin");
	}
	
	protected User getUser() {
		return (User)session().getAttribute("user");
	}
}
