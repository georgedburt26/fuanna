<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/lib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<title>扶沟富安娜</title>
<link href="css/css.css" rel="stylesheet" type="text/css" />
<link href="http://www.swiper.com.cn/dist/css/swiper.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#top {
	border-bottom: 1px solid #EEEEEE;
}

#top ul {
	width: 90%;
	margin: 0 auto;
	height: 100%;
}

#top ul li {
	float: left;
	height: 100%;
	list-style-type: none;
}

#top ul li img {
	height: 100%;
}

#bottom {
	border-top: 1px solid #EEEEEE;
	box-shadow: 0px -1px 1px #F7F7F7;
}

#bottom ul {
	margin-top: 2px;
	height: 100%;
}

#bottom ul li {
	float: left;
	width: 25%;
	height: 100%;
	list-style-type: none;
	text-align: center;
	line-height: 100%;
}

#bottom ul li .button img {
	height: 100%;
}

#bottom ul li .button {
	height: 70%;
	text-align: center;
}

#bottom ul li .seperate {
	height: 5%
}

#bottom ul li .summary {
	height: 15%;
	text-align: center;
}

.banner {
	width: 100%;
	overflow-x: hidden;
	position: relative;
	border: 0px;
}

.swiper-slide {
	border: 0px;
	text-align: center;
	font-size: 18px;
	background: #fff;
	/* Center slide text vertically */
	display: -webkit-box;
	display: -ms-flexbox;
	display: -webkit-flex;
	display: flex;
	-webkit-box-pack: center;
	-ms-flex-pack: center;
	-webkit-justify-content: center;
	justify-content: center;
	-webkit-box-align: center;
	-ms-flex-align: center;
	-webkit-align-items: center;
	align-items: center;
	text-align: center;
}

.swiper-slide img {
	width: 100%;
	height: 100%;
	border: 0px;
}

.swiper-pagination-bullet {
	background-color: #EEEEEE;
	opacity: 0.3;
}

.swiper-pagination-bullet-active {
	background-color: #EEEEEE;
	opacity: 1.0;
	box-shadow: 0px 2px 4px #888888;
}

.button-radius {
	border-radius: 50%;
	box-shadow: 0px 1px 2px #F7F7F7;
	border: 1px solid #DDDDDD;
	height: 80%;
}

.category {
	text-align: center;
	height: 60%;
}

.search {
	height: 60%;
}

.search div {
	float: left;
}

.button-search input, img {
	vertical-align: middle;
}

.search input {
	margin-left: 5px;
	border: 0px;
	border-bottom: 1px solid #DDDDDD;
	outline: none;
	height: 100%;
	line-height: 100%;
}

.cart {
	text-align: center;
	height: 60%;
}

.category-list-p {
	position: absolute;
	z-index: 2;
	width: 50%;
}

#category-list ul li {
	background-color: white;
	border-bottom: 1px solid #EEEEEE;
	list-style-type: none;
	text-align: center;
	cursor: pointer;
}

.container {
	margin: 0 auto;
	width: 100%;
}

.container .item {
	float: left;
	width: 44%;
	text-align: center;
	background-color: #fff;
	margin-left: 4%;
	margin-top: 5px;
	border: 1px solid #fff;
}

.container .item img {
	border-radius: 3px;
	margin: 0 auto;
	width: 90%;
	margin-top: 5%;
	margin-bottom: 5%;
}

.container .item .description {
	width: 90%;
	margin: 0 auto;
	height: 10%;
}

