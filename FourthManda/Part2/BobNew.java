package FourthManda.Part2;

import java.io.IOException;

/**
 * Represents part B in the Needham-Schroeder implementation.
 * @author Mathias Brandt
 */
public class BobNew {
	TCPServer server;
	int serverPort = 1338;
	
	public BobNew() {
		try {
			server = new TCPServer(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new BobNew();
	}
}
