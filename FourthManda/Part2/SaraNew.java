package FourthManda.Part2;

import java.io.IOException;

/**
 * Represents the server in the Needham-Schroeder implementation.
 * @author Mathias Brandt
 */
public class SaraNew {
	TCPServer server;
	int serverPort = 1337;
	
	public SaraNew() {
		try {
			server = new TCPServer(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new SaraNew();
	}
}
