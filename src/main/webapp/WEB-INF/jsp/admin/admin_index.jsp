<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-sm-12 am-u-md-9">
				<form id="doc-vld-msg" class="am-form am-form-horizontal">
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">用户名</label>
						<div class="am-u-sm-9">
							<input type="text" id="username" placeholder="输入用户名"
								minLength="3" required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">密码</label>
						<div class="am-u-sm-9">
							<input type="password" id="password"
								data-validation-message="请输入8到16位的数字字母组合"
								pattern="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"
								placeholder="请输入密码" required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">确认密码</label>
						<div class="am-u-sm-9">
							<input type="password" id="confirmpassword"
								data-equal-to="#password" data-validation-message="请与上面输入的密码一致"
								pattern="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"
								placeholder="请与上面输入的值一致" required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">姓名</label>
						<div class="am-u-sm-9">
							<input type="text" id="name" placeholder="姓名" minLength="2"
								required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">手机号</label>
						<div class="am-u-sm-9">
							<input type="text" id="mobilePhone" placeholder="手机号"
								data-validation-message="请输入正确的手机号"
								pattern="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$"
								required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">电子邮件</label>
						<div class="am-u-sm-9">
							<input type="email" id="email" placeholder="输入你的电子邮件" required />
						</div>
					</div>

					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">头像</label>
						<div class="am-u-sm-9">
							<input type="tel" id="headImg" placeholder="输入你的电话号码 ">
						</div>
					</div>

					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">角色</label>
						<div class="am-u-sm-9">
							<div class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
								<button class="am-btn am-btn-primary am-dropdown-toggle" 
									data-am-dropdown data-am-dropdown-toggle>
									下拉列表 <span class="am-icon-caret-down"></span>
								</button>
								<ul class="am-dropdown-content">
									<li class="am-dropdown-header">标题</li>
									<li><a href="#">快乐的方式不只一种</a></li>
									<li class="am-active"><a href="#">最荣幸是</a></li>
									<li><a href="#">谁都是造物者的光荣</a></li>
									<li class="am-disabled"><a href="#">就站在光明的角落</a></li>
									<li class="am-divider"></li>
									<li><a href="#">天空海阔 要做最坚强的泡沫</a></li>
								</ul>
							</div>
						</div>
					</div>

					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" class="am-btn am-btn-primary">提交</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	$('#doc-vld-msg').validator(
			{
				onValid : function(validity) {
					$(validity.field).closest('.am-u-sm-9').find('.am-alert')
							.hide();
				},
				onInValid : function(validity) {
					var $field = $(validity.field);
					var $group = $field.closest('.am-u-sm-9');
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