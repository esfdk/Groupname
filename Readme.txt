The system consists of three classes:
	Tester
	TCPServer
	TCPClient

The main class is located in the Tester class.
When launching the program, the user must choose wether to act as a server or a client.

SERVER:
	The createServer() method is called. The user is prompted for a port number, and an
	instance of the TCPServer class is created with the specified port as parameter.
	The TCPServer instance creates a new ServerSocket with the parameter port and enters
	a infinite loop. It listens for incoming connections and for each connection creates
	a new thread. When the thread is executed is creates an input stream. Then it reads
	the message and the task to be performed (convert to upper/lower case). After that,
	an output stream is created and the converted message is returned to the client.
	Lastly, the socket is closed and the thread terminates.

CLIENT:
	The createClient() method is called. The user is prompted for the IP address of the
	remote server and the port number. The client is launched as a new instance of
	TCPClient. The TCPClient instance creates both output and input streams. After that,
	the user is prompted for the task to be performed by the server (convert to upper/lower
	case) and the message to be converted. The message and task is sent to the server.
	Lastly, the converted message is read from the input stream, and the socket is
	closed.