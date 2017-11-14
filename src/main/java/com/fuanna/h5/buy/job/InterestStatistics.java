package com.fuanna.h5.buy.job;

import org.apache.log4j.Logger;

import com.fuanna.h5.buy.job.base.AbstractJob;


public class InterestStatistics extends AbstractJob{
	
	private Logger logger = Logger.getLogger(InterestStatistics.class);
	
	private static final String CONFIG_NAME = "interest_job_cron";
	
	public InterestStatistics() {
		super(CONFIG_NAME);
	}
	
	@Override
	protected void doJob(Object param) {
		try {
//			iDebtMatchService.interestStatistics();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public String cronExpression() {
		return null;
//		return BaseConfig.get(CONFIG_NAME);
	}
}
