package FourthManda.Part2;

import java.io.IOException;

/**
 * Represents part A in the Needham-Schroeder implementation.
 * @author Mathias Brandt
 */
public class Alice {
	TCPClient client;
	String saraServerAddress = "127.0.0.1";
	int saraServerPort = 1337;
	String bobServerAddress = "127.0.0.1";
	int bobServerPort = 1338;
	
	String message;
	
	public Alice() {
		// Connect to Sara
		connectToServer(saraServerAddress, saraServerPort);
		
		// Send message to Sara
		try {
			message = client.sendMessage("Message 1");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reply from Sara
		System.out.println("ALICE: Reply from Sara:");
		System.out.println("ALICE: " + message);
		
		// Connect to Bob
		connectToServer(bobServerAddress, bobServerPort);
		
		// Send message to Bob
		try {
			message = client.sendMessage("Message 3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reply from Bob
		System.out.println("ALICE: Reply from Bob:");
		System.out.println("ALICE: " + message);
		
		// Connect to Bob
		connectToServer(bobServerAddress, bobServerPort);
		
		// Send message to Bob
		try {
			client.sendMessage("Message 5");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connectToServer(String serverAddress, int serverPort) {
		try {
			client = new TCPClient();
			client.initialize(serverAddress, serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Alice();
	}
}
