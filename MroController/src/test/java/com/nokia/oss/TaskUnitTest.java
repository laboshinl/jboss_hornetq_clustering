package com.nokia.oss;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.nokia.oss.task.Task;
import com.nokia.oss.task.TaskUnit;

public class TaskUnitTest {

	@Test
	public void testTaskSet(){
		

		Task testtask = new Task();
		TaskUnit taskunit = new TaskUnit();
		taskunit.setRetrycount(3);
		taskunit.setTaskEpectedTime(1000,TimeUnit.MILLISECONDS);
		taskunit.setTaskid((UUID.randomUUID().getMostSignificantBits()));
		taskunit.setTaskStarttime(System.currentTimeMillis());
		Long timeset = taskunit.getTaskExpectedTime(TimeUnit.SECONDS);
		Assert.assertTrue(timeset==1);
	}
	
}
