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
			<div class="tpl-echarts" id="tpl-echarts-A"
				_echarts_instance_="ec_1513751109538"
				style="-webkit-tap-highlight-color: transparent; user-select: none; position: relative; background: transparent;">
				<div
					style="position: relative; overflow: hidden; width: 746px; height: 400px; cursor: default;">
					<canvas width="746" height="400" data-zr-dom-id="zr_0"
						style="position: absolute; left: 0px; top: 0px; width: 746px; height: 400px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas>
				</div>
				<div
					style="position: absolute; display: none; border-style: solid; white-space: nowrap; z-index: 9999999; transition: left 0.4s cubic-bezier(0.23, 1, 0.32, 1), top 0.4s cubic-bezier(0.23, 1, 0.32, 1); background-color: rgba(50, 50, 50, 0.7); border-width: 0px; border-color: rgb(51, 51, 51); border-radius: 4px; color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 14px; font-family: &amp; amp; amp; amp; amp; amp; amp; amp; quot; Microsoft YaHei&amp;amp; amp; amp; amp; amp; amp; amp; quot;; line-height: 21px; padding: 5px; left: 595.858px; top: 224px;">
					周六<br> <span
						style="display: inline-block; margin-right: 5px; border-radius: 10px; width: 9px; height: 9px; background-color: #59aea2"></span>邮件
					: 230<br> <span
						style="display: inline-block; margin-right: 5px; border-radius: 10px; width: 9px; height: 9px; background-color: #e7505a"></span>媒体
					: 330<br> <span
						style="display: inline-block; margin-right: 5px; border-radius: 10px; width: 9px; height: 9px; background-color: #32c5d2"></span>资源
					: 330
				</div>
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
				<div class="input-icon right" style="display:inline-block;float:right;">
                                <i class="am-icon-search" id="table_search"></i>
                                <input type="text" id="table_search_field" class="form-control form-control-solid" placeholder="搜索..."> </div>
			</div>
			<div class="tpl-scrollable">
				<table class="am-table am-table-hover table-main" id="datatable">
					<thead>
						<tr>
							<th class="table-check"
								style="text-align: center; padding: 0px; vertical-align: middle;"><input
								type="checkbox" class="tpl-table-fz-check"></th>
							<th class="table-productname">商品名</th>
							<th class="table-category">类别</th>
							<th class="table-price">价格</th>
							<th class="table-num">数量</th>
							<th class="table-option">操作</th>
						</tr>
					</thead>
				</table>
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
									"sName" : "productname",
									"mDataProp" : "productname",
								},
								{
									"sName" : "category",
									"mDataProp" : "category",
								},
								{
									"sName" : "price",
									"mDataProp" : "price",
								},
								{
									"sName" : "num",
									"mDataProp" : "num",
								},
								{
									"render" : function(data, type, row) {
										return "<span class='am-icon-minus-circle'></span>" +
										       "<span class='am-icon-plus-circle'></span>";
									}
								} ]
					});
	dataTable.fnAddData( [ {
        "productname":       "123",
        "category":   "Zystem Architect",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "Director",
        "price":"100.00",
        "num":"3"
    }, {
        "productname": "Garrett Winters",
        "category": "2irector",
        "price":"100.00",
        "num":"3"
    } ] );
	$('#table_search').on('click', function() {
		dataTable.fnFilter($("#table_search_field").val() + '');
	});
	$('#table_search_field').bind('keypress', function(event) {
		if (event.keyCode == 13) {
			dataTable.fnFilter($("#table_search_field").val() + '');
		}
	});
	$("#barcode").focus();
	$('#barcode').bind('keypress', function(event) {
		if (event.keyCode == 13) {
			alert('你输入的内容为1：' + $('#barcode').val(), 0);
		}
	});
</script>