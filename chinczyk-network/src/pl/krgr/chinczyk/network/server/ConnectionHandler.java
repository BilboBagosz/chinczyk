package pl.krgr.chinczyk.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import pl.krgr.chinczyk.network.commands.CommandFactory;
import pl.krgr.chinczyk.network.commands.ServerCommand;

public class ConnectionHandler extends Thread {

	private static volatile int genId = 0; 
	
	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;
	private CommandFactory commandFactory;
	private int sessionId = 0;
	
	public ConnectionHandler(Socket socket, CommandFactory factory) {
		this.socket = socket;
		this.commandFactory = factory;
		this.sessionId = genId++;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	
	@Override
	public void run() {
		String message;
		try {
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(socket.getOutputStream(), true);
			while ((message = inputStream.readLine()) != null) {				
				System.out.println("ConnectionHandler::run(), request = " + message);
				ServerCommand command = commandFactory.createCommand(message, sessionId);
				command.execute();
				System.out.println("ConnectionHandler::run(), response = " + command.getResponse());
				outputStream.println(command.getResponse());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendNotification(String notification) {
		outputStream.println(notification);
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		try {
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
