<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<style>
.am-form-group {
width:50%;
float:left;
}
.am-form-group div{
float:left;
}
#doc-vld-msg {
float:left;
width:100%;
}
</style>
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-sm-12">
				<form id="doc-vld-msg" class="am-form am-form-horizontal" data-am-validator>
				   <div class="am-form-group">
						<div><label class="am-form-label">条形码</label></div>
						<div class="am-u-sm-9">
						<input type="text" id="productName" readonly="readonly" required />
						</div>
					</div>
					<div class="am-form-group">
						<div><label class="am-form-label">商品名</label></div>
						<div class="am-u-sm-9">
						<input type="text" id="productName" placeholder="输入商品名" required />
						</div>
					</div>
					<div class="am-form-group">
						<div><label class="am-form-label">商品描述</label></div>
						<div class="am-u-sm-9">
							 <textarea rows="5" id="description" minlength="6" maxlength="500"></textarea>
						</div>
					</div>
					<div class="am-form-group" style="width:100%;">
						<div class="am-u-sm-9">
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
			$.post("admin/publicNotice.do", {"content":$("#noticeContent").val()}, function(data) {
				if (data.errorCode == "0000") {
					$("#notice_content_value").text(data.data.content);
					$("#notice_publishTime_value").text(data.data.publishTime);
				}
				$("#noticeContent").val("");
				showmsg(data.errorCode, data.errorMsg);
			});
		}
	});
</script>