package com.nokia.oss;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.nokia.ison.CommandType;
import com.nokia.ison.MessageWrapper;
import com.nokia.ison.MessageWrapperItf;
import com.nokia.oss.task.Task;
import com.nokia.oss.task.TaskUnit;


public class TaskTest {

	@Test
	public void testaggregateTaskResult() throws IOException{

		Task testtask = new Task();

		MessageWrapperItf resultmessage = new MessageWrapper();

		resultmessage.setCommandType(CommandType.START_NR);
		resultmessage.setMessageSenderId(InetAddress.getLocalHost().toString());
		resultmessage.setMessageTargetId(InetAddress.getLocalHost().toString());
		resultmessage.setPayload("Some Payload");
		resultmessage.setTaskId(UUID.randomUUID().getMostSignificantBits());

		testtask.aggregateTaskResult(resultmessage);

	}

	@Test
	public void testaddTaskUnit(){

		Task testtask = new Task();

		{
			TaskUnit taskunit = new TaskUnit();
			taskunit.setRetrycount(3);
			taskunit.setTaskEpectedTime(100,TimeUnit.MILLISECONDS);
			taskunit.setTaskid((UUID.randomUUID().getMostSignificantBits()));
			taskunit.setTaskStarttime(System.currentTimeMillis());
			testtask.addTaskUnit(taskunit);
		}
		{
			TaskUnit taskunit = new TaskUnit();
			taskunit.setRetrycount(3);
			taskunit.setTaskEpectedTime(100,TimeUnit.MILLISECONDS);
			taskunit.setTaskid((UUID.randomUUID().getMostSignificantBits()));
			taskunit.setTaskStarttime(System.currentTimeMillis());
			testtask.addTaskUnit(taskunit);
		}
		{
			TaskUnit taskunit = new TaskUnit();
			taskunit.setRetrycount(3);
			taskunit.setTaskEpectedTime(100,TimeUnit.MILLISECONDS);
			taskunit.setTaskid((UUID.randomUUID().getMostSignificantBits()));
			taskunit.setTaskStarttime(System.currentTimeMillis());
			testtask.addTaskUnit(taskunit);
		}
		Assert.assertTrue(testtask.getUnfinishedTaskUnit().size() ==3);
	}

	@Test
	public void testResultHandling() throws UnknownHostException{


		Task testtask = new Task();
		TaskUnit taskunit1 = new TaskUnit();
		taskunit1.setRetrycount(3);
		taskunit1.setTaskEpectedTime(100,TimeUnit.MILLISECONDS);
		taskunit1.setTaskid((UUID.randomUUID().getMostSignificantBits()));
		taskunit1.setTaskStarttime(System.currentTimeMillis());
		testtask.addTaskUnit(taskunit1);


		TaskUnit taskunit2 = new TaskUnit();
		taskunit2.setRetrycount(3);
		taskunit2.setTaskEpectedTime(100,TimeUnit.MILLISECONDS);
		taskunit2.setTaskid((UUID.randomUUID().getMostSignificantBits()));
		taskunit2.setTaskStarttime(System.currentTimeMillis());
		testtask.addTaskUnit(taskunit2);
		
		MessageWrapperItf resultmessage1 = new MessageWrapper();

		resultmessage1.setCommandType(CommandType.START_NR);
		resultmessage1.setMessageSenderId(InetAddress.getLocalHost().toString());
		resultmessage1.setMessageTargetId(InetAddress.getLocalHost().toString());
		resultmessage1.setPayload("Some Payload");
		resultmessage1.setTaskId(taskunit1.getTaskId());

		testtask.aggregateTaskResult(resultmessage1);
		
		MessageWrapperItf resultmessage2 = new MessageWrapper();

		resultmessage2.setCommandType(CommandType.START_NR);
		resultmessage2.setMessageSenderId(InetAddress.getLocalHost().toString());
		resultmessage2.setMessageTargetId(InetAddress.getLocalHost().toString());
		resultmessage2.setPayload("Some Payload");
		resultmessage2.setTaskId(taskunit2.getTaskId());

		testtask.aggregateTaskResult(resultmessage2);
		Assert.assertEquals(testtask.getUnfinishedTaskUnit().size() ,0);
		

	}
}
