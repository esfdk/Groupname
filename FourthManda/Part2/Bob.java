package FourthManda.Part2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents part B in the Needham-Schroeder implementation.
 * @author Mathias Brandt
 */
public class Bob {
	TCPServer server;
	int serverPort = 1338;
	
	public Bob() {
		try {
			startServer(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer(int serverPort) throws IOException{
		ServerSocket serverSocket = new ServerSocket(serverPort);
		System.out.println("Server: ServerSocket created on port " + serverPort);
		
		while(true) {
			System.out.println("Server: Waiting for client...");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Server: Established connection to client " + serverSocket.getInetAddress());

			handleConnection(clientSocket);
		}
	}
	
	/**
	 * Takes care of receiving messages.
	 */
	public void handleConnection(Socket cs) {
		Socket clientSocket;
		InputStream is;
		DataInputStream dis;
		OutputStream os;
		DataOutputStream dos;
		
		clientSocket = cs;
		
		
		try {
			is = clientSocket.getInputStream();
			
			System.out.println("Server: InputStream created.");
	
			dis = new DataInputStream(is);
			System.out.println("Server: DataInputStream created.");
	
			String message = dis.readUTF();
			System.out.println("Server: Received message from client: ");
			System.out.println("Server: " + message);
					
			os = clientSocket.getOutputStream();
			System.out.println("Server: OutputStream created.");
	
			dos = new DataOutputStream(os);
			System.out.println("Server: DataOutputStream created.");
			
			// Alter message for return
			message = "Message 4";
					
			System.out.println("Server: Replying to client with new message: ");
			System.out.println("Server: " + message);
			dos.writeUTF(message);
	
			dos.flush();
			System.out.println("Server: Flushed DataOutputStream.");
	
			clientSocket.close();
			System.out.println("Server: Closed socket");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Bob();
	}
}
