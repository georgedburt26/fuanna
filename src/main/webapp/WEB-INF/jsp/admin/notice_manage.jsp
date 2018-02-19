<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-sm-12 am-u-md-9">
				<form id="doc-vld-msg" class="am-form am-form-horizontal" data-am-validator>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">公告</label>
						<div class="am-u-sm-9">
							 <textarea rows="5" id="noticeContent" minlength="6" maxlength="500"></textarea>
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" id="save" class="am-btn am-btn-primary">发布</button>
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
	
	$("#save").on('click', function() {
		if ($('#doc-vld-msg').validator('isFormValid')) {
			$("#save").text("发布中...");
			$("#save").attr('disabled',"true");
			$.post("admin/publicNotice.do", {"content":$("#noticeContent").val()}, function(data) {
				if (data.errorCode == "0000") {
					$("#notice_content_value").text(data.data.content);
					$("#notice_publishTime_value").text(data.data.publishTime);
				}
				$("#noticeContent").val("");
				showmsg(data.errorCode, data.errorMsg);
				$("#save").text("发布");
				$("#save").removeAttr("disabled");
			});
		}
	});
</script>