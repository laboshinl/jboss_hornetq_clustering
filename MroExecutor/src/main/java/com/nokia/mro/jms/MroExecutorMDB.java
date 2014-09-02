package com.nokia.mro.jms;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import com.google.gson.Gson;
import com.nokia.ison.JsonSourceDestDN;
import com.nokia.ison.MessageWrapperItf;

/**
 *  A Prototype class written with minimal POM dependncy to check MDB in JBOSS 
 *  TODO - Refactor for correct use
 * @author acp
 *
 */

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/MROQueue")	
		}
	)
public class MroExecutorMDB implements MessageListener{

	 private final static Logger LOGGER = Logger.getLogger(MroExecutorMDB.class.toString());
	 
	@Override
	public void onMessage(Message rcvMessage) {
		ObjectMessage msg = null;
        try {
            if (rcvMessage instanceof ObjectMessage) {
                msg = (ObjectMessage) rcvMessage;
                MessageWrapperItf objmsg =(MessageWrapperItf) msg.getObject();
                String payload =objmsg.getPayload();
                JsonSourceDestDN output=new Gson().fromJson(payload, JsonSourceDestDN.class);
				
                System.out.println("----Received Message from Queue:Command Type= "
                		+ objmsg.getCommandType() +
                		" MessageType= "+ objmsg.getMessageType() +
                		" SenderId= "+  objmsg.getMessageSenderId()+
                		" Number of DN's Present= " + output.sourceDNList.size() );
                
                (new NRSorter()).handleDnsFromScope(output.sourceDNList);
                   		
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
