package com.fuanna.h5.buy.base;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.enumeration.ErrorCode;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.util.JsonUtils;

public class FuannaErrorHandler {

	private static final Logger logger = Logger
			.getLogger(FuannaErrorHandler.class);

	private Object handle(ProceedingJoinPoint pjp) {
		Object rtn = null;
		Object data = null;
		try {
			Object[] args = pjp.getArgs();
			rtn = pjp.proceed(args);
		} catch (Throwable e) {
			String errorMsg = "系统内部错误";
			if (e instanceof FuannaErrorException) {
				FuannaErrorException fe = (FuannaErrorException) e;
				errorMsg = fe.getErrorMsg();
				data = fe.getData();
			}else {
				logger.error("系统内部错误" + e.getMessage(), e);
				if (data == null) {
				rtn = "redirect:/500.jsp";
				return rtn;
				}
			}
			JsonUtils.printObject(new RstResult(ErrorCode.SB, errorMsg, data));
			return null;
		}
		return rtn == null ? null : rtn;
	}
}
