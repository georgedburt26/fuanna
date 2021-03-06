<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-6"
				style="display: inline-block; width: auto; margin: 0px;">
				<div class="am-btn-toolbar">
					<div class="am-btn-group am-btn-group-xs">
						<!--<button type="button" id="addAdmin"
							class="am-btn am-btn-default am-btn-success" resource="admin/addAdminIndex.do">
							<span class="am-icon-plus"></span> 新增
						</button>
							<button type="button"
							class="am-btn am-btn-default am-btn-secondary">
							<span class="am-icon-save"></span> 保存
						</button>
						<button type="button" class="am-btn am-btn-default am-btn-warning">
							<span class="am-icon-archive"></span> 审核
						</button>
						<button type="button" id="deleteAdmin"
							class="am-btn am-btn-default am-btn-danger" resource="admin/deleteAdmin.do">
							<span class="am-icon-trash-o"></span> 删除
						</button>-->
					</div>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-3 datatable-search">
				<!--<label>用户名: <input type="text"
					id="datatable-search-username"></label> <label>姓名: <input
					type="text" id="datatable-search-name"></label> <label>手机号:
					<input type="text" id="datatable-search-mobilePhone">
				</label>
				<button
					class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
					type="button" id="datatable-search-btn"></button>-->
			</div>

		</div>
		<div class="am-g">
			<div class="am-u-sm-12">
				<form class="am-form">
					<table class="am-table am-table-hover table-main" id="datatable">
						<thead>
							<tr>
								<!--<th class="table-check"
									style="text-align: center; padding: 0px; vertical-align: middle;"><input
									type="checkbox" class="tpl-table-fz-check"></th>-->
								<th class="table-username">用户名</th>
								<th class="table-name">姓名</th>
								<th class="table-mobilePhone">手机号</th>
								<th class="table-email">邮箱</th>
								<th class="table-ip">IP地址</th>
								<th class="table-terminal">终端</th>
								<th class="table-location">地址</th>
								<th class="table-loginTime">登录时间</th>
								<th class="table-operate">操作</th>
							</tr>
						</thead>
					</table>

				</form>
			</div>

		</div>
	</div>
