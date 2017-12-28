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
						style="outline:none;margin-top: 38px;width:100%;height: 30px;padding: 2px 26px 3px 10px;font-size: 13px;border:1px solid #ddd;border-radius:3px;" />
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
					style="position: absolute; display: none; border-style: solid; white-space: nowrap; z-index: 9999999; transition: left 0.4s cubic-bezier(0.23, 1, 0.32, 1), top 0.4s cubic-bezier(0.23, 1, 0.32, 1); background-color: rgba(50, 50, 50, 0.7); border-width: 0px; border-color: rgb(51, 51, 51); border-radius: 4px; color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 14px; font-family: &amp; amp; amp; amp; amp; amp; quot; Microsoft YaHei&amp;amp; amp; amp; amp; amp; quot;; line-height: 21px; padding: 5px; left: 595.858px; top: 224px;">
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
			</div>
			<div class="tpl-scrollable">
				<div class="number-stats">
					<div class="stat-number am-fl am-u-md-6">
						<div class="title am-text-right">Total</div>
						<div class="number am-text-right am-text-warning">2460</div>
					</div>
					<div class="stat-number am-fr am-u-md-6">
						<div class="title">Total</div>
						<div class="number am-text-success">2460</div>
					</div>

				</div>

				<table class="am-table tpl-table">
					<thead>
						<tr class="tpl-table-uppercase">
							<th>人员</th>
							<th>余额</th>
							<th>次数</th>
							<th>效率</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><img src="assets/img/user01.png" alt="" class="user-pic">
								<a class="user-name" href="###">禁言小张</a></td>
							<td>￥3213</td>
							<td>65</td>
							<td class="font-green bold">26%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user02.png" alt="" class="user-pic">
								<a class="user-name" href="###">Alex.</a></td>
							<td>￥2635</td>
							<td>52</td>
							<td class="font-green bold">32%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user03.png" alt="" class="user-pic">
								<a class="user-name" href="###">Tinker404</a></td>
							<td>￥1267</td>
							<td>65</td>
							<td class="font-green bold">51%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user04.png" alt="" class="user-pic">
								<a class="user-name" href="###">Arron.y</a></td>
							<td>￥657</td>
							<td>65</td>
							<td class="font-green bold">73%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user05.png" alt="" class="user-pic">
								<a class="user-name" href="###">Yves</a></td>
							<td>￥3907</td>
							<td>65</td>
							<td class="font-green bold">12%</td>
						</tr>

						<tr>
							<td><img src="assets/img/user06.png" alt="" class="user-pic">
								<a class="user-name" href="###">小黄鸡</a></td>
							<td>￥900</td>
							<td>65</td>
							<td class="font-green bold">10%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user06.png" alt="" class="user-pic">
								<a class="user-name" href="###">小黄鸡</a></td>
							<td>￥900</td>
							<td>65</td>
							<td class="font-green bold">10%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user06.png" alt="" class="user-pic">
								<a class="user-name" href="###">小黄鸡</a></td>
							<td>￥900</td>
							<td>65</td>
							<td class="font-green bold">10%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user06.png" alt="" class="user-pic">
								<a class="user-name" href="###">小黄鸡</a></td>
							<td>￥900</td>
							<td>65</td>
							<td class="font-green bold">10%</td>
						</tr>
						<tr>
							<td><img src="assets/img/user06.png" alt="" class="user-pic">
								<a class="user-name" href="###">小黄鸡</a></td>
							<td>￥900</td>
							<td>65</td>
							<td class="font-green bold">10%</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script>
$("#barcode").focus();
$('#barcode').bind('keypress',function(event){ 
    if(event.keyCode == 13)      
    {  
        alert('你输入的内容为1：' + $('#barcode').val());  
    } 
});
</script>