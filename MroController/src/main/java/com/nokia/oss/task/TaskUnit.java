package com.nokia.oss.task;

import java.util.concurrent.TimeUnit;

/**
 * As simple holder task for holding data related to a Task; These are the Task units
 * that are sent out to be executed by the MROExecutors
 * 
 * @author acp
 *
 */
public class TaskUnit {
	
	private Long TaskId;
	private Long TaskStarttime;
	private int retrycount;
	private Long  maxtimeinmillisecond;
	public Long getTaskId() {
		return TaskId;
	}
	public void setTaskid(Long taskid) {
		TaskId = taskid;
	}
	public Long getTaskStarttime() {
		return TaskStarttime;
	}
	public void setTaskStarttime(Long taskStarttime) {
		TaskStarttime = taskStarttime;
	}
	public int getRetrycount() {
		return retrycount;
	}
	public void setRetrycount(int retrycount) {
		this.retrycount = retrycount;
	}
	public Long getTaskExpectedTime(TimeUnit timeunit) {
		return  timeunit.convert(maxtimeinmillisecond, TimeUnit.MILLISECONDS);
	
	}
	public void setTaskEpectedTime(	int value, TimeUnit timeunit) {
		this.maxtimeinmillisecond = timeunit.toMillis(value);
	}

}
