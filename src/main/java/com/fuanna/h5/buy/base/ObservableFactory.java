package com.fuanna.h5.buy.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.fuanna.h5.buy.job.base.QuartzJobManager;

public class ObservableFactory{

	private static Logger logger = Logger.getLogger(QuartzJobManager.class);

	private static Object _lock = new Object();
	
	private static ObservableFactory observableFactory;
	
	private final Map<String, ObservableBean> observables = new HashMap<String, ObservableBean>();
	
	public static ObservableFactory getInstance() {
		try {
			if (observableFactory == null) {
				synchronized (_lock) {
					if (observableFactory == null) {
						observableFactory = new ObservableFactory();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return observableFactory;
	}
	
	public synchronized ObservableBean getObservable(String name) {
		ObservableBean observableBean = observables.get(name);
		if (observableBean == null) {
			observableBean = new ObservableBean();
			observables.put(name, observableBean);
		}
		return observableBean;
	}
	
	public class ObservableBean extends Observable {
		
		public void changed(){
			setChanged();
		}
	}
}