</div>
<script>
	var dataTable = $('#datatable')
			.dataTable(
					{
						"oLanguage" : { //语言设置
							"sLengthMenu" : "每页显示 _MENU_ 条记录",
							"sZeroRecords" : "对不起，查询不到相关数据！",
							"sEmptyTable" : "没有数据存在！",
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
						"sAjaxSource" : "admin/adminOnlineList.do",
						"bPaginate" : true, //显示分页器
						"sPaginationType" : "full_numbers",
						"iDisplayLength " : 10, //一页显示条数
						"sScrollX" : "100%",
						"sAjaxDataProp" : "rows",//是服务器分页的标志，必须有
						"sZeroRecords" : "没有检索到数据",
						"retrieve" : true,//保证只有一个table实例  
						"fnInitComplete": function() {
							permission();
							},
						"aoColumns" : [
//								{
//									//自定义列
//									"sName" : "id", //Ajax提交时的列明（此处不太明白，为什么用两个属性--sName，mDataProp）
//									"mDataProp" : "id", //获取数据列名
//									"render" : function(data, type, row) { //列渲染
//										return '<input type="checkbox" class="tpl-table-fz-data-check" value="'+data+'"/>';
//									}
//								},
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
									"sName" : "ip",
									"mDataProp" : "ip",
								},
								{
									"sName" : "terminal",
									"mDataProp" : "terminal",	
								},
								{
									"sName" : "location",
									"mDataProp" : "location",	
								},
								{
									"sName" : "loginTime",
									"mDataProp" : "loginTime",
								}
								,
								{
								"render" : function(data, type, row) {
										return "<div class='am-btn-toolbar' style='display: inline-block'>"
											+ "<div class='am-btn-group am-btn-group-xs'> "
//											+ "<button type='button' " +
//											"class='am-btn am-btn-default am-btn-xs' style='display:block' onclick='checkAdmin(" + row.id + ")' resource='admin/readAdminIndex.do'>"
//												+ "<span class='am-icon-file-text-o'></span>查看"
//												+ "</button>"
//												+ "<button type='button' " +
//											"class='am-btn am-btn-default am-btn-xs am-text-secondary' onclick='editAdmin(" + row.id + ")' resource='admin/updateAdminIndex.do'>"
//												+ "<span class='am-icon-pencil-square-o'></span> 编辑"
//												+ "</button>"
												+ "<button type='button' "
												+ "class='am-btn am-btn-default am-btn-xs am-text-danger' onclick='forceLogOut(\""
												+ row.sessionId
												+ "\"," + row.id + ",this)' resource='admin/forceLogOut.do' delete>"
												+ "强制退出"
												+ "</button>"
												+ "</div>"
												+ "</div>";
									}
								} 
								],
						"fnServerData" : function(source, rows, fnCallback) {
							$.ajax({
								url : source,//这个就是请求地址对应sAjaxSource
								data : {
									"rows" : JSON.stringify(rows),
//									"username" : $("#datatable-search-username").val(),
//									"name" : $("#datatable-search-name").val(),
//									"mobilePhone" : $("#datatable-search-mobilePhone").val()
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
	$("#datatable-search-btn").click(function() {
		var username = $("#datatable-search-username").val();
		var name = $("#datatable-search-name").val();
		var mobilePhone = $("#datatable-search-mobilePhone").val();
		dataTable.fnDraw();	
	});
	
	function forceLogOut(sessionId, adminId, object) {
		var d = dialog({
			title : '强制退出',
			content : '<input type="text" id="time" class="am-u-sm-12 am-u-md-6 am-u-lg-6" placeholder="锁定时间" style="border:1px solid #ddd;border-radius:3px;padding:9px;" ></input>' + 
			          '<input type="hidden" id="unit"></input>' + 
			          '<div class="am-u-sm-12 am-u-md-6 am-u-lg-6 am-dropdown am-dropdown-down" ' +
								'id="am-selected-c8n6r" style="display:inline-block;padding:0px;" data-am-dropdown>' + 
								'<button type="button"' + 
									'class="am-selected-btn am-btn am-dropdown-toggle am-btn-default" style="height:39.1px;color:#757575">' + 
									'<span class="am-selected-status am-fl" id="select-value">时间单位</span>' + 
									'<i class="am-selected-icon am-icon-caret-down"></i>' + 
								'</button>' + 
								'<div class="am-u-sm-12 am-u-md-4 am-u-lg-4 am-selected-content am-dropdown-content">' + 
									'<ul class="am-selected-list">' + 
									'<li class="" data-index="0" data-group="0" data-value="0"><span class="am-selected-text">天</span> <i class="am-icon-check"></i></li>' + 
									'<li class="" data-index="1" data-group="0" data-value="1"><span class="am-selected-text">时</span> <i class="am-icon-check"></i></li>' + 
									'<li class="" data-index="2" data-group="0" data-value="2"><span class="am-selected-text">分</span> <i class="am-icon-check"></i></li>' + 
									'</ul>' + 
								'</div>' + 
							'</div>',
			okValue : '确 定',
			ok : function() {
				var time = $("#time").val();
				var unit = $("#unit").val();
				var timeValidate = true;
				var unitValidate = true;
				if (!/^[0-9]*$/.test(time)) {
					showmsg("9999", "锁定时间必须是数字");
					timeValidate = false;
				}
				if (unit == null || unit == "") {
					showmsg("9999", "时间单位不能为空");
					unitValidate = false;
				}
				if (timeValidate && unitValidate) {
				$.post("admin/forceLogOut.do", {
					"time" : time, "unit" : unit
				}, function(data) {
					showmsg(data.errorCode, data.errorMsg);
				});
				}
			},
			cancelValue : '取消',
			cancel : function() {
				
			}
		});
		d.show();
		$('[data-am-dropdown]').dropdown();
	}
	$('body').on(
			'click',
			".am-selected-list li",
			function() {
				$(this).addClass("am-checked");
				$(this).siblings().removeClass("am-checked");
				$("#select-value").text($(this).find(".am-selected-text").text());
				$("#unit").val($(this).attr("data-value"));
				$("#am-selected-c8n6r").dropdown('close');
			});
</script>