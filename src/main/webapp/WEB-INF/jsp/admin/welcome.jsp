<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
<style>
.widget {
	width: 100%;
	min-height: 148px;
	margin-bottom: 20px;
	border-radius: 0;
	padding: 10px 20px 13px;
	background-color: #fff;
	border-radius: 4px;
	color: #838FA1;
}

.widget-body {
	padding: 13px 15px;
}

.tpl-page-state-title {
	font-size: 40px;
	font-weight: bold;
	text-align: center !important;
}

.tpl-error-content {
	margin-top: 20px;
	margin-bottom: 20px;
	font-size: 16px;
	text-align: center;
	color: #96a2b4;
	padding: 10px 0;
}

.tpl-error-title-info {
	line-height: 30px;
	font-size: 21px;
	margin-top: 20px;
	text-align: center;
	color: #dce2ec;
}

#tpl-error-btn {
	background: #03a9f3;
	border: 1px solid #03a9f3;
	border-radius: 30px;
	padding: 6px 20px 8px;
	font-size: 14px;
	color: #fff;
	cursor: pointer;
}
</style>
</head>
<body>
	<!--<div class="main">
		<div class="error01" style="text-align: center">
			<img src="img/admin/index11_35.jpg" width="448" height="125" />
		</div>
	</div>  -->
	<div class="tpl-portlet-components">
		<div class="row-content am-cf">
			<div class="widget am-cf"> 
				<div class="widget-body">
					<div class="tpl-page-state">
						<div class="tpl-page-state-title am-text-center tpl-error-title">欢迎使用富安娜库存管理系统</div>
						<div class="tpl-page-state-content tpl-error-content">
							<p>天气：<span id="weather">无法获取</span></p>
							<p>温度：<span id="temperature">无法获取</span></p>
							<p>PM2.5：<span id="pm25">无法获取</span></p>
							<p>风级：<span id="wind">无法获取</span></p>
							<p>位置：<span id="location">无法定位</span></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
$.post("admin/getLocationInfo.do", {}, function(data) {
	$("#weather").text(data.data.weatherInfo);
	$("#temperature").text(data.data.temperature);
	$("#pm25").text(data.data.pm25);
	$("#wind").text(data.data.wind);
	$("#location").text(data.data.location);
	});
</script>
</html>