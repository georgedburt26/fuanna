<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="tpl-portlet-components">
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-u-md-6"
				style="display: inline-block; width: auto; margin: 0px;">

			</div>
			<div class="am-u-sm-12 am-u-md-3 datatable-search">
				<label>条形码: <input type="text"
					id="datatable-search-barcode"></label> <label>商品名: <input
					type="text" id="datatable-search-name"></label> <label>类别:
					<input type="text" id="datatable-search-category">
				</label>
				<button
					class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search"
					type="button" id="datatable-search-btn"></button>
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
									type="checkbox" class="tpl-table-fz-check"></th>
							<th class="table-barcode">条形码</th>
							<th class="table-productname">商品名</th>
							<th class="table-category">类别</th>
							<th class="table-attr">参数</th>
							<th class="table-price">价格（元）</th>
							<th class="table-inventory">库存</th>
							<th class="table-option">操作</th>
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
						"sAjaxSource" : "admin/productManageList.do",
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
									"sName" : "barcode",
									"mDataProp" : "barcode",
								},
								{
									"sName" : "name",
									"mDataProp" : "name",
								},
								{
									"sName" : "category",
									"mDataProp" : "category",
								},
								{
									"sName" : "attribute",
									"mDataProp" : "attribute",
									"render" : function(data, type, row) {
										var attribute = "";
										for(var i=0; i<data.length; i++){
											 attribute += data.charAt(i);
											 if (i != 0 && i % 20 == 0) {
												 attribute += "<br>"; 
											 }
											 }
										return attribute;
									}
								},
								{
									"sName" : "price",
									"mDataProp" : "price",
								},
								{
									"sName" : "inventory",
									"mDataProp" : "inventory",
								},
								{
									"render" : function(data, type, row) {
										return "<div class='am-btn-toolbar' style='display: inline-block'>"
												+ "<div class='am-btn-group am-btn-group-xs'> "
												+ "<button type='button' " +
											"class='am-btn am-btn-default am-btn-xs am-text-secondary' onclick='editProduct(" + row.barcode + ")'>"
												+ "<span class='am-icon-pencil-square-o'></span> 编辑"
												+ "</button>"
												+ "</div>"
												+ "</div>";
									}
								} ],
						"fnServerData" : function(source, rows, fnCallback) {
							$.ajax({
								url : source,//这个就是请求地址对应sAjaxSource
								data : {
									"rows" : JSON.stringify(rows),
									"barcode" : $("#datatable-search-barcode").val(),
									"name" : $("#datatable-search-name").val(),
									"category" : $("#datatable-search-category").val()
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
		pageContent("admin/addAdminIndex.do?type=1");
	});
	$("#datatable-search-btn").click(function() {
		var username = $("#datatable-search-username").val();
		var name = $("#datatable-search-name").val();
		var mobilePhone = $("#datatable-search-mobilePhone").val();
		if (username != '' || name != '' || mobilePhone != '') {
			dataTable.fnDraw();	
		}
	});
	function editProduct(barcode) {
		pageContent("admin/updateProductIndex.do?barcode=" + barcode);
	}
</script>