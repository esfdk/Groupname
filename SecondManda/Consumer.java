package SecondManda;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * Consumer class for receving messages from producer.
 * 
 * @author Jakob Melnyk
 * @version 1
 */
public class Consumer implements MessageListener{

	public static void main(String[] args) {
		Consumer c = new Consumer();
		c.receive();
	}

	public void receive()
	{
		try
		{
			Context ctx = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("TCF");
			Topic t = (Topic) ctx.lookup("Messages");
			TopicConnection conn = tcf.createTopicConnection();
			TopicSession s = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			//Will only receive messages from sender with name Alice
			TopicSubscriber ts = s.createSubscriber(t, "Sender = 'Alice'", false);
			conn.start();
			ts.setMessageListener(this);
		}
		catch (Exception e)
		{		
			//Do something
		}		
	}

	/**
	 * What happens when message is received.
	 */
	@Override
	public void onMessage(Message arg0) {
		try {
			TextMessage msg = (TextMessage) arg0;
			System.out.println(msg.getText());
		} catch (Exception e) {
			//Do something
		}
	}	
}