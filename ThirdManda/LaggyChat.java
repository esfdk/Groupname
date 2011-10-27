package ThirdManda;

import org.jgroups.JChannel;

import org.jgroups.Message;
import org.jgroups.protocols.UDP;

import java.io.*;
import java.util.*;

public class LaggyChat {
	JChannel channel;
	String user_name=System.getProperty("user.name", "n/a");
	final List<String> state=new LinkedList<String>();

	// Local vector clock.
	HashMap<String, Integer> VectorClock = new HashMap<String, Integer>();
	HashMap<String, Integer> PrintedClock = new HashMap<String, Integer>();

	// Messages that are yet to be displayed
	LinkedList<MessageBody> waitingMessages = new LinkedList<MessageBody>();

	// Method for comparing two vector timestamps
	// We do not use this.
	int CompareTimestamps(HashMap<String, Integer> a, HashMap<String, Integer> b)
	{
		//Implementation inspired by http://tud-in4150-fp-ass2b.googlecode.com/svn/trunk/src/in4150/mutex/VectorClock.java

		// Initially we assume it is all possible things.
		boolean Equal	= true;
		boolean Greater = true;
		boolean Smaller = true;

		// Go over all elements in Clock a.
		for (String key : a.keySet())
		{
			// Compare if also present in Clock b.
			if (b.containsKey(key))
			{
				// If there is a difference, it can never be equal.
				// Greater / smaller depends on the difference.
				if (a.get(key) < b.get(key))
				{
					Equal	= false;
					Greater = false;
				}
				if (a.get(key) > b.get(key))
				{
					Equal	= false;
					Smaller = false;
				}
			}

			// Else assume zero (default value is 0).
			else if (a.get(key) != 0)
			{
				Equal	= false;
				Smaller = false;
			}
		}

		// Go over all elements in Clock b.
		for (String key : b.keySet())
		{
			// Only elements we have not found in clock a still need to be checked.
			if (!a.containsKey(key) && (b.get(key) != 0))
			{
				Equal	= false;
				Greater = false;
			}
		}

		// Return based on determined information.
		if (Equal)
		{
			return 0;
		}
		else if (Greater && !Smaller)
		{
			return 1;
		}
		else if (Smaller && !Greater)
		{
			return -1;
		}
		else
		{
			return 2;
		}
	}    

	//Method for updating the local clock based on some vector timestamp
	public void UpdateClock(HashMap<String, Integer> t)
	{	
		for(String s : t.keySet()){
			int maxValue = Math.max(t.get(s), VectorClock.get(s));
			VectorClock.put(s, maxValue);
		}
	}

	// Method for testing if a message is ready to be printed.
	// Think about when a message can be printed for the order of messages to make sense:
	// what is required of the vectortimestamp on the message and the local clock?
	public boolean MessageReady(MessageBody m)
	{    	
		if(VectorClock.get(m.Sender) == PrintedClock.get(m.Sender) + 1){
			return true;
		}
		return false;
	}


	public void newMessage(MessageBody m)
	{    

		if(!VectorClock.keySet().contains(m.Sender)){
			VectorClock.put(m.Sender, m.VectorTimestamp.get(m.Sender));
		}

		if(!PrintedClock.keySet().contains(m.Sender)){
			PrintedClock.put(m.Sender, 0);
		} 

		UpdateClock(m.VectorTimestamp);
		waitingMessages.add(m);

		for(MessageBody mb : waitingMessages){
			if(MessageReady(mb)){
				PrintedClock.put(mb.Sender, PrintedClock.get(mb.Sender) + 1);
				System.out.println(mb.Sender+ ":" + mb.Message);
				waitingMessages.remove(mb);
			}
		}
	}


	private void start() throws Exception {
		channel=new JChannel();
		channel.setReceiver(new LaggyReceiver(this));
		channel.connect("ChatCluster");
		channel.getState(null, 10000);
		VectorClock.put(channel.getAddressAsString(), 0); // Initializing own logical time as 0.
		eventLoop();
		channel.close();
	}

	private void eventLoop() {
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {            	
				System.out.print("> "); System.out.flush();
				String line=in.readLine().toLowerCase();
				if(line.startsWith("quit") || line.startsWith("exit")) {
					break;
				}
				line="[" + user_name + "] " + line;
				MessageBody mb = new MessageBody();
				mb.Message = line;
				mb.Sender = channel.getAddressAsString();

				String addr = mb.Sender;
				int clock = VectorClock.get(addr) + 1;               
				VectorClock.put(addr, clock);

				mb.VectorTimestamp = VectorClock; 

				Message msg=new Message(null, null, mb);
				channel.send(msg);
			}
			catch(Exception e) {
				System.out.println("Exception:" + e.getMessage());
				e.printStackTrace();	
			}
		}
	}


	public static void main(String[] args) throws Exception {
		new LaggyChat().start();
	}
}