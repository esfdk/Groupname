package ThirdManda;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.pbcast.NAKACK;
import org.jgroups.util.Util;

import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.text.DateFormat;

public class TimedChat extends ReceiverAdapter {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<String>();
    DriftingClock clock = new DriftingClock();
    // New class for helping us synchronize our clocks
    ClockSynchonizer cs;
    
    public void receive(Message msg) {
    	MessageBody mb = (MessageBody)msg.getObject();
    	if (mb.Header.equals("Chat Message"))
		{
            String line=msg.getSrc() + ": " + mb.Message;
            System.out.println(line);		
		}
    	else
    	{
    	    // Messages with other headers are meant for the clock synchronizer
    		cs.receive(msg);
    	}		
    }

    private void start() throws Exception {
        channel=new JChannel();        
        
        UDP udp = (UDP)channel.getProtocolStack().findProtocol(UDP.class);
        udp.setLogDiscardMessages(false);
        
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
        
        // start the clocksynchronizer
        cs = new ClockSynchonizer(clock, channel);
        cs.start();        
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
                line="[" + user_name + "] " + "[" +  DateFormat.getTimeInstance(DateFormat.MEDIUM).format(clock.getTime()) + "] " + line;
                
                MessageBody mb = new MessageBody();
                mb.Header = "Chat Message";
                mb.Message = line;
                mb.Sender = channel.getAddressAsString(); 
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
        new TimedChat().start();
    }
}