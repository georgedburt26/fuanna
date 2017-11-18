package com.fuanna.h5.buy.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fuanna.h5.buy.enumeration.ErrorCode;
import com.fuanna.h5.buy.enumeration.FuannaConstraints;
import com.fuanna.h5.buy.exception.FuannaErrorException;
import com.fuanna.h5.buy.model.RstResult;
import com.fuanna.h5.buy.util.JsonUtils;

public class FuannaErrorHandler {

	private static final Logger logger = Logger
			.getLogger(FuannaErrorHandler.class);

	private Object handle(ProceedingJoinPoint pjp) {
		Object rtn = null;
		RedirectAttributes model = null;
		String redirectUrl = "";
		Object data = null;
		try {
			Object[] args = pjp.getArgs();
			for (Object arg : args) {
				if (arg instanceof RedirectAttributes) {
					model = (RedirectAttributes) arg;
				}
			}
			rtn = pjp.proceed(args);
		} catch (Throwable e) {
			String errorCode = "9999";
			String errorMsg = "系统内部错误";
			if (e instanceof FuannaErrorException) {
				FuannaErrorException fe = (FuannaErrorException) e;
				errorCode = fe.getErrorCode();
				errorMsg = fe.getErrorMsg();
				rtn = fe.getRedirectUrl();
				data = fe.getData();
				if (StringUtils.isNotBlank(rtn.toString())) {
					model.addFlashAttribute(FuannaConstraints.ERROR_CODE, errorCode);
					model.addFlashAttribute(FuannaConstraints.ERROR_MSG, errorMsg);
					return rtn.toString();
				}
			}else {
				logger.error("系统内部错误" + e.getMessage(), e);
				if (data == null) {
				rtn = "/500.jsp";
				return rtn.toString();
				}
			}
			JsonUtils.printObject(new RstResult(ErrorCode.SB, errorMsg, data));
			return null;
		}
		return rtn == null ? null : rtn.toString();
	}
}
