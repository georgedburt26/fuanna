<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-sm-12 am-u-md-9">
				<form id="doc-vld-msg" class="am-form am-form-horizontal">
					<input type="hidden" id="adminId" name="adminId" value=${admin.id} />
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">用户名</label>
						<div class="am-u-sm-9">
							<input type="text" id="username" placeholder="输入用户名"
								<c:if test="${type==2}">readonly="readonly"</c:if>
								<c:if test="${type!=1}">value=${admin.username}</c:if>
								minLength="3" required />
						</div>
					</div>
					<c:if test="${type==1}">
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
					</c:if>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">姓名</label>
						<div class="am-u-sm-9">
							<input type="text" id="name" placeholder="姓名" minLength="2"
								<c:if test="${type==2}">readonly="readonly"</c:if>
								<c:if test="${type!=1}">value=${admin.name}</c:if>  required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">手机号</label>
						<div class="am-u-sm-9">
							<input type="text" id="mobilePhone" placeholder="手机号"
								data-validation-message="请输入正确的手机号"
								pattern="^[1][3,4,5,7,8][0-9]{9}$" 
								<c:if test="${type==2}">readonly="readonly"</c:if>
								<c:if test="${type!=1}">value=${admin.mobilePhone}</c:if> required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">电子邮件</label>
						<div class="am-u-sm-9">
							<input type="email" id="email" placeholder="输入你的电子邮件" 
							<c:if test="${type==2}">readonly="readonly"</c:if>
							<c:if test="${type!=1}">value=${admin.email}</c:if> required />
						</div>
					</div>

					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">角色</label>
						<div class="am-u-sm-9">
						<c:if test="${type==2}"><input type="text" id="role" value="${admin.roleName}" readonly="readonly"></c:if>
						<c:if test="${type==1 || type == 3}">
							<input type="hidden" id="role" value="" />
							<div class="am-selected am-dropdown am-dropdown-down"
								id="am-selected-c8n6r" data-am-dropdown>
								<button type="button"
									class="am-selected-btn am-btn am-dropdown-toggle am-btn-default">
									<span class="am-selected-status am-fl" id="select-value">点击选择...</span>
									<i class="am-selected-icon am-icon-caret-down"></i>
								</button>
								<div class="am-selected-content am-dropdown-content"
									style="min-width: 200px;">
									<ul class="am-selected-list">
									</ul>
								</div>
							</div>
							</c:if>
						</div>
					</div>

					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">头像</label>
						<div class="am-u-sm-9">
							<img id="headImg" src=<c:choose>
							<c:when test="${type != 1}">  
							"<%=sourcePath%>${admin.headImg}"     
							</c:when>
							<c:otherwise> 
							"<%=sourcePath%>img/admin/headImg.jpg"    
							</c:otherwise>
							</c:choose>
								style="width: 160.83px;" />
						<c:if test="${type!=2}"><div id="fileContainer"></div></c:if>
						</div>
					</div>
					<c:if test="${type!=2}">
						<div class="am-form-group">
							<div class="am-u-sm-9 am-u-sm-push-3">
								<button type="button" id="save" class="am-btn am-btn-primary">保存</button>
							</div>
						</div>
					</c:if>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
var type = "${type}";
$('[data-am-dropdown]').dropdown();
$.post("admin/listRoles.do", {}, function(data) {
	if (data.errorCode != "0000") {
		showmsg(data.errorCode, data.errorMsg);
		return;
	}
	$.each(data.data,function(index,value){
		$(".am-selected-list").append("<li class='' id='selectItem" + value.id + "' data-index='" + index + "' data-group='0' data-value='" + value.id + "'>" +
				                      "<span class='am-selected-text'>" + value.name + "</span> <i class='am-icon-check'></i></li>");
	});
	if(type == "3") {
		$.each("${admin.role}".split(","),function(index,value){
			$("#selectItem" + value).toggleClass("am-checked");
		});
		$("#select-value").text("${admin.roleName}");
		$("#role").val("${admin.role}");
	}
});
	renderFile($("#fileContainer"), {
		source:"<%=source%>",
		filepath:"/img/admin/headImg/",
		filetype:"jpg,png,jpeg,gif,bmp",
		id:"headImgValue",
		callback:function(url) {
			$("#headImg").attr("src", "<%=sourcePath%>" + url);
		}
	});
	$('#doc-vld-msg').validator(
			{
				ignore : '',
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
	$('body').on(
			'click',
			".am-selected-list li",
			function() {
				$(this).toggleClass("am-checked");
				var values = new Array();
				var role = new Array();
				$(".am-checked").each(function() {
					values.push($(this).find(".am-selected-text").text());
					role.push($(this).attr("data-value"));
				});
				$("#select-value").text(
						values.length == 0 ? "点击选择..." : values.join(","));
				$("#role").val(role.join(","));
			});
	$("#save").on('click', function() {
		if ($('#doc-vld-msg').validator('isFormValid')) {
			var params = {};
			params.username = $("#username").val();
			params.password = $("#password").val();
			params.confirmpassword = $("#confirmpassword").val();
			params.name = $("#name").val();
			params.mobilePhone = $("#mobilePhone").val();
			params.email = $("#email").val();
			params.role = $("#role").val();
			params.headImg = $("#headImgValue").val();
			params.type = "${type}";
			params.adminId = $("#adminId").val();
			$("#save").text("保存中...");
			$("#save").attr('disabled',"true");
			$.ajax({
				cache : true,
				type : "POST",
				url : "admin/addAdmin.do",
				data : params,
				error : function(data) {
					showmsg("9999", "保存失败");
					$("#save").removeAttr("disabled");
					$("#save").text("保存");
				},
				success : function(data) {
					showmsg(data.errorCode, data.errorMsg);
					if (data.errorCode == "0000") {
						pageContent('admin/adminManage.do');
					}
					else {
						$("#save").removeAttr("disabled");
						$("#save").text("保存");
					}
				}
			});
		}
		;
	});
</script>