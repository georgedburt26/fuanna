<%@page import="com.fuanna.h5.buy.base.BaseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String sourcePath = "";
	String source = BaseConfig.getBaseConfig("source");
	if ("1".equals(source)) {
		sourcePath = basePath;
	}
	if ("2".equals(source)) {
		sourcePath = BaseConfig.getUploadConfig("qiniu_host");
	}
	if ("3".equals(source)) {
		sourcePath = BaseConfig.getUploadConfig("tencent_host");
	}
%>
