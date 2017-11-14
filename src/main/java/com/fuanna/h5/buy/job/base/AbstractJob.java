package com.fuanna.h5.buy.job.base;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;

import com.fuanna.h5.buy.base.ObservableFactory;

public abstract class AbstractJob implements IJob,Observer{

	private static Logger logger = Logger.getLogger(AbstractJob.class);

	private String jobName;

	private String groupName;

	private String triggerName;
	
	private String configName;
	
	public AbstractJob() {
		
	}
	
	public AbstractJob(String configName) {
		jobName();
		this.configName = configName;
		regConfigObserver();
	}

	public AbstractJob(String jobName, String triggerName, String groupName, String configName) {
		this.jobName = jobName;
		this.triggerName = triggerName;
		this.groupName = groupName;
		this.configName = configName;
		regConfigObserver();
	}

	@Override
	public void execute(Object param) {
		try {
			beforeJob(param);
			doJob(param);
			afterJob(param);
		} catch (Exception e) {
			causeException(e);
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		String value = ((String[]) arg)[1];
		QuartzJobManager.getInstance().modifyJobTime(triggerName, groupName, value);
	}
	
	@Override
	public String groupName() {
		groupName = groupName == null ? Scheduler.DEFAULT_GROUP : groupName;
		return groupName;
	}

	@Override
	public String jobName() {
		jobName = jobName == null ? this.getClass().getSimpleName() + "Job" : jobName;
		return jobName;
	}

	@Override
	public String triggerName() {
		triggerName = triggerName == null ? this.getClass().getSimpleName() + "Trigger" : triggerName;
		return triggerName;
	}
	
	public String configName() {
		return configName;
	}
	
	private void regConfigObserver() {
		if (!StringUtils.isBlank(configName)) {
		ObservableFactory.getInstance().getObservable(configName).addObserver(this);
		logger.info(jobName + "注册监听配置" + configName);
		}
	}

	protected void causeException(Exception e) {
		
	}
	
	protected void beforeJob(Object param){

	}
	
	protected void afterJob(Object param){
		
	}
	
	protected abstract void doJob(Object param);
}
