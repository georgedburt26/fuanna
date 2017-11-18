package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.enumeration.ErrorCode;
import com.fuanna.h5.buy.enumeration.FuannaConstraints;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.model.User;
import com.fuanna.h5.buy.util.JsonUtils;

public class BaseController {

	protected void send(String errorMsg, Object data) throws FuannaErrorException {
		throw new FuannaErrorException(ErrorCode.SB.toString(), errorMsg, data);
	}
	
	protected void sendToUrl(String errorMsg, String redirect) throws FuannaErrorException {
//		String action = request().getRequestURI().replaceAll(request().getContextPath(), "");
		throw new FuannaErrorException(ErrorCode.SB.toString(), errorMsg, redirect);
	}
	
	protected void send(ErrorCode errorCode, String errorMsg, Object data) {
		RstResult rstResult = new RstResult(ErrorCode.CG, errorMsg, data);
		JsonUtils.printObject(rstResult);
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
