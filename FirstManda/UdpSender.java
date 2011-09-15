package FirstManda;
import java.net.*;

public class UDPSender {
  public static void main(String args[ ]) throws Exception{ 
		InetAddress receiversAddress = InetAddress.getByName("10.25.239.237");
		int receiversPort = 6789;
		System.out.println("CLIENT: Creating DatagramSocket...");
		DatagramSocket datagramSocket = new DatagramSocket();
		System.out.println("CLIENT: Done.");
		String messageAsString = "Hello";
		byte [ ] message = messageAsString.getBytes();
		int messageLength = messageAsString.length();
		System.out.println("CLIENT: Creating DatagramPacket");
		DatagramPacket datagramPacket = new DatagramPacket(message, messageLength, receiversAddress, receiversPort);
		System.out.println("CLIENT: Done.");
		int i = 0;
		while(i < 10000) {
			System.out.println("CLIENT: Sending data...");
			datagramSocket.send( datagramPacket );
			System.out.println("CLIENT: Done.");
			i++;
		}
		System.out.println("lol all done");
  }
}