package com.nokia.ison.dispatch;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.nokia.ison.MessageWrapperItf;

/**
 * Handled sending the message via JMS
 * 
 * @author acp
 * 
 */

public class SendtoExecutor implements SendMessageItf {

	public SendtoExecutor() {

	}

	/*
	 * @Resource(mappedName = "java:/ConnectionFactory") private
	 * ConnectionFactory connectionFactory;
	 * 
	 * @Resource(mappedName = "java:/queue/test") private Queue queue;
	 * 
	 * @Resource(mappedName = "java:/topic/test") private Topic topic;
	 */

	@Override
	public void sendMessage(MessageWrapperItf message) {

		System.out.println("Going to Send the Messsae");

		Context context = null;
		Connection connection = null;
		try {

			System.out
					.println("#----Controller Entering into the Method to SendMessage----#");

			context = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) context
					.lookup("/ConnectionFactory");
			Queue destination = (Queue) context.lookup("queue/MROQueue");

			connection = connectionFactory
					.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);
			connection.start();

			Message objmsg = session.createObjectMessage(message);

			producer.send(objmsg);
			System.out.println("#---Controller Out Of SendMesssae-----#");

		} catch (JMSException e) {
			e.printStackTrace();

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				context.close();
				if (connection != null) {
					connection.close();
				}
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				e.printStackTrace();
			}

		}

	}

/*Not needed
 * 	protected static InitialContext getInitialContext() throws NamingException {
		Hashtable env = new Hashtable();

		final String STD_IP = "10.254.60.74";
		final String STD_PORT = "4447";
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "remote://" + STD_IP + ":" + STD_PORT);
		env.put(Context.SECURITY_PRINCIPAL, "appuser");
		// Add this user also to
		// ..domain\configuration\application-roles.properties
		// as guest; and in domain.xml <security-setting match="#">
		// <permission type="send" roles="guest"/>
		env.put(Context.SECURITY_CREDENTIALS, "123123");
		return new InitialContext(env);
	}*/
}
