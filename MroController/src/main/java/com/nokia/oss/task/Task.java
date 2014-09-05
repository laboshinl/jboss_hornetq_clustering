package com.nokia.oss.task;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Lock;
import javax.ejb.LockType;

import com.nokia.ison.MessageWrapperItf;

@Lock(LockType.WRITE)
public class Task {

	//The list of task unit for the task
	private Map<Long,TaskUnit> taskUnitMap;
	private Map<Long,MessageWrapperItf> resultAggregation;
	
	public Task(){
		taskUnitMap= new HashMap<Long, TaskUnit>();
		resultAggregation= new HashMap<Long, MessageWrapperItf>();
		
	}
	

	/**
	 * Collect the results of the task
	 * @param resultmessage
	 */
	public void aggregateTaskResult(MessageWrapperItf resultmessage){

		if(!taskUnitMap.containsKey(resultmessage.getTaskId())){
			System.out.println("ERROR: No Corresponging Task , OR Task RESEND mode");
			return;
		}
		if(!resultAggregation.containsKey(resultmessage.getTaskId())){
			resultAggregation.put(resultmessage.getTaskId(), resultmessage);
			System.out.println("Task Result added -TaskId=" +resultmessage.getTaskId());
			TaskUnit task = taskUnitMap.remove(resultmessage.getTaskId());
			System.out.println("Task with TaskId Received and removed from monitoring Taskid="+ task.getTaskId());
		}
		else{
			System.out.println("ERROR:Tasks Result has come  twice!!-TaskId=" +resultmessage.getTaskId());
		}

	}
	
	public void addTaskUnit(TaskUnit taskUnit){
		
		if(taskUnitMap.containsKey(taskUnit.getTaskId())){
			System.out.println("ERROR:Task with same Id present");
			return;
		}
		taskUnitMap.put(taskUnit.getTaskId(), taskUnit);
		
	}

	public Map<Long,TaskUnit> getUnfinishedTaskUnit(){
		
		return taskUnitMap;
		
	}

}
