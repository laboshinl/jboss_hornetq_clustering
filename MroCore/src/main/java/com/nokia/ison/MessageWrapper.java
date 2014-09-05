package com.nokia.ison;

import java.io.Serializable;


public class MessageWrapper implements MessageWrapperItf, Serializable {

	private static final long serialVersionUID = -5007295409908444658L;
	private String payload;
	private String senderid;
	private String targetid;
	private MessageType messageType;
	private CommandType commandType;
	private Long taskid;

	@Override
	public String getPayload() {
		return payload;
	}

	@Override
	public void setPayload(String payload) {
		this.payload = payload;

	}

	@Override
	public void setMessageSenderId(String id) {
		this.senderid = id;

	}

	@Override
	public String getMessageSenderId() {

		return senderid;
	}

	@Override
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;

	}

	@Override
	public MessageType getMessageType() {

		return messageType;
	}
	
	@Override
	public String toJSON() {
	
		return null;
	}

	@Override
	public CommandType getCommandType() {
		
		return commandType;
	}
	
	@Override
	public void setCommandType(CommandType type) {
		this.commandType=type;
		
	}

	@Override
	public String getMessageTargetId() {
		return targetid;
	}

	/**
	 * For sending message to a particular Controller
	 */
	@Override
	public void setMessageTargetId(String id) {
		this.targetid =id;
		
	}

	@Override
	public void setTaskId(Long id) {
		this.taskid =id;
		
	}

	@Override
	public Long getTaskId() {
		return taskid;
	}
}
