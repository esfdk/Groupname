package FourthManda.Part2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	/**
	 * Starts the server on a specific port. Server will run until runtime is stopped.
	 * 
	 * @param serverPort The port on the server.
	 * @throws IOException
	 */
	public void startServer(int serverPort) throws IOException{
		ServerSocket serverSocket = new ServerSocket(serverPort);
		System.out.println("Server: ServerSocket created on port " + serverPort);
		
		while(true) {
			System.out.println("Server: Waiting for client...");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Server: Established connection to client " + serverSocket.getInetAddress());

			Thread t = new ServerThread(clientSocket);
			t.start();
		}
	}	
}

/**
 * Takes care of receiving messages.
 */
class ServerThread extends Thread {
	private Socket clientSocket;
	private InputStream is;
	private DataInputStream dis;
	private OutputStream os;
	private DataOutputStream dos;

	public ServerThread(Socket cs) {
		clientSocket = cs;
	}

	public void run() {
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
			
			System.out.println("Server: Replying to client with new message: ");
			System.out.println("Server: " + message);
			dos.writeUTF(message);

			dos.flush();
			System.out.println("Server: Flushed DataOutputStream.");

			clientSocket.close();
			System.out.println("Server: Closed socket");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}