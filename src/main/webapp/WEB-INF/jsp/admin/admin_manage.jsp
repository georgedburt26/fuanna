<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-6">
				<div class="am-btn-toolbar">
					<div class="am-btn-group am-btn-group-xs">
						<button type="button" id="addAdmin"
							class="am-btn am-btn-default am-btn-success">
							<span class="am-icon-plus"></span> 新增
						</button>
						<!--	<button type="button"
							class="am-btn am-btn-default am-btn-secondary">
							<span class="am-icon-save"></span> 保存
						</button>
						<button type="button" class="am-btn am-btn-default am-btn-warning">
							<span class="am-icon-archive"></span> 审核
						</button>-->
						<button type="button" id="deleteAdmin"
							class="am-btn am-btn-default am-btn-danger">
							<span class="am-icon-trash-o"></span> 删除
						</button>
					</div>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-3">
				<div class="am-form-group">
					<select data-am-selected="{btnSize: 'sm'}" style="display: none;">
						<option value="option1">所有类别</option>
						<option value="option2">IT业界</option>
						<option value="option3">数码产品</option>
						<option value="option3">笔记本电脑</option>
						<option value="option3">平板电脑</option>
						<option value="option3">只能手机</option>
						<option value="option3">超极本</option>
					</select>
					<div class="am-selected am-dropdown " id="am-selected-2agoa"
						data-am-dropdown="">
						<button type="button"
							class="am-selected-btn am-btn am-dropdown-toggle am-btn-sm am-btn-default">
							<span class="am-selected-status am-fl">所有类别</span> <i
								class="am-selected-icon am-icon-caret-down"></i>
						</button>
						<div class="am-selected-content am-dropdown-content">
							<h2 class="am-selected-header">
								<span class="am-icon-chevron-left">返回</span>
							</h2>
							<ul class="am-selected-list">
								<li class="am-checked" data-index="0" data-group="0"
									data-value="option1"><span class="am-selected-text">所有类别</span>
									<i class="am-icon-check"></i></li>
								<li class="" data-index="1" data-group="0" data-value="option2">
									<span class="am-selected-text">IT业界</span> <i
									class="am-icon-check"></i>
								</li>
								<li class="" data-index="2" data-group="0" data-value="option3">
									<span class="am-selected-text">数码产品</span> <i
									class="am-icon-check"></i>
								</li>
								<li class="" data-index="3" data-group="0" data-value="option3">
									<span class="am-selected-text">笔记本电脑</span> <i
									class="am-icon-check"></i>
								</li>
								<li class="" data-index="4" data-group="0" data-value="option3">
									<span class="am-selected-text">平板电脑</span> <i
									class="am-icon-check"></i>
								</li>
								<li class="" data-index="5" data-group="0" data-value="option3">
									<span class="am-selected-text">只能手机</span> <i
									class="am-icon-check"></i>
								</li>
								<li class="" data-index="6" data-group="0" data-value="option3">
									<span class="am-selected-text">超极本</span> <i
									class="am-icon-check"></i>
								</li>
							</ul>
							<div class="am-selected-hint"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-3">
				<div class="am-input-group am-input-group-sm">
					<input type="text" class="am-form-field"> <span
						class="am-input-group-btn">
						<button
							class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
							type="button"></button>
					</span>
				</div>
			</div>
		</div>
		<div class="am-g">
			<div class="am-u-sm-12">
				<form class="am-form">
					<table class="am-table am-table-hover table-main" id="datatable">
						<thead>
							<tr>
								<th class="table-check"
									style="text-align: center; padding: 0px; vertical-align: middle;"><input
									type="checkbox" class="tpl-table-fz-check" value="2"></th>
								<th class="table-username">用户名</th>
								<th class="table-name">姓名</th>
								<th class="table-mobilePhone">手机号</th>
								<th class="table-email">邮箱</th>
								<th class="table-role">角色</th>
								<th class="table-createTime">创建日期</th>
								<th class="table-set">操作</th>
							</tr>
						</thead>
					</table>

				</form>
			</div>

		</div>
	</div>
</div>
<style>
.am-datatable-info {
	float: left;
}

#datatable_length {
	font-size: 1.4rem;
	color: #555;
	float: left;
	margin-left: 1rem;
}

.am-datatable-length label {
	display: inline-block;
	font-weight: normal;
	vertical-align: middle;
}

.am-datatable-length select {
	display: inline-block;
	width: auto;
}

