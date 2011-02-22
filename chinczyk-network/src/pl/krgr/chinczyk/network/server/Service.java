package pl.krgr.chinczyk.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {

	ServerSocket listenSocket;
	
	public Service(int port) throws IOException {
		listenSocket = new ServerSocket(port);
	}
	
	public void start() {
		while (true) {
			try {
				if (Thread.currentThread().isInterrupted()) {
					break;
				}
				System.out.println("Service::start(), listening on socket");
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connected client on port: " + clientSocket.getLocalPort());
				Thread handleConnection = new Thread(new ConnectionHandler(clientSocket));
				handleConnection.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
