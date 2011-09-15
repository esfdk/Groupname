package FirstManda;

import java.net.*;

public class UdpSender {
  public static void main(String args[ ]) throws Exception{ 
		InetAddress receiversAddress = InetAddress.getByName("10.25.239.237");
		int receiversPort = 1234;		                                                 
	  DatagramSocket datagramSocket = new DatagramSocket();
		String messageAsString = "Hello spadser gange 1000";
		byte [ ] message = messageAsString.getBytes();
		int messageLength = messageAsString.length();
		DatagramPacket datagramPacket = new DatagramPacket(message, messageLength, receiversAddress, receiversPort);
		for(int i = 0; i < 1000; i++){
		datagramSocket.send( datagramPacket );
		}
  }
}