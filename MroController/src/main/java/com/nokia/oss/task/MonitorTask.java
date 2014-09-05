package com.nokia.oss.task;

import java.util.HashMap;
import java.util.Map;

public class MonitorTask {
	
	private Map<Long, TaskUnit> taskMap;
	
	public MonitorTask(){
		taskMap = new HashMap<Long,TaskUnit>();
	}
	
	public void setTask(TaskUnit task){
		if(taskMap.containsKey(task.getTaskId())){
			taskMap.remove(task.getTaskId());
		}
		taskMap.put(task.getTaskId(), task);
	}
	public TaskUnit getTask(Long taskid){
		
		return taskMap.get(taskid);
		
	}
	
	

}
