package com.fuanna.h5.buy.constraints;

public enum ErrorCode {
	CG("0000"),
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
