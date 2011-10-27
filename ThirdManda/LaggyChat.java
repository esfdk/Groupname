package ThirdManda;

import org.jgroups.JChannel;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.*;

public class LaggyChat {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<String>();

    // Local vector clock.
    HashMap<String, Integer> VectorClock = new HashMap<String, Integer>();

    // Messages that are yet to be displayed
    LinkedList<MessageBody> waitingMessages = new LinkedList<MessageBody>();
    
    // Method for comparing two vector timestamps
    int CompareTimestamps(HashMap<String, Integer> a, HashMap<String, Integer> b)
    {
    	// TODO: implement this
    	return 0;
    }    
    
    // method for updating the local clock based on some vector timestamp
    public void UpdateClock(HashMap<String, Integer> t)
    {
    	// TODO: implement this    
    }
    
    // Method for testing if a message is ready to be printed.
    // Think about when a message can be printed for the order of messages to make sense:
    // what is required of the vectortimestamp on the message and the local clock?
    public boolean MessageReady(MessageBody m)
    {    	
    	// TODO: implement this
    	return false;
    }
    
    
    public void newMessage(MessageBody m)
    {    
    	// TODO: Add the sender to the local clock in case it was not known before.

    	// TODO: Check if the message can be printed, if not: add it to the waiting list 
    	// TODO: Can any other waiting messages be printed?
    	// Hint: Remember to update the local clock when messages are printed.
    	// Hint: To be efficient, store waiting messages in their partial order, this way you only have to go through
    	//       the list once when you're checking if any of them can be printed when a new message arrived. 
    	System.out.println(m.Sender+ ":" + m.Message);
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
                // TODO: Add the vector timestamp to the message and increase the local clock. 
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