package SecondManda;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;


public class Consumer {

	public static void main(String[] args) {
		Consumer c = new Consumer();	
		while (true){	
			System.out.println(c.receive());
		}
	}
	
	public String receive()
	{
		try
		{
			Context ctx = new InitialContext();
			// TODO: Lookup the TopicConnectionFactory
			// TODO: Lookup the Topic
			// TODO: Create a new Topic Connection
			// TODO: Start a new Topic Session
			// TODO: Create the subscriber
			// TODO: Start the topic connection
			// TODO: Receive the message
			// TODO: Return the message text
			return null;
		}
		catch (Exception e)
		{		
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();	
			return null;
			
		}		
	}	

}
