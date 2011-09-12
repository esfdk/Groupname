package FirstManda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private Socket socket;
	ServerSocket serverSocket;
	private InputStream is;
	private DataInputStream dis;
	private OutputStream os;
	private DataOutputStream dos;
	
	public void startServer(int serverPort) throws IOException{
		serverSocket = new ServerSocket(serverPort);
		System.out.println("Server: ServerSocket created with " + serverPort);
		
		socket = serverSocket.accept();
		System.out.println("Server: Socket created.");
		
		is = socket.getInputStream();
		System.out.println("Server: InputStream created.");
		
		dis = new DataInputStream(is);
		System.out.println("Server: DataInputStream created.");
		
		String message = dis.readUTF();
		System.out.println(message);
		
		os = socket.getOutputStream();
		System.out.println("Server: OutputStream created.");
		
		dos = new DataOutputStream(os);
		System.out.println("Server: OutputStream created.");
	
		dos.writeUTF(message.toUpperCase());
		System.out.println("Server: Wrote message in upper-case.");
		
		dos.flush();
		System.out.println("Server: Flushed dos.");
		
		socket.close();
		System.out.println("Server: Socket close.");
	}
}
