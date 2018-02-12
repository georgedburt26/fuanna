$(function() {
	var $fullText = $('.admin-fullText');
	$('#admin-fullscreen').on('click', function() {
		$.AMUI.fullscreen.toggle();
	});

	$(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
		$fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
	});

	var dataType = $('body').attr('data-type');
	for (key in pageData) {
		if (key == dataType) {
			pageData[key]();
		}
	}

	$('.tpl-switch').find('.tpl-switch-btn-view').on('click', function() {
		$(this).prev('.tpl-switch-btn').prop("checked", function() {
			if ($(this).is(':checked')) {
				return false
			} else {
				return true
			}
		})
		// console.log('123123123')

	})
})
// ==========================
// 侧边导航下拉列表
// ==========================

$('.tpl-left-nav-link-list').on(
		'click',
		function() {
			$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80).end()
					.find('.tpl-left-nav-more-ico').toggleClass(
							'tpl-left-nav-more-ico-rotate');
			$(this).addClass("active");
			$(this).parent().siblings().find("a").removeClass("active");
		})

$('.tpl-left-nav-sub-menu li a').on(
		'click',
		function() {
			$(".tpl-left-nav-link-list").removeClass("active");
			$(this).parent().parent().parent().find("a").addClass("active");
			$(this).parent().parent().parent().siblings().find("ul li a")
					.removeClass("active");
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
		});
// ==========================
// 头部导航隐藏菜单
// ==========================

