package SecondManda;

import javax.jms.*;
import javax.naming.Context; 
import javax.naming.InitialContext; 
import java.util.Scanner;


public class Producer {

	public static void main(String[] args) {
		Producer p = new Producer();
		Scanner in = new Scanner(System.in);

		String c;
		do
		{
			System.out.println("Enter message:");
			c = in.nextLine();
			System.out.println("Sending: " + c);
			if (c != "") p.raise(c);
		} while (c != "");
	}

	public void raise(String m)
	{
		try
		{
			Context ctx = new InitialContext();
			TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("TCF");
			Topic t = (Topic) ctx.lookup("Messages");
			TopicConnection conn = tcf.createTopicConnection();
			TopicSession s = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicPublisher tp = s.createPublisher(t);
			TextMessage message = s.createTextMessage();
			message.setText(m);
			message.setStringProperty("Sender", "Alice");
			tp.publish(message);
			conn.close();		
		}
		catch (Exception e)
		{		
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();			
		}		
	}	
}
