package pl.krgr.chinczyk.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pl.krgr.chinczyk.network.commands.CommandFactory;

public class Service extends Thread {

	private ServerSocket listenSocket;
	private CommandFactory commandFactory;
	private boolean running = false;
	
	public Service(int port, CommandFactory factory) throws IOException {
		listenSocket = new ServerSocket(port);
		this.commandFactory = factory;
	}
	
	public void run() {
		while (true) {
			try {
				setRunning(true);
				if (isInterrupted()) {
					setRunning(false);
					break;
				}
				System.out.println();
				System.out.println("Service::run(), listening on socket, port number = " + listenSocket.getLocalPort());
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

	
	
	public synchronized boolean isRunning() {
		return running;
	}

	private synchronized void setRunning(boolean running) {
		this.running = running;
		notifyAll();
	}
	
	public synchronized void waitUntilStop() {
		while(isRunning()) {
			try {
				wait();				
			} catch (InterruptedException e) {
				return;
			}			
		}
	}
	
	public synchronized void waitUntilStarted() {
		while (!isRunning()) {
			try {
				wait();				
			} catch (InterruptedException e) {
				return;
			}
		}		
	}

	@Override
	public void interrupt() {		
		super.interrupt();
		try {
			listenSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
