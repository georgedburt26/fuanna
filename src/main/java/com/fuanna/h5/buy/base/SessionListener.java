package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.fuanna.h5.buy.model.Admin;

public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener{

	private static final Logger logger = Logger
			.getLogger(SessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin != null) {
			BaseConfig.removeSessionMap(admin.getCompanyId(), session.getId());
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		Object object = event.getValue();
		if(object.getClass() == Admin.class) {
			Admin admin = (Admin) object;
			HttpSession session = event.getSession();
			BaseConfig.putSessionMap(admin.getCompanyId(), session.getId(), session);
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}

}
