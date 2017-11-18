package com.fuanna.h5.buy.exception;

public class FuannaErrorException extends Exception{
	
	private String errorCode;
	private String errorMsg;
	private Object data;
	
	public FuannaErrorException() {
		super();
	}
	
	public FuannaErrorException(Exception e) {
		super(e);
	}
	
	public FuannaErrorException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
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
}
