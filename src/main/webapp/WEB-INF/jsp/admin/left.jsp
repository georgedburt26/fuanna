<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<div class="tpl-left-nav tpl-left-nav-hover">
	<div class="tpl-left-nav-title">功能列表</div>
	<div class="tpl-left-nav-list">
		<ul class="tpl-left-nav-menu">
			<c:forEach var="resource" items="${leftResources}" varStatus="status">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);"
					onclick="pageContent('${resource.url}')"
					class="nav-link tpl-left-nav-link-list">
						<i class="${resource.icon}"></i> <span>${resource.name}</span> <c:if
							test="${not empty resource.resources}">
							<i
								class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right"></i>
						</c:if> <!--<i class="tpl-left-nav-content tpl-badge-danger"> 12 </i>-->
				</a> <c:if test="${not empty resource.resources}">
						<ul class="tpl-left-nav-sub-menu">
							<li><c:forEach var="resource" items="${resource.resources}"
									varStatus="status">
									<a href="javascript:void(0);"
										onclick="pageContent('${resource.url}')"> <i
										class="am-icon-angle-right"></i> <span>${resource.name}</span>
										<!--<i
										class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>-->
									</a>
								</c:forEach></li>
						</ul>
					</c:if></li>
			</c:forEach>
		</ul>
	</div>
	<div id="notice">
		<span class="am-icon-bookmark"
			style="font-size: 20px; position: relative; text-shadow: none; font-weight: 300; top: 2px; margin-left: 1px; margin-right: 6px; color: #a7bdcd;"></span><span>公告</span>
	</div>
	<div id="notice_content">
		<c:choose>
			<c:when test="${notice == null}">  
            暂无      
   </c:when>
			<c:otherwise>
				<p id="notice_content_value">${notice.content}</p>
				<p align="right" id="notice_publishTime_value">${notice.publishTime}</p>
			</c:otherwise>
		</c:choose>
	</div>
	<span id="btnResources" style="display:none">${btnResources}</span>
</div>