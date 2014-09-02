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
	 * Set the id of hte message Sender
	 * @param id
	 */
	public void setMessageSenderId(String id);
	
	/**
	 * Get the Message Sender Id
	 * @return
	 */
	public String getMessageSenderId();
	
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
