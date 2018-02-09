<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title></title>
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="<%=sourcePath%>img/admin/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="<%=sourcePath%>css/amazeui.min.css" />
<link rel="stylesheet" href="<%=sourcePath%>css/amazeui.datatables.min.css" />
<link rel="stylesheet" href="<%=sourcePath%>css/admin.css">
<link rel="stylesheet" href="<%=sourcePath%>css/app.css">
<link rel="stylesheet" href="<%=sourcePath%>css/ui-dialog.css">
<link rel="stylesheet" href="<%=sourcePath%>css/jquery.mloading.css">
<link rel="stylesheet" href="<%=sourcePath%>css/amazeui.tree.min.css">
<script src="<%=sourcePath%>js/renderFile.js"></script>
<script src="<%=sourcePath%>js/echarts.min.js"></script>
</head>
<style>
.am-icon-list:before {
	line-height: 75px;
}

.am-icon-sign-out:before {
	line-height: 75px;
}
</style>
<body data-type="index">
	<jsp:include page="top.jsp" />
	<div class="tpl-page-container tpl-page-header-fixed">
		<jsp:include page="left.jsp" />
		<div class="tpl-content-wrapper"></div>
	</div>
	<script src="<%=sourcePath%>js/jquery.min.js"></script>
	<script src="<%=sourcePath%>js/amazeui.datatables.min.js"></script>
	<script src="<%=sourcePath%>js/amazeui.min.js"></script>
	<script src="<%=sourcePath%>js/app.js"></script>
	<script src="<%=sourcePath%>js/msg.js"></script>
	<script src="<%=sourcePath%>js/dialog-min.js"></script>
	<script src="<%=sourcePath%>js/jquery.mloading.js"></script>
	<script src="<%=sourcePath%>js/amazeui.tree.min.js"></script>
</body>
<script>
showmsg('${errorCode}', '${errorMsg}');
</script>
</html>