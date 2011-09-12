package FirstManda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public void startServer(int serverPort) throws IOException{
		ServerSocket serverSocket = new ServerSocket(serverPort);
		System.out.println("Server: ServerSocket created on port " + serverPort);
		
		while(true) {
			System.out.println("Server: Waiting for client...");
			Socket socket = serverSocket.accept();
			System.out.println("Server: Established connection to client " + serverSocket.getInetAddress());

			Thread t = new ServerThread(serverSocket, socket);
			t.start();
		}
	}	
}

class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private DataInputStream dis;
	private OutputStream os;
	private DataOutputStream dos;

	public ServerThread(ServerSocket ss, Socket s) {
		serverSocket = ss;
		socket = s;
	}

	public void run() {
		try {
			is = socket.getInputStream();
			System.out.println("Server: InputStream created.");

			dis = new DataInputStream(is);
			System.out.println("Server: DataInputStream created.");

			String message = dis.readUTF();
			System.out.println("Server: Received message from client: ");
			System.out.println(message);
			
			int i = dis.readInt();
			System.out.println("Server: Received integer from client: ");
			System.out.println(i);

			if(i == 0){
				message = message.toLowerCase();
				System.out.println("Server: Converted message to upper-case.");
			}
			else if(i == 1){
				message = message.toUpperCase();
				System.out.println("Server: Converted message to upper-case.");
			}
			else{
				System.out.println("Wrong input.");
			}

			os = socket.getOutputStream();
			System.out.println("Server: OutputStream created.");

			dos = new DataOutputStream(os);
			System.out.println("Server: DataOutputStream created.");

			dos.writeUTF(message);

			dos.flush();
			System.out.println("Server: Flushed DataOutputStream.");

			socket.close();
			System.out.println("Server: Closed socket");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
