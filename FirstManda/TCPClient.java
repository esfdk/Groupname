package FirstManda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
	
	private Socket socket;
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
		
		socket = new Socket(serverAddress, serverPort);
		System.out.println("Client: Socket created.");
		
		os = socket.getOutputStream();
		System.out.println("Client: OutputStream created.");
		
		dos = new DataOutputStream(os);
		System.out.println("Client: DataOutputStream created.");
		
		is = socket.getInputStream();
		System.out.println("Client: InputStream created.");
		
		dis = new DataInputStream(is);
		System.out.println("Client: DataInputStream created.");
	}
	
	/**
	 * Sends the message to the server through the port/address used in initialize.
	 * Will do nothing unless initialize is run.
	 * 
	 * @param i 0 for lower-case, 1 for upper-case.
	 * @param message The message to be changed to lower-case or upper-case.
	 * @throws IOException
	 */
	public void sendMessage(int i, String message) throws IOException{
		dos.writeUTF(message);
		
		dos.flush();
		System.out.println("Client: Flushed DataOutputStream.");
		
		dos.writeInt(i);
		
		dos.flush();
		System.out.println("Client: Flushed DataOutputStream.");
		
		System.out.println("Client: Message received from server: ");
		System.out.println(dis.readUTF());
		
		socket.close();
		System.out.println("Client: Closed socket.");
	}
}