.container .item .price {
	width: 90%;
	margin: 0 auto;
	height: 10%;
}
</style>
</head>
<body>
	<div id="top"
		style="width: 100%; height: 8%; position: absolute; z-index: 2">
		<ul>
			<li id="button-category" style="width: 10%;"><div
					style="height: 10%"></div>
				<div class="button-radius">
					<div style="height: 20%"></div>
					<div id="category" class="category">
						<img src="img/category.png">
					</div>
					<div style="height: 20%"></div>
				</div>
				<div style="height: 10%"></div></li>
			<li id="button-search" style="width: 80%"><div
					style="height: 20%"></div>
				<div id="search" class="search">
					<div style="width: 5%; height: 100%"></div>
					<div id="search-img-input"
						style="width: 85%; height: 100%; text-align: center">
						<img src="img/search.png"> <input type="text"
							id="searchText" style="height: 100%">
					</div>
					<div style="width: 10%; height: 100%;"></div>
				</div>
				<div style="height: 20%"></div></li>
			<li id="button-cart" style="width: 10%;"><div
					style="height: 10%"></div>
				<div class="button-radius">
					<div style="height: 20%"></div>
					<div id="cart" class="cart">
						<img src="img/cart.png">
					</div>
					<div style="height: 20%"></div>
				</div>
				<div style="height: 10%"></div></li>
		</ul>
	</div>
	<div id="category-list-p" class="category-list-p">
		<div id="category-list">
			<ul>
				<c:forEach items="${categoryList}" var="item">
					<li>${item.name}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="back-top"></div>
	<div
		style="width: 100%; height: 100%; overflow: hidden; position: absolute; z-index: 1">
		<div style="height: 8%; width: 100%;"></div>
		<div id="main"
			style="width: 100%; height: 92%; background-color: black; overflow-y: scroll;">
			<div id="scrollMenu" style="background-color: #F7F7F7;">
				<div id="banner" class="banner">
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<img src="img/dva.jpg" />
						</div>
						<div class="swiper-slide">
							<img src="img/dva.jpg" />
						</div>
						<div class="swiper-slide">
							<img src="img/dva.jpg" />
						</div>
					</div>
					<div class="swiper-pagination"></div>
				</div>
				<div id="goods">
					<div id="container" class="container">
						<c:forEach items="${productList}" var="item">
							<div class="item">
								<div>
									<img src="img/3.jpg">
								</div>
								<div class="description">${item.name}</div>
								<div class="price">Y${item.price}元</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<!--		<div id="bottom" style="width: 100%; height: 10%;">
			<ul>
				<li id="shouye"><div class="button">
						<img src="img/shouye.png">
					</div>
					<div class="seperate"></div>
					<div class="summary">首页</div></li>
				<li id="fenlei"><div class="button">
						<img src="img/fenlei.png">
					</div>
					<div class="seperate"></div>
					<div class="summary">分类</div></li>
				<li id="gouwuche"><div class="button">
						<img src="img/gouwuche.png">
					</div>
					<div class="seperate"></div>
					<div class="summary">购物车</div></li>
				<li id="wode"><div class="button">
						<img src="img/wode.png">
					</div>
					<div class="seperate"></div>
					<div class="summary">个人中心</div></li>
			</ul>
		</div>-->
	</div>
	<script src="http://libs.baidu.com/jquery/1.8.3/jquery.js"></script>
	<script src="http://www.swiper.com.cn/dist/js/swiper.min.js"></script>
	<script src="js/masonry.pkgd.min.js"></script>
	<script>
		$(function() {
			var winHeight = $(window).height();
			function initWindow() {
				$("#category-list ul li").height(winHeight * 0.05);
				$("#category-list ul li").css("line-height",
						(winHeight * 0.05) + "px");
				$("#banner").height(winHeight * 0.4);
				$(".button-radius").width($(".button-radius").height());
				$(".item").height($(".item").width() * 1.2);
				$(".item img").height($(".item").height() * 0.7);
				$(".item").css("font-size", ($(".item").width() / 13) + "px");
				var searchTextWidth = $("#search-img-input").width() * 0.8;
				$("#searchText").width(searchTextWidth);
				$("#category-list-p").attr("hide", "true");
				$("#category-list-p").css("margin-top",
						(-1 * $("#category-list").height()) + "px");
				$("#category-list-p").css("margin-left",
						($("#top").width() * 0.05) + "px");
				$('body').height($('body')[0].clientHeight);
			}
			initWindow();
			$(window).resize(function() {
				initWindow();
			});
		});
	</script>
	<script type="text/javascript">
		$('#main')
				.scroll(
						function() {
							var scrollTop = $(this).scrollTop();
							var scrollHeight = $(this).height();
							var totalHeight = $('#scrollMenu').height();
							if (scrollTop + scrollHeight >= totalHeight) {
								var $elems = $('<div class="item"><div><img src="img/3.jpg"></div><div class="description">你妹的</div> <div class="price"></div></div><div class="item"><div><img src="img/3.jpg"></div><div class="description">你妹的</div> <div class="price"></div></div>');
								$container.imagesLoaded(function() {
									$container.append($elems);
									$elems.height($elems.width() * 1.2);
									$elems.css("font-size",
											($elems.width() / 13) + "px");
									$elems.find("img").height(
											$(".item").height() * 0.7);
									$container.masonry('appended', $elems);
								});
							}
						});
		$("#button-category").click(function() {
			var hide = $("#category-list-p").attr("hide");
			if (hide == "true") {
				$("#category-list-p").animate({
					"margin-top" : $("#top").height() + "px"
				}, 200);
				$("#category-list-p").attr("hide", "false");
			} else {
				$("#category-list-p").animate({
					"margin-top" : (-1 * $("#category-list").height()) + "px"
				}, 200);
				$("#category-list-p").attr("hide", "true");
			}
		});
		$("#button-search").click(function() {
		});
		$("#button-cart").click(function() {
		});
		$("#searchText").blur(function() {
			var value = $("#searchText").val();
			if (value != '') {
				window.location.href = "https://www.baidu.com";
			}
		});
		$("#searchText").focus(function() {

		});

		$('#searchText').bind('keypress', function(event) {
			if (event.keyCode == "13") {
				alert('你输入的内容为：' + $('#searchText').val());
			}
		});
		var $container = $('#container');
		$container.imagesLoaded(function() {
			$container.masonry({
				transitionDuration : "2s",
				itemSelector : '.item',
				hiddenStyle : {
					opacity : 0
				},
				visibleStyle : {
					opacity : 1
				}
			});
		});
		new Swiper('.banner', {
			pagination : '.swiper-pagination',
			paginationClickable : false,
			autoplay : 3000,
			loop : true
		});
	</script>
</body>
</html>