$('.tpl-header-nav-hover-ico').on('click', function() {
	$('.tpl-left-nav').toggle();
	$('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
});

$('body').on('click', ".tpl-table-fz-check", function() {
	$('.tpl-table-fz-data-check').prop("checked", $(this).is(':checked'));
});

$(window).bind('resize', function() {
	$("#datatable").dataTable().fnAdjustColumnSizing(false);
});

function pageContent(url) {
	if (url != null && url != "") {
		// $(".tpl-content-wrapper").html("");
		// var progress = $.AMUI.progress
		// .configure({
		// barSelector : '[role="nprogress-bar"]',
		// parent : '.tpl-content-wrapper',
		// template : "<div class='am-progress am-progress-striped
		// am-progress-sm am-active '>"
		// + "<div class='am-progress-bar am-progress-bar-secondary'
		// role='nprogress-bar' style='width:30%'></div>"
		// + "</div>"
		// });
		// progress.start();
		$(".tpl-content-wrapper").html("");
		$(".tpl-content-wrapper").mLoading({
			mask : false
		});
		$(".tpl-content-wrapper").load(url,
				function(responseTxt, statusTxt, xhr) {
					// progress.done();
					if (responseTxt.indexOf("adminLogin.do") >= 0) {
						window.location.href = "admin/login.do";
					}
					if (xhr.status == 200) {
						permission();
					}
					if (xhr.status == 404) {
						$(this).load("404.html");
					}
					if (xhr.status == 500) {
						$(this).load("500.html");
					}
					$(".tpl-content-wrapper").mLoading("hide");
				});
	}
}
var btnResources = $("#btnResources").text();
function permission() {
	if ($("#btnResources")) {
		$("#btnResources").remove();
	}
	$("*[resource]").each(function(index, data) {
		if(btnResources.indexOf($(data).attr("resource")) < 0) {
			$(this).remove();
		}
	});
}

function terminal() {
    var u = navigator.userAgent;
    var u2 = navigator.userAgent.toLowerCase();
    return { //移动终端浏览器版本信息
        trident: u.indexOf('Trident') > -1, //IE内核
        presto: u.indexOf('Presto') > -1, //opera内核
        webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
        mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
        iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
        iPad: u.indexOf('iPad') > -1, //是否iPad
        webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
        iosv: u.substr(u.indexOf('iPhone OS') + 9, 3),
        weixin: u2.match(/MicroMessenger/i) == "micromessenger",
        ali: u.indexOf('AliApp') > -1,
    };
}

// 页面数据
var pageData = {
	// ===============================================
	// 首页
	// ===============================================
	'index' : function indexData() {

		var myScroll = new IScroll('#wrapper', {
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			preventDefault : false,
			fadeScrollbars : true
		});

		var myScrollA = new IScroll('#wrapperA', {
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			preventDefault : false,
			fadeScrollbars : true
		});

		var myScrollB = new IScroll('#wrapperB', {
			scrollbars : true,
			mouseWheel : true,
			interactiveScrollbars : true,
			shrinkScrollbars : 'scale',
			preventDefault : false,
			fadeScrollbars : true
		});

		// document.addEventListener('touchmove', function(e) {
		// e.preventDefault(); }, false);

		// ==========================
		// 百度图表A http://echarts.baidu.com/
		// ==========================

		var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));
		option = {

			tooltip : {
				trigger : 'axis',
			},
			legend : {
				data : [ '邮件', '媒体', '资源' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : true,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			} ],

			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '邮件',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 120, 132, 101, 134, 90, 230, 210 ],
				itemStyle : {
					normal : {
						color : '#59aea2'
					},
					emphasis : {

					}
				}
			}, {
				name : '媒体',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 220, 182, 191, 234, 290, 330, 310 ],
				itemStyle : {
					normal : {
						color : '#e7505a'
					}
				}
			}, {
				name : '资源',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 150, 232, 201, 154, 190, 330, 410 ],
				itemStyle : {
					normal : {
						color : '#32c5d2'
					}
				}
			} ]
		};
		echartsA.setOption(option);
	},
	// ===============================================
	// 图表页
	// ===============================================
	'chart' : function chartData() {
		// ==========================
		// 百度图表A http://echarts.baidu.com/
		// ==========================

		var echartsC = echarts.init(document.getElementById('tpl-echarts-C'));

		optionC = {
			tooltip : {
				trigger : 'axis'
			},
			toolbox : {
				top : '0',
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			legend : {
				data : [ '蒸发量', '降水量', '平均温度' ]
			},
			xAxis : [ {
				type : 'category',
				data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月',
						'10月', '11月', '12月' ]
			} ],
			yAxis : [ {
				type : 'value',
				name : '水量',
				min : 0,
				max : 250,
				interval : 50,
				axisLabel : {
					formatter : '{value} ml'
				}
			}, {
				type : 'value',
				name : '温度',
				min : 0,
				max : 25,
				interval : 5,
				axisLabel : {
					formatter : '{value} °C'
				}
			} ],
			series : [
					{
						name : '蒸发量',
						type : 'bar',
						data : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2,
								32.6, 20.0, 6.4, 3.3 ]
					},
					{
						name : '降水量',
						type : 'bar',
						data : [ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2,
								48.7, 18.8, 6.0, 2.3 ]
					},
					{
						name : '平均温度',
						type : 'line',
						yAxisIndex : 1,
						data : [ 2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4,
								23.0, 16.5, 12.0, 6.2 ]
					} ]
		};

		echartsC.setOption(optionC);

		var echartsB = echarts.init(document.getElementById('tpl-echarts-B'));
		optionB = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				x : 'center',
				data : [ '某软件', '某主食手机', '某水果手机', '降水量', '蒸发量' ]
			},
			radar : [ {
				indicator : [ {
					text : '品牌',
					max : 100
				}, {
					text : '内容',
					max : 100
				}, {
					text : '可用性',
					max : 100
				}, {
					text : '功能',
					max : 100
				} ],
				center : [ '25%', '40%' ],
				radius : 80
			}, {
				indicator : [ {
					text : '外观',
					max : 100
				}, {
					text : '拍照',
					max : 100
				}, {
					text : '系统',
					max : 100
				}, {
					text : '性能',
					max : 100
				}, {
					text : '屏幕',
					max : 100
				} ],
				radius : 80,
				center : [ '50%', '60%' ],
			}, {
				indicator : (function() {
					var res = [];
					for (var i = 1; i <= 12; i++) {
						res.push({
							text : i + '月',
							max : 100
						});
					}
					return res;
				})(),
				center : [ '75%', '40%' ],
				radius : 80
			} ],
			series : [
					{
						type : 'radar',
						tooltip : {
							trigger : 'item'
						},
						itemStyle : {
							normal : {
								areaStyle : {
									type : 'default'
								}
							}
						},
						data : [ {
							value : [ 60, 73, 85, 40 ],
							name : '某软件'
						} ]
					},
					{
						type : 'radar',
						radarIndex : 1,
						data : [ {
							value : [ 85, 90, 90, 95, 95 ],
							name : '某主食手机'
						}, {
							value : [ 95, 80, 95, 90, 93 ],
							name : '某水果手机'
						} ]
					},
					{
						type : 'radar',
						radarIndex : 2,
						itemStyle : {
							normal : {
								areaStyle : {
									type : 'default'
								}
							}
						},
						data : [
								{
									name : '降水量',
									value : [ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7,
											75.6, 82.2, 48.7, 18.8, 6.0, 2.3 ],
								},
								{
									name : '蒸发量',
									value : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7,
											35.6, 62.2, 32.6, 20.0, 6.4, 3.3 ]
								} ]
					} ]
		};
		echartsB.setOption(optionB);
		var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));
		option = {

			tooltip : {
				trigger : 'axis',
			},
			legend : {
				data : [ '邮件', '媒体', '资源' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : true,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			} ],

			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '邮件',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 120, 132, 101, 134, 90, 230, 210 ],
				itemStyle : {
					normal : {
						color : '#59aea2'
					},
					emphasis : {

					}
				}
			}, {
				name : '媒体',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 220, 182, 191, 234, 290, 330, 310 ],
				itemStyle : {
					normal : {
						color : '#e7505a'
					}
				}
			}, {
				name : '资源',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 150, 232, 201, 154, 190, 330, 410 ],
				itemStyle : {
					normal : {
						color : '#32c5d2'
					}
				}
			} ]
		};
		echartsA.setOption(option);
	}
}