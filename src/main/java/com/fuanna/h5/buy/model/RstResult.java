package com.fuanna.h5.buy.model;

import java.io.Serializable;

import com.fuanna.h5.buy.constraints.ErrorCode;
import com.fuanna.h5.buy.exception.FuannaErrorException;

public class RstResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;
	
	private String errorMsg;
	
	private Object data;
	
	public RstResult(ErrorCode errorCode, String errorMsg){
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public RstResult(ErrorCode errorCode, String errorMsg, Object data){
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.data = data;
	}
	
	public RstResult() {
		
	}

	public String getErrorCode() {
		return errorCode.toString();
	}

	public void setErrorCode(ErrorCode errorCode) {
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
