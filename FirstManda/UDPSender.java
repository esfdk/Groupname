package FirstManda;
import java.net.*;

public class UDPSender {
  public static void main(String args[ ]) throws Exception{ 
		InetAddress receiversAddress = InetAddress.getByName("localhost");
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
		for(int i = 0; i < 1000; i++) {
			System.out.println("CLIENT: Sending data...");
			datagramSocket.send( datagramPacket );
			System.out.println("CLIENT: Done.");
			i++;
		}
		messageAsString = "terminate";
		message = messageAsString.getBytes();
		datagramPacket = new DatagramPacket(message, 9, receiversAddress, receiversPort);
		datagramSocket.send(datagramPacket);
  }
}