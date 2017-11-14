package com.fuanna.h5.buy.job.base;

public interface IJob{
	
	public static final String DO_METHOD = "execute";
	
	public void execute(Object param);

	public String groupName();
	
	public String jobName();
	
	public String cronExpression();
	
	public String triggerName();
	
}
