<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/lib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script src="js/jquery.min.js"></script>
</head>
<body>
<form action="tencentUpload.do" method="post" id="iconForm" enctype="multipart/form-data">
<input type="file" id="fileToUpload" name="file"/>
<input type="submit" value="上传">
<input type="button" id="ajax" value="ajax">
</form>
</body>
</html>