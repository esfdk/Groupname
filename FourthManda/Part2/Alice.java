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
	
	String message = "Message 1";
	
	public Alice() {
		// Connect to Sara
		System.out.println("Alice: connecting to Sara");
		connect(saraServerAddress, saraServerPort);
		
		// Send message to Sara
		System.out.println("Alice: sending message to Sara:");
		System.out.println("Alice: " + message);
		try {
			message = client.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reply from Sara
		System.out.println("ALICE: Reply from Sara:");
		System.out.println("ALICE: " + message);
		
		message = incrementMessage(message);
		
		// Connect to Bob
		System.out.println("Alice: Connecting to Bob");
		connect(bobServerAddress, bobServerPort);
		
		// Send message to Bob
		System.out.println("Alice: sending message to Bob:");
		System.out.println("Alice: " + message);
		try {
			message = client.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Reply from Bob
		System.out.println("ALICE: Reply from Bob:");
		System.out.println("ALICE: " + message);
		
		message = incrementMessage(message);
		
		// Connect to Bob
		System.out.println("Connecting to Bob");
		connect(bobServerAddress, bobServerPort);
		
		// Send message to Bob
		System.out.println("Alice: sending message to Bob:");
		System.out.println("Alice: " + message);
		try {
			client.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String serverAddress, int serverPort) {
		try {
			client = new TCPClient();
			client.initialize(serverAddress, serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String incrementMessage(String m) {
		String[] strings = m.split(" ");
		int number = Integer.parseInt(strings[1]);
		number++;
		return strings[0] + " " + number;
	}
	
	public static void main(String[] args) {
		new Alice();
	}
}
