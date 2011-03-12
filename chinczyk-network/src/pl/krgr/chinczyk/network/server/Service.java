package pl.krgr.chinczyk.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pl.krgr.chinczyk.network.commands.CommandFactory;

public class Service extends Thread {

	private ServerSocket listenSocket;
	private CommandFactory commandFactory;
	private boolean started = false;
	
	public Service(int port, CommandFactory factory) throws IOException {
		listenSocket = new ServerSocket(port);
		this.commandFactory = factory;
	}
	
	public void run() {
		while (true) {
			try {
				if (Thread.currentThread().isInterrupted()) {
					started = false;
					break;
				}
				System.out.println();
				System.out.println("Service::run(), listening on socket, port number = " + listenSocket.getLocalPort());
				started = true;
				Socket clientSocket = listenSocket.accept();
				System.out.println("Service::run(), Connected client on port: " + clientSocket.getPort());
				ConnectionHandler handler = new ConnectionHandler(clientSocket, commandFactory);
				Thread handleConnection = new Thread(handler);
				handleConnection.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isStarted() {
		return started;
	}
}
