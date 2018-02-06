<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="row am-u-md-4 am-u-sm-12 row-mb" id="row_left">
	<div class="am-u-md-12 am-u-sm-12 row-mb">
		<div class="tpl-portlet">
			<div class="tpl-portlet-title">
				<div class="tpl-caption font-green ">
					<i class="am-icon-barcode"></i> <span>条形码</span>
				</div>
			</div>
			<div>
				<input type="text" id="barcode" placeholder="请扫描条形码"
					style="outline: none; margin-top: 38px; width: 100%; height: 30px; padding: 2px 26px 3px 10px; font-size: 13px; border: 1px solid #ddd; border-radius: 3px;" />
			</div>
		</div>
	</div>
	<div class="am-u-md-12 am-u-sm-12 row-mb">
		<div class="tpl-portlet">
			<div class="tpl-portlet-title">
				<div class="tpl-caption font-green ">
					<i class="am-icon-table"></i> <span>商品信息</span>
				</div>
			</div>
			<div id="productInfo">
				<ul>
					<li><span class="no-change-line">条形码:</span><span
						class="change-line" id="p_barcode"></span></li>
					<li><span class="no-change-line">商品名:</span><span
						class="change-line" id="p_productName"></span></li>
					<li><span class="no-change-line">类别:</span><span
						class="change-line" id="p_category"></span></li>
					<li><span class="no-change-line">价格:</span><span
						class="change-line" id="p_price"></span></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="row am-u-md-8 am-u-sm-12 row-mb" id="row_right">
	<div class="am-u-md-12 am-u-sm-12 row-mb">
		<div class="tpl-portlet">
			<div class="tpl-portlet-title">
				<div class="tpl-caption font-green ">
					<i class="am-icon-bar-chart"></i> <span>待入库商品列表</span>
				</div>
				<div class="input-icon right"
					style="display: inline-block; float: right;">
					<i class="am-icon-search" id="table_search"></i> <input type="text"
						id="table_search_field" class="form-control form-control-solid"
						placeholder="搜索...">
				</div>
			</div>
			<div class="tpl-scrollable">
				<table class="am-table am-table-hover table-main" id="datatable">
					<thead>
						<tr>
							<th class="table-barcode">条形码</th>
							<th class="table-productname">商品名</th>
							<th class="table-category">类别</th>
							<th class="table-price">价格（元）</th>
							<th class="table-num">数量</th>
							<th class="table-option">操作</th>
							<th class="table-delete">删除</th>
						</tr>
					</thead>
				</table>
				<div class="am-form-group">
					<div class="am-u-sm-9 am-u-sm-push-3" style="padding: 0px;">
						<button type="button" id="save" class="am-btn am-btn-primary"
							style="float: right">入库</button>
					</div>
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
							"dom" : '<t><ilp>',
							"bProcessing" : false,
							"bLengthChange" : true, //改变每页显示数据数量
							"bAutoWidth" : false, //自动适应宽度
							"bFilter" : true, //查询
							"bSort" : false, //排序
							"bInfo" : true, //页脚信息
							"bDestroy" : true,
							"bServerSide" : false,
							"bSortCellsTop" : true,
							"bPaginate" : true, //显示分页器
							"sPaginationType" : "full_numbers",
							"iDisplayLength " : 10, //一页显示条数
							"sScrollX" : "100%",
							"sAjaxDataProp" : "rows",//是服务器分页的标志，必须有
							"sZeroRecords" : "没有检索到数据",
							"retrieve" : true,//保证只有一个table实例  
							'bStateSave' : true,
							"aoColumns" : [
									{
										//自定义列
										"sName" : "barcode", //Ajax提交时的列明（此处不太明白，为什么用两个属性--sName，mDataProp）
										"mDataProp" : "barcode", //获取数据列名
										"render" : function(data, type, row) { //列渲染
											return "<span id='" + data + "'>"
													+ data + "</span>";
										}
									},
									{
										"sName" : "productName",
										"mDataProp" : "productName"
									},
									{
										"sName" : "category",
										"mDataProp" : "category"
									},
									{
										"sName" : "price",
										"mDataProp" : "price"
									},
									{
										"sName" : "num",
										"mDataProp" : "num",
										"render" : function(data, type, row) {
											return "<span id='"
													+ row.barcode
													+ "_num' onclick='updateNum(\""
													+ row.barcode
													+ "_num\",this," + data
													+ ")'>" + data + "</span>";
										}
									},
									{
										"render" : function(data, type, row) {
											return "<div class='am-btn-toolbar' style='display: inline-block'>"
													+ "<span row='"
													+ row
													+ "' onclick='operateNum("
													+ row.barcode
													+ ", true)' class='addNum am-icon-plus-circle am-icon-sm' style='margin-right:10px;cursor:pointer;'></span>"
													+ "<span onclick='operateNum("
													+ row.barcode
													+ ", false)' class='minusNum am-icon-minus-circle am-icon-sm' style='cursor:pointer;'></span>"
													+ "</div>";
										}
									},
									{
										"render" : function(data, type, row) {
											return "<div class='am-btn-toolbar' style='display: inline-block'>"
													+ "<div class='am-btn-group am-btn-group-xs'> "
													+ "<button type='button' "
													+ "class='am-btn am-btn-default am-btn-xs am-text-danger' onclick='deleteRow("
													+ row.barcode
													+ ",this)'>"
													+ "<span class='am-icon-trash-o'></span> 删除"
													+ "</button>"
													+ "</div>"
													+ "</div>";
										}
									} ]
						});
		$('#table_search').on('click', function() {
			dataTable.fnFilter($("#table_search_field").val() + '');
		});
		$('#table_search_field').bind('keypress', function(event) {
			if (event.keyCode == 13) {
				dataTable.fnFilter($("#table_search_field").val() + '');
			}
		});
		$("#save").on("click", function() {
			var nTrs = dataTable.fnGetNodes();//fnGetNodes获取表格所有行，nTrs[i]表示第i行tr对象  
			if (nTrs.length == 0) {
				showmsg("9999", "待入库商品列表空");
				return;
			}
			var d = dialog({
				title : '消息',
				content : '是否确定入库？',
				okValue : '确 定',
				ok : function() {
					var array = new Array();
					for (var i = 0; i < nTrs.length; i++) {
						array.push(dataTable.fnGetData(nTrs[i]));//fnGetData获取一行的数据  
					}
					$.post("admin/stockIn.do", {
						"data" : JSON.stringify(array)
					}, function(data) {
						if (data.errorCode == "0000") {
							dataTable.fnClearTable();
							$("#p_barcode").text("");
							$("#p_productName").text("");
							$("#p_category").text("");
							$("#p_price").text("");
							$("#barcode").val("");
						} else {
							$.each(data.data,function(index,value){
								var tableData = {};
								tableData.barcode = value.barcode;
								tableData.productName = value.productName == null ? ""
										: value.productName;
								tableData.category = value.category == null ? ""
										: value.category;
								tableData.price = value.normalPrice == null ? ""
										: value.normalPrice
												+ "元";
								tableData.num = value.inventory == null ? ""
										: value.inventory;;
								dataTable.fnAddData(tableData);
							})
						}
						showmsg(data.errorCode, data.errorMsg);
					});
				},
				cancelValue : '取消',
				cancel : function() {
					$(".table-main button").css("background", "#fff");
				}
			});
			d.showModal();
		});
		$("#barcode").focus();
		$('#barcode')
				.bind(
						'keypress',
						function(event) {
							if (event.keyCode == 13) {
								if ($("#barcode").val() == "") {
									showmsg("9999", "条形码不能为空");
									return;
								}
								$(".tpl-content-wrapper").mLoading({
									mask : false
								});
								$
										.post(
												"admin/findProductByBarCode.do",
												{
													"barcode" : $("#barcode")
															.val()
												},
												function(data) {
													var tableData = {};
													tableData.barcode = data.data.barcode;
													tableData.productName = data.data.name == null ? ""
															: data.data.name;
													tableData.category = data.data.category == null ? ""
															: data.data.category;
													tableData.price = data.data.normalPrice == null ? ""
															: data.data.normalPrice
																	+ "元";
													tableData.num = 1;
													$("#p_barcode").text(
															tableData.barcode);
													$("#p_productName")
															.text(
																	tableData.productName);
													$("#p_category").text(
															tableData.category);
													$("#p_price").text(
															tableData.price);
													if (!operateNum(
															tableData.barcode,
															true)) {
														dataTable
																.fnAddData(tableData);
													}
													$(".tpl-content-wrapper")
															.mLoading("hide");
												});
							}
						});

		function operateNum(barcode, isAdd) {
			if (document.getElementById(barcode) == null) {
				return false;
			}
			var num = ($("#" + barcode + "_num").text() == null || $(
					"#" + barcode + "_num").text() == "") ? $(
					"#" + barcode + "_num").val() : $("#" + barcode + "_num")
					.text();
			var rowIndex = dataTable.fnGetPosition(document
					.getElementById(barcode + "").parentNode.parentNode);
			var colIndex = $("#" + barcode + "_num").parent().index();
			var data = isAdd ? parseInt(num) + 1 : parseInt(num) - 1;
			if (data == 0) {
				dataTable.fnDeleteRow($("#" + barcode).parent().parent(), null,
						false);
				dataTable.fnDraw(false);
				return;
			}
			dataTable.fnUpdate(data, rowIndex, colIndex, false);
			return true;
		}
		function updateNum(id, obj, value) {
			obj.parentNode.innerHTML = "<input type='text' id=" + id
					+ " onblur='updateInputNum(\"" + id
					+ "\",this)' onkeypress='updateInputNum(\"" + id
					+ "\",this," + obj.value + ")' value='" + value
					+ "'></input>";
			$("#" + id).val("").focus().val(value);
		}

		function updateInputNum(id, obj) {
			var value = obj.value;
			var event = window.event;
			if (typeof (event.keyCode) != "undefined" && event.keyCode != 13) {
				console.log(typeof (event.keyCode));
				return;
			}
			if (value == null || value == "") {
				showmsg("9999", "修改数量不能为空");
				obj.value = 1;
				return;
			}
			if (parseInt(value) <= 0) {
				showmsg("9999", "修改数量不能小于等于0");
				obj.value = 1;
				return;
			}
			obj.parentNode.innerHTML = "<span id=" + id
					+ " onclick='updateNum(\"" + id + "\",this," + value
					+ ")'>" + value + "</span>";
		}
		function deleteRow(barcode) {
			var d = dialog({
				title : '消息',
				content : '是否确定，删除选中项？',
				okValue : '确 定',
				ok : function() {
					dataTable.fnDeleteRow($("#" + barcode).parent().parent(),
							null, false);
					dataTable.fnDraw(false);
				},
				cancelValue : '取消',
				cancel : function() {
					$(".table-main button").css("background", "#fff");
				}
			});
			d.show();
		}
	</script>