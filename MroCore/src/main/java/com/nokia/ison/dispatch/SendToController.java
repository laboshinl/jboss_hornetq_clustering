package com.nokia.ison.dispatch;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.nokia.ison.MessageWrapperItf;

public class SendToController implements SendMessageItf {

	public SendToController() {

	}
	
	@Override
	public void sendMessage(MessageWrapperItf message) {

		System.out.println("Going to Send the Messsae");

		Context context=null;
		Connection connection=null;
		try {
			context =  new InitialContext();
			System.out.println("#----MROExecutor Entering into the Method to SendMessage To MRO Topic----#");

			ConnectionFactory connectionFactory = (ConnectionFactory) context
			.lookup("/ConnectionFactory");
			
			Topic destination = (Topic)context.lookup("topic/MROTopic");
					
			connection = connectionFactory.createConnection();
						
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);
			connection.start();

			Message objmsg = session.createObjectMessage(message);
			
			producer.send(objmsg);
			System.out.println("#---MROExecutor Out Of SendMesssae To MRO Topic-----#");

		} catch (JMSException e) {
			e.printStackTrace();

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				context.close();
				if(connection!=null){
				connection.close();}
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
					e.printStackTrace();
			}
			
		}

	}

}
