package ThirdManda;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class LaggyReceiver extends ReceiverAdapter {
	
	private class Pair
	{
		public int Position;
		public MessageBody Message;
		
		public Pair(int p, MessageBody m)
		{
			Position = p;
			Message = m;
		}
		

	}
	
	private LaggyChat parent;
	private List<Pair> backlog;
	private Random generator;
	
	public LaggyReceiver(LaggyChat u)
	{
		parent = u;
		generator = new Random();
		backlog = new LinkedList<Pair>();
	}	
	
    public void receive(Message msg) {
    	MessageBody mb = (MessageBody)msg.getObject();
    	
        int r = generator.nextInt(3);
        backlog.add(new Pair(r, mb));
        
        RefreshBacklog();
         
    }
    
    
    public void RefreshBacklog()
    {
    
    	List<Pair> done = new LinkedList<Pair>();
    	for (Pair x : backlog)
    	{
    		if (x.Position == 0)
    		{
    			parent.newMessage(x.Message);
    			done.add(x);
    		}
    		else
    			x.Position--;
    		
    	}   	
    	for (Pair x : done)
    		backlog.remove(x);   	
   
    }
    

    
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }    

}
