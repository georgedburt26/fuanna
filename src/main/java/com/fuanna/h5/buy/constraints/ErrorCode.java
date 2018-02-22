package com.fuanna.h5.buy.constraints;

public enum ErrorCode {
	CG("0000"),
	NL("0001"),//未登录
	NP("0002"),//没有权限
	SB("9999");
	private String value;
	ErrorCode(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
