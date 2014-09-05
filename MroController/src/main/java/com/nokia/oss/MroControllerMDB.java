package com.nokia.oss;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;

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
						" SenderId= "+  objmsg.getMessageSenderId()+
						" TargetId=" + objmsg.getMessageTargetId());
				String targetId=objmsg.getMessageTargetId();

				String thiscontrollerID="";
				try {
					thiscontrollerID = InetAddress.getLocalHost().toString();
				} catch (UnknownHostException e) {

					e.printStackTrace();
				}
				//Check if this message is for us 
				if(targetId.equals(thiscontrollerID)){
					System.out.println("Message meant for this Conroller!! Goint to call the MROController ");
					//Going to call MROController
					try {
						final Hashtable jndiProperties = new Hashtable();
						jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
						final javax.naming.Context context = new InitialContext(jndiProperties);
						MroControllerItf  mroController =(MroControllerItf)context.lookup("java:app/MroController/MroController!com.nokia.oss.MroControllerItf");
						mroController.aggregateNRSortResults("Test Results");
						System.out.println("Out of the call to MROController ");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//TODO: Call MRO Controller
				}else{
					//This means that this MDB is running in a differnt machine and anyway ivocation of the MROController
					//without using remote will fail
					System.out.println("!!Message not meant for this controller -Controller ID=" +thiscontrollerID);

				}

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
