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
<link rel="stylesheet" href="<%=sourcePath%>css/amazeui.min.css" />
<link rel="stylesheet" href="<%=sourcePath%>css/admin.css">
<link rel="stylesheet" href="<%=sourcePath%>css/app.css">
</head>
<style>
html, body {
	background: #fff;
}

.am-selected-list li {
	padding-left: 18px;
}
</style>
<body data-type="login">
	<div class="am-g myapp-login">
		<div class="myapp-login-logo-block  tpl-login-max">
			<div class="myapp-login-logo-text">
				<img src="<%=sourcePath%>img/admin/loginLogo.jpg">
				<div class="myapp-login-logo-text" style="margin: 0px;">
					<span>富安娜库存管理系统</span>
				</div>
			</div>
			<div class="am-u-sm-10 login-am-center">
				<form class="am-form" action="admin/adminLogin.do" id="doc-vld-msg"
					data-am-validator>
					<fieldset>
							<div class="am-selected am-dropdown am-dropdown-down"
								id="am-selected-c8n6r"
								style="width: 100%; border-radius: 6px 6px 0px 0px; border: 1px solid #e9ecf3; border-bottom: 0px;"
								data-am-dropdown>
								<button type="button"
									class="am-selected-btn am-btn am-dropdown-toggle am-btn-default"
									style="padding-left: 18px; font-size: 15px; background: none; color: #999999;">
									<span class="am-selected-status am-fl" id="select-value">地区代理</span>
									<i class="am-selected-icon am-icon-caret-down"
										style="margin-top: 6px;"></i>
								</button>
								<div class="am-selected-content am-dropdown-content"
									style="min-width: 200px; width: 100%;">
									<div class="am-selected-search"><input class="am-form-field am-input-sm search" style="border-radius:0px;" oninput="searchCompany(this)" search /></div>
									<ul class="am-selected-list" id="companyList" 
										style="color: #696969;height:auto;width:auto;">
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
										<li class="" data-index="0" data-group="0" data-value="a">
											<span class='am-selected-text'>扶沟富安娜</span> <i
											class='am-icon-check'></i>
										</li>
									</ul>
								</div>
							</div>
						<div class="am-form-group">
							<input type="text" class="am-form-field am-validate" name="username"
								id="doc-ipt-email-1" placeholder="请输入用户名" required />
						</div>
						<div class="am-form-group">
							<input type="password" class="am-form-field am-validate" name="password"
								id="doc-ipt-pwd-1" placeholder="请输入密码"
								style="border-radius: 0px; border-bottom: 1px solid #e9ecf3;"
								required />
						</div>
						<div class="am-form-group"
							style="width: 100%; white-space: nowrap; overflow: hidden;">
							<input type="text" class="am-form-field am-validate" name="imageCode" id="doc-ipt-pwd-1"
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
	<script src="<%=sourcePath%>js/jquery.min.js"></script>
	<script src="<%=sourcePath%>js/amazeui.min.js"></script>
	<script src="<%=sourcePath%>js/msg.js"></script>
	<script>
		showmsg('${errorCode}', '${errorMsg}');
		$.post("admin/listCompany.do", {}, function(data) {
			$.each(data.data,function(index,value){
				$(".am-selected-list").append("<li class='' id='selectItem" + value.id + "' data-index='" + index + "' data-group='0' data-value='" + value.id + "'>" +
						                      "<span class='am-selected-text'>" + value.name + "</span> <i class='am-icon-check'></i></li>");
				console.log($("#companyList").get(0).offsetHeight);
				if($(".am-selected-list").height() > 200) {
					console.log($(".am-selected-list").css("overflow"));
				};
			});
		});
		$('#doc-vld-msg')
				.validator(
						{
							keyboardFields: ':input:not(:button, :disabled,.search)',
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
		function searchCompany(obj) {
			alert(obj.value);
		}
	</script>
</body>
</html>