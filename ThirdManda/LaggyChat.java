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
    	boolean bIsBigger = false;
    	boolean foundStrictlySmaller = false;
    	
    	for(String s : a.keySet()){
    		if(b.get(s) < a.get(s)){
    			bIsBigger = true;
    		}
    		else if(b.get(s) > a.get(s)){
    			foundStrictlySmaller = true;
    		}
    	}
    	
    	if(!bIsBigger & foundStrictlySmaller) return -1;
    	if(bIsBigger) return 1;
    	return 0;
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
    	// TODO: When is a message ready?
    	
    	return false;
    }
    
    
    public void newMessage(MessageBody m)
    {    
    	if(VectorClock.keySet().contains(m.Sender)){
    		VectorClock.put(m.Sender, 0);
    		UpdateClock(m.VectorTimestamp);
    	} 
    	
    	if(!MessageReady(m)){
    		waitingMessages.add(m);
    	}
    	
    	for(MessageBody mb : waitingMessages){
    		if(MessageReady(mb)){
    			System.out.println(mb.Sender+ ":" + mb.Message);
    	    	UpdateClock(mb.VectorTimestamp);
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