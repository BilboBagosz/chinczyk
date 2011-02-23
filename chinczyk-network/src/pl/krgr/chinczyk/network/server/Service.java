package pl.krgr.chinczyk.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pl.krgr.chinczyk.network.commands.CommandFactory;

public class Service {

	private ServerSocket listenSocket;
	private CommandFactory commandFactory;
	
	public Service(int port, CommandFactory factory) throws IOException {
		listenSocket = new ServerSocket(port);
		this.commandFactory = factory;
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
				Thread handleConnection = new Thread(new ConnectionHandler(clientSocket, commandFactory));
				handleConnection.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
