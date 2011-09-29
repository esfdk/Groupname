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
			TopicConnectionFactory tcf = (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory");
			Topic t = (Topic) ctx.lookup("Messages");
			
			TopicConnection conn = (TopicConnection) tcf.createConnection();
			TopicSession s = (TopicSession) conn.createSession(true, 0);
			MessageProducer mp = s.createProducer(t);
			TextMessage message = s.createTextMessage();
			message.setText(m);
			mp.send(message);
			conn.close();
			
			// TODO: Lookup the TopicConnectionFactory
			// TODO: Lookup the Topic
			// TODO: Create a new Topic Connection
			// TODO: Start a new Topic Session
			// TODO: Create the publisher
			// TODO: Create a message
			// TODO: Set the text of the message
			// TODO: Send the message
			// TODO: Close the topic connection			
		}
		catch (Exception e)
		{		
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();			
		}		
	}	
}
