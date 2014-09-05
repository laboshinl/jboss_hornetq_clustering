package com.nokia.mro.jms;

import java.util.List;

import com.nokia.ison.CommandType;
import com.nokia.ison.MessageType;
import com.nokia.ison.MessageWrapper;
import com.nokia.ison.dispatch.SendMessageItf;
import com.nokia.ison.dispatch.SendToController;


public class NRSorter {
	
	public NRSorter(){
		
	}
	
	/**
	 * For a particular Scope source do MRO algortihm till NR ranking
	 * and inform the MRoController via JMS
	 * @param targetid 
	 * @param objmsg
	 * @return
	 */
	protected void handleDnsFromScope(List<String> dnlist, String targetid){
		
		//First get the TargetDN List
		
		SendMessageItf messageSender = new SendToController();
		
		MessageWrapper messageWrapper= new MessageWrapper();
		messageWrapper.setMessageSenderId("NRSorter");
		messageWrapper.setMessageType(MessageType.COMMAND);
		messageWrapper.setCommandType(CommandType.GET_TARGET);
		messageWrapper.setMessageTargetId(targetid);
		messageSender.sendMessage(messageWrapper);

		//wait for response
	
		return ;
	}

}
