<<<<<<< HEAD
=======
package ThirdManda;

>>>>>>> 70cde107b71f1c8472d62c142c23a1fb01010b96
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.pbcast.NAKACK;

public class ClockSynchonizer extends ReceiverAdapter {
    JChannel channel;
    Address Master;
    boolean MasterMode;
    Date lastRequest;
    DriftingClock clock;
    
    
    public ClockSynchonizer(DriftingClock c, JChannel ch)
    {
    	clock = c;
    	channel = ch;
    }    
	
    public void start() { 
    	// initially assume we're the first client and should act as master.
        MasterMode = true;
    	// Request if there are any other masters.          
        send("Master Request", "");        
    }    
    
    public void send(String h, String m) { send(h, m, null); }
    
    // helper method for sending messages.
    public void send(String h, String m, Address dst) {
    	try {
    		//System.out.println("SC Sending: Header: " + h + " Message: " + m);
	        MessageBody mb = new MessageBody();
	        mb.Header = h;
	        mb.Message = m;
	        mb.Sender = channel.getAddressAsString();
	        Message msg=new Message(dst, null, mb);
	        channel.send(msg);
	    }
	    catch(Exception e) {
			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();	
	    }
    }
    
    
<<<<<<< HEAD
    // 
=======
    
>>>>>>> 70cde107b71f1c8472d62c142c23a1fb01010b96
    public void receive(Message msg) {
    	MessageBody mb = (MessageBody)msg.getObject();
    	if ((mb.Header.equals("Master Request")) && (!mb.Sender.equals(channel.getAddressAsString())) && MasterMode)
    	{             
    		// If we get a Master Request and we are the master, then send a Master Reply.
    		
    		//System.out.println("Received Master Request");
    		 send("Master Reply", "", msg.getSrc());
    	}
    	if ((mb.Header.equals("Master Reply")) && (!mb.Sender.equals(channel.getAddressAsString())))
    	{
    		// If we get a master reply, we know we are not the first client, and can set the other client as our master.
    		
    		//System.out.println("Received Master Reply");
    		 MasterMode = false;
    		 Master = msg.getSrc();
    		 // Hint: The following line means that the slaves do a regular update of their clock, 
    		 // if you choose to implement Berkleys algorithm, the master should start the update process, so this line
    		 // should be somewhere else.
    		 StartUpdateTask(5);  
    	} 	
    	if ((mb.Header.equals("Time Request")) && MasterMode)
    	{
    		// If we get a time request, then answer with out local time.
    		//System.out.println("Time Request");
    		String s = String.valueOf(clock.getTime().getTime());
    		send("Time Reply", s, msg.getSrc());
    		
    	}
    	if (mb.Header.equals("Time Reply"))
    	{    		
    		//System.out.println("Time Reply");
    		
    		// TODO: Implement this.
    		int dif = 0;
    		
    		synchronized(clock)
    		{
    			clock.Adjust(dif);
    		}
    	}    	
    	
    	
    }
    
    public void UpdateClock()
    {
		// TODO: Implement this.    	
    }    
    
    Timer timer;

    // This method repeats a task at a regular interval, for example requesting the time from a master server,
    // or the master requesting local times from its slaves.
    public void StartUpdateTask(int seconds) {
      timer = new Timer();
      timer.scheduleAtFixedRate(new UpdateTask(this), 1000, seconds * 1000);
    }

    class UpdateTask extends TimerTask {
    	
    	public ClockSynchonizer parent;
    	public UpdateTask(ClockSynchonizer p)
    	{parent = p;}

    	
      public void run() {
    	  parent.UpdateClock();    	   
      }
    }    
    

}