.dataTables_paginate {
	float: right;
}
</style>
<script>
	var dataTable = $('#datatable')
			.dataTable(
					{
						"oLanguage" : { //语言设置
							"sLengthMenu" : "每页显示 _MENU_ 条记录",
							"sZeroRecords" : "对不起，查询不到相关数据！",
							"sEmptyTable" : "表中无数据存在！",
							"sInfo" : "当前显示 _START_ 到 _END_ 条",
							"sInfoFiltered" : "，共 _MAX_ 条记录",
							"sSearch" : "搜索",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "末页"
							}
						},
						"dom" : '<tr><ilp>',
						"bProcessing" : false,
						"bLengthChange" : true, //改变每页显示数据数量
						"bAutoWidth" : false, //自动适应宽度
						"bFilter" : false, //查询
						"bSort" : false, //排序
						"bInfo" : true, //页脚信息
						"bDestroy" : true,
						"bServerSide" : true,
						"bSortCellsTop" : true,
						"sAjaxSource" : "admin/adminManageList.do",
						"bPaginate" : true, //显示分页器
						"sPaginationType" : "full_numbers",
						"iDisplayLength " : 10, //一页显示条数
						"sScrollX" : "100%",
						"sAjaxDataProp" : "rows",//是服务器分页的标志，必须有
						"sZeroRecords" : "没有检索到数据",
						"retrieve" : true,//保证只有一个table实例  
						"aoColumns" : [
								{
									//自定义列
									"sName" : "id", //Ajax提交时的列明（此处不太明白，为什么用两个属性--sName，mDataProp）
									"mDataProp" : "id", //获取数据列名
									"render" : function(data, type, row) { //列渲染
										return '<input type="checkbox" class="tpl-table-fz-data-check" value="'+data+'"/>';
									}
								},
								{
									"sName" : "username",
									"mDataProp" : "username",
								},
								{
									"sName" : "name",
									"mDataProp" : "name",
								},
								{
									"sName" : "mobilePhone",
									"mDataProp" : "mobilePhone",
								},
								{
									"sName" : "email",
									"mDataProp" : "email",
								},
								{
									"sName" : "roleName",
									"mDataProp" : "roleName",
								},
								{
									"sName" : "createTime",
									"mDataProp" : "createTime",
								},
								{
									"render" : function(data, type, row) {
										return "<div class='am-btn-toolbar' style='display: inline-block'>"
												+ "<div class='am-btn-group am-btn-group-xs'> "
												+ "<button type='button' " +
											"class='am-btn am-btn-default am-btn-xs am-hide-sm-only'>"
												+ "<span class='am-icon-file-text-o'></span>查看"
												+ "</button>"
												+ "<button type='button' " +
											"class='am-btn am-btn-default am-btn-xs am-text-secondary'>"
												+ "<span class='am-icon-pencil-square-o'></span> 编辑"
												+ "</button>"
												+ "<button type='button' "
												+ "class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' onclick='deleteAdmin("
												+ row.id
												+ ",this)'>"
												+ "<span class='am-icon-trash-o'></span> 删除"
												+ "</button>"
												+ "</div>"
												+ "</div>"
									}
								} ],
						"fnServerData" : function(source, rows, fnCallback) {
							$.ajax({
								url : source,//这个就是请求地址对应sAjaxSource
								data : {
									"rows" : JSON.stringify(rows)
								},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
								type : 'post',
								dataType : 'json',
								success : function(result) {
									if (result.errorCode == '9999') {
										showmsg(result.errorCode,
												result.errorMsg);
										return;
									}
									fnCallback(result.data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
								},
								error : function(msg) {
									showmsg("9999", "系统繁忙，请稍后再试.");
								}
							});
						}
					});
	$("#addAdmin").click(function() {
		pageContent("admin/adminIndex.do?type=1");
	});
	$("#deleteAdmin").click(function() {
		if ($(".tpl-table-fz-data-check:checked").length == 0) {
			showmsg("9999", "请选择要删除的选项");
			return;
		}
		var ids = new Array();
		$(".tpl-table-fz-data-check:checked").each(function() {
			ids.push($(this).val());
		});
		deleteAdmin(ids.join(","));
	});
	function deleteAdmin(ids, btn) {
		var d = dialog({
			title : '消息',
			content : '是否确定，删除选中项？',
			okValue : '确 定',
			ok : function() {
				$.post("admin/deleteAdmin.do", {
					"ids" : ids
				}, function(data) {
					showmsg(data.errorCode, data.errorMsg);
					dataTable.fnDraw();
				});
			},
			cancelValue : '取消',
			cancel : function() {
				$(".table-main button").css("background", "#fff");
			}
		});
		d.show();
	}
</script>