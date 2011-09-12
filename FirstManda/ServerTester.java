package FirstManda;

import java.io.IOException;
import java.net.InetAddress;

public class ServerTester {
	
	public static void main(String[] args) {
		TCPServer TCPs = new TCPServer();
		
		try {
			TCPs.startServer(4567);
			System.out.println("Started server.");
		} catch (IOException e) {
			System.out.println("It was unsuccessful.");
			e.printStackTrace();
		}
	}

}
