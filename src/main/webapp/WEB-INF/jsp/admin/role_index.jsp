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
							<input type="text" id="description" placeholder="请输入描述" required />
						</div>
					</div>
					<div class="am-form-group">
						<label class="am-u-sm-3 am-form-label">权限模块</label>
						<div class="am-u-sm-9">
						<span class="am-icon-spin am-icon-spinner" id="treeloading"></span>
							<ul class="am-tree am-tree-folder-select" role="tree"
								id="resourceTree">
								<li class="am-tree-branch am-hide" data-template="treebranch"
									role="treeitem" aria-expanded="false">
									<div class="am-tree-branch-header">
										<button
											class="am-tree-icon am-tree-icon-caret am-icon-caret-right" type="button">
											<span class="am-sr-only">Open</span>
										</button>
										<button class="am-tree-branch-name" type="button">
											<span class="am-tree-icon am-tree-icon-folder"></span> <span
												class="am-tree-label"></span>
										</button>
									</div>
									<ul class="am-tree-branch-children" role="group"></ul>
									<div class="am-tree-loader" role="alert"><span class="am-icon-spin am-icon-spinner"></span></div>
								</li>
								<li class="am-tree-item am-hide" data-template="treeitem"
									role="treeitem">
									<button class="am-tree-item-name" type="button">
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
	var resourceTree = $('#resourceTree').tree({
		multiSelect : true,
		cacheItems : true,
		folderSelect : true,
		dataSource : function(options, callback) {
			var data = new Array();
			$.post("admin/listResources.do", {}, function(result) {
				if (result.errorCode != '0000') {
					showmsg(result.errorCode, result.errorMsg);
					return ;
				}
				$.each(result.data,function(index,value){
					var item = {};
					item.title = value.name;
					item.type= value.resources.length > 0 ? "folder" : "item";
					item.attr = {};
					item.attr.id = value.id;
					item.attr.icon = value.icon;
					if (value.resources.length > 0) {
						item.products = new Array();
						$.each(value.resources,function(index,value){
							item.products.push({
								title:value.name,
								type:"item",
								attr:{
									id:value.id
								}
							});
						});
					}
					data.push(item);
				});
				callback({
					data : options.products || data
				});
				$("#treeloading").remove();
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
			var resources = new Array();
			$.each(resourceTree.tree('selectedItems'),function(index,value){
				resources.push(value.attr.id);
			});
			var params = {};
			params.name = $("#name").val();
			params.description = $("#description").val();
			params.resources = resources.join(",");
			$.ajax({
				cache : true,
				type : "POST",
				url : "admin/addRole.do",
				data : params,
				error : function(data) {
					showmsg("9999", "保存失败")
				},
				success : function(data) {
					showmsg(data.errorCode, data.errorMsg);
					if (data.errorCode == "0000") {
						pageContent('admin/roleManage.do');
					}
				}
			});
		}
		;
	});
</script>