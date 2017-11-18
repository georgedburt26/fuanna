package com.fuanna.h5.buy.exception;

public class FuannaErrorException extends Exception{
	
	private String errorCode;
	private String errorMsg;
	private Object data;
	private String redirect;
	private String action;
	
	public FuannaErrorException() {
		super();
	}
	
	public FuannaErrorException(Exception e) {
		super(e);
	}
	
	public FuannaErrorException(String errorCode, String errorMsg, String redirect, String action) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.redirect = redirect;
		this.action = action;
	}
	
	public FuannaErrorException(String errorCode, String errorMsg, String redirect) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.redirect = redirect;
	}
	
	public FuannaErrorException(String errorCode, String errorMsg, Object data) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}	
	
}
