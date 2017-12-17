<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-sm-12 am-u-md-9">
				<form id="doc-vld-msg" class="am-form am-form-horizontal">
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">角色名</label>
						<div class="am-u-sm-9">
							<input type="text" id="name" placeholder="输入角色名" required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">描述</label>
						<div class="am-u-sm-9">
							<input type="text" id="name" placeholder="请输入描述" required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">权限模块</label>
						<div class="am-u-sm-9">
							<ul class="am-tree am-tree-folder-select" role="tree"
								id="resourceTree">
								<li class="am-tree-branch am-hide" data-template="treebranch"
									role="treeitem" aria-expanded="false">
									<div class="am-tree-branch-header">
										<button
											class="am-tree-icon am-tree-icon-caret am-icon-caret-right" id="folderBtn">
											<span class="am-sr-only">Open</span>
										</button>
										<button class="am-tree-branch-name">
											<span class="am-tree-icon am-tree-icon-folder"></span> <span
												class="am-tree-label"></span>
										</button>
									</div>
									<ul class="am-tree-branch-children" role="group"></ul>
									<div class="am-tree-loader" role="alert">Loading...</div>
								</li>
								<li class="am-tree-item am-hide" data-template="treeitem"
									role="treeitem">
									<button class="am-tree-item-name">
										<span class="am-tree-icon am-tree-icon-item"></span> <span
											class="am-tree-label"></span>
									</button>
								</li>
							</ul>
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<button type="button" id="save" class="am-btn am-btn-primary">保存</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	$('[data-am-dropdown]').dropdown();
	var data = [ {
		title : '苹果公司',
		type : 'folder',
		attr : {
			folderIcon : 'am-icon-spinner',
			classNames : 'nimeide',
			id : "nimeide"
		},
		products : [ {
			title : 'iPhone',
			type : 'item'
		}, {
			title : 'iMac',
			type : 'item'
		}, {
			title : 'MacBook Pro',
			type : 'item'
		} ]
	}, {
		title : '微软公司',
		type : 'item'
	}, {
		title : 'GitHub',
		type : 'item',
		attr : {
			icon : 'am-icon-spinner'
		}
	} ];

	var resourceTree = $('#resourceTree').tree({
		multiSelect : true,
		cacheItems : true,
		folderSelect : true,
		dataSource : function(options, callback) {
			callback({
				data : options.products || data
			});
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
			params.headImg = $("#headImg").val();
			$.ajax({
				cache : true,
				type : "POST",
				url : "admin/addAdmin.do",
				data : params,
				error : function(data) {
					showmsg("9999", "保存失败")
				},
				success : function(data) {
					showmsg(data.errorCode, data.errorMsg);
					if (data.errorCode == "0000") {
						pageContent('admin/adminManage.do');
					}
				}
			});
		}
		;
	});
</script>