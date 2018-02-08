package com.fuanna.h5.buy.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fuanna.h5.buy.base.BaseConfig;
import com.fuanna.h5.buy.model.Admin;
import com.fuanna.h5.buy.model.Resource;

public class AdminLoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI().replaceAll(request.getContextPath(), "");
		// 创建session
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin == null) {
			// 重定向
			response.sendRedirect("login.do");
			return false;
		}
		else {
			Resource resource = new Resource();
			resource.setUrl(url.substring(1, url.length()));
			if (BaseConfig.getResources().contains(resource) && !((List<Resource>)session.getAttribute("resources")).contains(resource)) {
				response.sendRedirect("login.do");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
