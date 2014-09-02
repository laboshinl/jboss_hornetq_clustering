package com.nokia.oss;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import com.nokia.ison.MessageWrapperItf;

/**
 *  A Prototype class written with minimal POM dependncy to check MDB in JBOSS 
 *  TODO - Refactor for correct use
 * @author acp
 *
 */

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/MROTopic")
		})
public class MroControllerMDB implements MessageListener{

	private final static Logger LOGGER = Logger.getLogger(MroControllerMDB.class.toString());

	@Override
	public void onMessage(Message rcvMessage) {
		ObjectMessage msg = null;
		try {
			if (rcvMessage instanceof ObjectMessage) {
				msg = (ObjectMessage) rcvMessage;
				MessageWrapperItf objmsg =(MessageWrapperItf) msg.getObject();
			
				System.out.println("----Received Message from Topic Command Type= "
						+ objmsg.getCommandType() +
						" MessageType= "+ objmsg.getMessageType() +
						" SenderId= "+  objmsg.getMessageSenderId());
				
			//TODO: Call MRO Controller
			} else {
				LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	private void sendReply(String text, Queue dest) {
		// TODO Auto-generated method stub

	}

}
