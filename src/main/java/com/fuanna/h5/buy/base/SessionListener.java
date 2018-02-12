package com.fuanna.h5.buy.base;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener{

	private static final Logger logger = Logger
			.getLogger(SessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		logger.info("session被创建了");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
	}

}
