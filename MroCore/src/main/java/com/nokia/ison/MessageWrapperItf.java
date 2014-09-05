package com.nokia.ison;

import java.io.Serializable;

/**
 * Used for wrappign messages between MROController and MROExexcutionEngines
 * @author acp
 *
 */
public interface MessageWrapperItf extends Serializable{
	
	/**
	 * Get the message Payload
	 * @return
	 */
	public String getPayload();
	/**
	 * Set the message payload
	 * @param payload
	 */
	public void setPayload(String payload);
	
	/**
	 * Set the id of the message Sender
	 * @param id
	 */
	public void setMessageSenderId(String id);
	
	/**
	 * Get the Message Target Id
	 * @return
	 */
	public String getMessageTargetId();
	
	/**
	 * Set the id of the message Target Id
	 * @param id
	 */
	public void setMessageTargetId(String id);
	
	/**
	 * Get the Message Sender Id
	 * @return
	 */
	public String getMessageSenderId();
	
	
	/**
	 * Set the id of the Task in case it is isa Task message
	 * @param id
	 */
	public void setTaskId(Long id);
	
	/**
	 * Get the Message Sender Id
	 * @return
	 */
	public Long getTaskId();
	
	
	/**
	 * Sets the message type
	 * @param messageType
	 */
	public void setMessageType(MessageType messageType);

	/**
	 * Get the message type
	 * @return
	 */
	public MessageType getMessageType();
	
	/**
	 * Convert to JSON
	 * @return
	 */
	public String toJSON();
	
	/**
	 * Set the Type of command
	 * @param type
	 */
	public void setCommandType(CommandType type);
	
	/**
	 * Get the command 
	 * @return
	 */
	public CommandType getCommandType();
}
