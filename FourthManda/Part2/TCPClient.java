package FourthManda.Part2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
	
	private Socket serverSocket;
	private OutputStream os;
	private DataOutputStream dos;
	private InputStream is;
	private DataInputStream dis;
	
	/**
	 * Starts the client.
	 * 
	 * @param serverAddress IP-address of server.
	 * @param serverPort Port number on server.
	 * @throws IOException
	 */
	public void initialize(String serverAddress, int serverPort) throws IOException{
		System.out.println("Client: Trying to create socket with port: " + serverPort +
				" and InetAddress: " + serverAddress);
		
		serverSocket = new Socket(serverAddress, serverPort);
		System.out.println("Client: Server socket created.");
		
		os = serverSocket.getOutputStream();
		System.out.println("Client: OutputStream created.");
		
		dos = new DataOutputStream(os);
		System.out.println("Client: DataOutputStream created.");
		
		is = serverSocket.getInputStream();
		System.out.println("Client: InputStream created.");
		
		dis = new DataInputStream(is);
		System.out.println("Client: DataInputStream created.");
	}
	
	/**
	 * Sends the message to the server through the port/address used in initialize.
	 * Will do nothing unless initialize is run.
	 * 
	 * @param message The message to be changed to lower-case or upper-case.
	 * @return The message returned from the server.
	 * @throws IOException
	 */
	public String sendMessage(String message) throws IOException{
		dos.writeUTF(message);
		
		dos.flush();
		System.out.println("Client: Flushed DataOutputStream.");
		
		String returnMessage = dis.readUTF();
		
		serverSocket.close();
		System.out.println("Client: Closed socket.");
		
		return returnMessage;
	}
}
