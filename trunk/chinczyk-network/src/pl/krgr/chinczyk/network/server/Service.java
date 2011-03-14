package pl.krgr.chinczyk.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import pl.krgr.chinczyk.network.commands.CommandFactory;
import pl.krgr.chinczyk.network.notifications.ServerNotification;

public class Service extends Thread {

	private ServerSocket listenSocket;
	private CommandFactory commandFactory;
	private boolean running = false;
	private List<ConnectionHandler> handlers = new LinkedList<ConnectionHandler> ();
	
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
				handler.start();
				handlers.add(handler);
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

	public void sendNotification(int sessionId, String notification) {
		ConnectionHandler handler = getHandler(sessionId);
		handler.sendNotification(notification);
	}
	
	private ConnectionHandler getHandler(int sessionId) {
		for (ConnectionHandler handler : handlers) {
			if (handler.getSessionId() == sessionId) {
				return handler;
			}
		}
		return null;
	}

	public void broadcastExcept(ServerNotification notification, int sessionId) {
		for (ConnectionHandler handler : handlers) {
			if (handler.getSessionId() != sessionId) {
				handler.sendNotification(notification.getNotification());
			}
		}		
	}
	
	public void broadcast(ServerNotification notification) {
		for (ConnectionHandler handler : handlers) {
			handler.sendNotification(notification.getNotification());
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
		for (ConnectionHandler handler : handlers) {
			handler.interrupt();
		}
	}
}
