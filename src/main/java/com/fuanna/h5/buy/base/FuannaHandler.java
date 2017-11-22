package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.constraints.FuannaConstraints;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.util.JsonUtils;

public class FuannaHandler{

	private static final Logger logger = Logger
			.getLogger(FuannaHandler.class);
	
	private Object round(ProceedingJoinPoint pjp) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		Object rtn = null;
		Object data = null;
		RedirectAttributes model = null;
		try {
			Object[] args = pjp.getArgs();
			for (Object arg : args) {
				if (arg instanceof RedirectAttributes)
					model = (RedirectAttributes) arg;
			}
			rtn = pjp.proceed(args);
		} catch (Throwable e) {
			String errorMsg = "系统内部错误";
			if (e instanceof FuannaErrorException) {
				FuannaErrorException fe = (FuannaErrorException) e;
				errorMsg = fe.getErrorMsg();
				data = fe.getData();
				rtn = fe.getRedirect();
				if (rtn != null && model != null) {
					model.addFlashAttribute(FuannaConstraints.ERROR_CODE, ErrorCode.SB.toString());
					model.addFlashAttribute(FuannaConstraints.ERROR_MSG, errorMsg);
					return rtn;
				}
			}else {
				data = "系统内部错误" + e.getMessage();
				logger.error(data, e);
				if(!ajax){
					rtn = "redirect:/500.jsp";
					return rtn;
				}
			}
			JsonUtils.printObject(new RstResult(ErrorCode.SB, errorMsg, data));
			return null;
		}
		return rtn == null ? null : rtn;
	}
	
	public void afterReturn() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if (request.getRequestURL().toString().contains("/adminLogin.do")) {
			request.getSession().removeAttribute("admin_imageCode");	
		}
	}
}
