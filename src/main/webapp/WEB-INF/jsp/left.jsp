<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/lib.jsp"%>
<div class="tpl-left-nav tpl-left-nav-hover">
	<div class="tpl-left-nav-title">功能列表</div>
	<div class="tpl-left-nav-list">
		<ul class="tpl-left-nav-menu">
			<c:forEach var="resource" items="${resources}" varStatus="status">
				<li class="tpl-left-nav-item"><a href="javascript:void(0);" onclick="pageContent('${resource.url}')"
					class="nav-link tpl-left-nav-link-list <c:if test='${status.index == 0}'>active</c:if>">
						<i class="${resource.icon}"></i> <span>${resource.name}</span> <c:if
							test="${not empty resource.resources}">
							<i
								class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right"></i>
						</c:if> <!--<i class="tpl-left-nav-content tpl-badge-danger"> 12 </i>-->
				</a> <c:if test="${not empty resource.resources}">
						<ul class="tpl-left-nav-sub-menu">

							<li><c:forEach var="resource" items="${resource.resources}"
									varStatus="status">
									<a href="javascript:void(0);" onclick="pageContent('${resource.url}')"> <i class="am-icon-angle-right"></i>
										<span>${resource.name}</span> <!--<i
										class="am-icon-star tpl-left-nav-content-ico am-fr am-margin-right"></i>-->
									</a>
								</c:forEach></li>
						</ul>
					</c:if></li>
			</c:forEach>
		</ul>
	</div>
</div>