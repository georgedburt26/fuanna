package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
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
		Object[] args = null;
		try {
			args = pjp.getArgs();
			return pjp.proceed(args);
		} catch (Throwable e) {
			logger.error("系统出错", e);
			Object redirectUrl = null;
			String errorMsg = "系统内部错误";
			if (e instanceof FuannaErrorException) {
				RedirectAttributes model = null;
				for (Object arg : args) {
					if (arg instanceof RedirectAttributes)
						model = (RedirectAttributes) arg;
				}
				FuannaErrorException fe = (FuannaErrorException) e;
				errorMsg = fe.getErrorMsg();
				redirectUrl = fe.getRedirect();
				if (redirectUrl != null && model != null) {
					model.addFlashAttribute(FuannaConstraints.ERROR_CODE, ErrorCode.SB.toString());
					model.addFlashAttribute(FuannaConstraints.ERROR_MSG, errorMsg);
					return redirectUrl;
				}
			}else {
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
				logger.error(errorMsg + e.getMessage(), e);
				if(!ajax){
					redirectUrl = "redirect:/500.html";
					return redirectUrl;
				}
			}
			MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
			Class<?> returnType = methodSignature.getReturnType();	
			if (returnType == String.class) {
				JsonUtils.printObject(new RstResult(ErrorCode.SB, errorMsg, null));
				return null;
			}
			else {
				return new RstResult(ErrorCode.SB, errorMsg, null);
			}
		}
	}
	
	public void afterReturn() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if (request.getRequestURL().toString().contains("/adminLogin.do")) {
			request.getSession().removeAttribute("admin_imageCode");	
		}
	}
}
