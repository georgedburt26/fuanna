package com.fuanna.h5.buy.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.User;

import net.sf.json.JSONObject;

public class BaseController {

	protected void error(String errorMsg) throws FuannaErrorException {
		throw new FuannaErrorException(ErrorCode.SB.toString(), errorMsg, null);
	}

	protected void error(String errorMsg, String redirect) throws FuannaErrorException {
		throw new FuannaErrorException(ErrorCode.SB.toString(), errorMsg, redirect);
	}

	protected HttpSession session() {
		return request().getSession();
	}

	protected HttpServletRequest request() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	protected Admin admin() {
		return (Admin) session().getAttribute("admin");
	}

	protected User getUser() {
		return (User) session().getAttribute("user");
	}
}
