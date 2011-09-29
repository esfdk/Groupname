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
			System.out.println("Consumer: " + c.receive());
		}
	}

	public String receive()
	{
		try
		{
			Context ctx = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("TCF");
			Topic t = (Topic) ctx.lookup("Messages");

			TopicConnection conn = tcf.createTopicConnection();
			TopicSession s = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber ts = s.createSubscriber(t);
			conn.start();
			TextMessage message = (TextMessage) ts.receive();
			return message.getText();
		}
		catch (Exception e)
		{		
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();	
			return null;

		}		
	}	

}
