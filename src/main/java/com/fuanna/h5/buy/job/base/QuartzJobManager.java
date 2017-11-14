package com.fuanna.h5.buy.job.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;


public class QuartzJobManager {

	private static Logger logger = Logger.getLogger(QuartzJobManager.class);

	private static Object _lock = new Object();

	private static QuartzJobManager quartzJobManager;

	private static Scheduler scheduler;
	
	private final Map<String, IJob> jobs = new HashMap<String, IJob>();

	public QuartzJobManager () {
		
	}
	
	public static QuartzJobManager getInstance() {
		try {
			if (quartzJobManager == null) {
				synchronized (_lock) {
					if (quartzJobManager == null) {
						scheduler = new StdSchedulerFactory().getScheduler();
						scheduler.start();
						quartzJobManager = new QuartzJobManager();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return quartzJobManager;
	}

	public void addJob(IJob job) {
		try {
			String jobName = job.jobName();
			String groupName = job.groupName();
			String cronExpression = job.cronExpression();
			String triggerName = job.triggerName();
	        MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();  
	        mjdfb.setName(jobName);// 设置Job名称  
	        mjdfb.setTargetObject(job);// 设置任务类  
	        mjdfb.setTargetMethod(IJob.DO_METHOD); // 设置任务方法  
	        mjdfb.setConcurrent(false); // 设置是否并发启动任务  
	        mjdfb.afterPropertiesSet();  
			JobDetail jobDetail = (JobDetail) mjdfb.getObject();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
			scheduler.scheduleJob(jobDetail, trigger);
			jobs.put(groupName + "." + jobName, job);
			logger.info("添加定时任务成功" + groupName + "." + jobName);
		} catch (Exception e) {
			logger.error("添加定时任务失败" + e.getMessage(), e);
		}
	}
	
	public Map<String, IJob> getJobMap() {
		return jobs;
	}
	
	public IJob getJob(String groupName, String jobName) {
		return jobs.get(groupName + "." + jobName);
	}

	public void pauseJob(String jobName, String groupName) {
		try {
			JobKey jobKey = JobKey.jobKey(jobName, groupName);
			scheduler.pauseJob(jobKey);
			logger.info("暂停定时任务成功" + groupName + "." + jobName);
		} catch (SchedulerException e) {
			logger.error("暂停定时任务失败" + e.getMessage(), e);
		}
	}

	public void resumeJob(String jobName, String groupName) {
		try {
			JobKey jobKey = JobKey.jobKey(jobName, groupName);
			scheduler.resumeJob(jobKey);
			logger.info("恢复定时任务成功" + groupName + "." + jobName);
		} catch (SchedulerException e) {
			logger.error("恢复定时任务失败" + e.getMessage(), e);
		}
	}

	public void removeJob(String jobName, String groupName) {
		try {
			JobKey jobKey = JobKey.jobKey(jobName, groupName);
			scheduler.deleteJob(jobKey);
			jobs.remove(groupName + "." + jobName);
			logger.info("删除定时任务成功" + groupName + "." + jobName);
		} catch (SchedulerException e) {
			logger.error("删除定时任务失败" + e.getMessage(), e);
		}
	}

	public void modifyJobTime(String triggerName, String groupName, String cronExpression) {
		try {
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, groupName);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
		logger.info("修改定时任务时间成功" + groupName + "." + triggerName);
		} catch (SchedulerException e) {
			logger.error("修改定时任务时间失败" + e.getMessage(), e);
		}
	}

}
