package FirstManda;
import java.net.*;

public class UDPReceiver{
  public static void main(String args[ ]) throws Exception{ 
		int receiversPort = 1234;		   
		System.out.println("SERVER: Creating DatagramSocket...");
		DatagramSocket datagramSocket = new DatagramSocket( receiversPort );
		System.out.println("SERVER: Done.");
		byte[ ] buffer = new byte[1000]; // max 65.536 bytes
		System.out.println("SERVER: Creating DatagramPacket...");
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
		System.out.println("SERVER: Done.");
		int i = 0;
		while(true) {
			System.out.println("SERVER: Trying to receive data...");
			datagramSocket.receive( datagramPacket );
			System.out.println("SERVER: Done.");
			String message = new String( datagramPacket.getData() );
			message = message.trim(); //removes white spaces
			System.out.println("SERVER: Data received:");
			System.out.println( message );
			i++;
			System.out.println(i);
			if(message.equals("terminate"))
				break;
		}
		System.out.println("SERVER: TOTAL NO OF PACKAGES RECEIVED:");
		System.out.println(i);
  }
}