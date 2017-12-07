<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>扶沟富安娜库存管理后台</title>
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="assets/i/favicon.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/i/app-icon72x72@2x.png">
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="stylesheet" href="<%=basePath%>css/amazeui.min.css" />
<link rel="stylesheet" href="<%=basePath%>css/admin.css">
<link rel="stylesheet" href="<%=basePath%>css/app.css">
</head>

<body data-type="login">
	<div class="am-g myapp-login">
		<div class="myapp-login-logo-block  tpl-login-max">
			<div class="myapp-login-logo-text">
				<img src="img/admin/loginLogo.jpg">
				<div class="myapp-login-logo-text" style="margin: 0px;">
					<span>富安娜库存管理系统</span>
				</div>
			</div>
			<div class="am-u-sm-10 login-am-center">
				<form class="am-form" action="admin/adminLogin.do" id="doc-vld-msg">
					<fieldset>
						<div class="am-form-group">
							<input type="text" class="am-form-field" name="username"
								id="doc-ipt-email-1" placeholder="请输入用户名" required />
						</div>
						<div class="am-form-group">
							<input type="password" class="" name="password"
								id="doc-ipt-pwd-1" placeholder="请输入密码"
								style="border-radius: 0px;" required />
						</div>
						<div class="am-form-group"
							style="width: 100%; white-space: nowrap; overflow: hidden;">
							<input type="text" class="" name="imageCode" id="doc-ipt-pwd-1"
								placeholder="验证码"
								style="display: inline-block; border-radius: 0px 0px 6px 6px; width: 59%;"
								pattern="^(\w){4}$" data-validation-message="请输入四位字符" required />
							<img onclick="this.src='imageCode.do?page=admin&'+Math.random()"
								src="imageCode.do?page=admin"
								style="cursor: pointer; display: inline-block; border: 1px solid #e9ecf3; border-radius: 0px 0px 6px 6px; width: 40%; height: 44px;" />

						</div>
						<p>
							<button type="submit" class="am-btn am-btn-default">登录</button>
						</p>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<script src="http://www.jq22.com/jquery/jquery-2.1.1.js"></script>
	<script src="<%=basePath%>js/amazeui.min.js"></script>
	<script src="<%=basePath%>js/msg.js"></script>
	<script>
		showmsg('${errorCode}', '${errorMsg}');
		$('#doc-vld-msg')
				.validator(
						{
							onValid : function(validity) {
								$(validity.field).closest('.am-form-group')
										.find('.am-alert').hide();
							},
							onInValid : function(validity) {
								var $field = $(validity.field);
								var $group = $field.closest('.am-form-group');
								var $alert = $group.find('.am-alert');
								// 使用自定义的提示信息 或 插件内置的提示信息
								var msg = $field.data('validationMessage')
										|| this.getValidationMessage(validity);
								if (!$alert.length) {
									$alert = $(
											'<div class="am-alert am-alert-danger"></div>')
											.hide().appendTo($group);
								}
								$alert.html(msg).show();
							}
						});
	</script>
</body>
</html>