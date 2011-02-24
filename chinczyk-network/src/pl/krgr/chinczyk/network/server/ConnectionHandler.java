package pl.krgr.chinczyk.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import pl.krgr.chinczyk.network.commands.CommandFactory;
import pl.krgr.chinczyk.network.commands.ServerCommand;

public class ConnectionHandler implements Runnable {

	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;
	private CommandFactory commandFactory;
	
	public ConnectionHandler(Socket socket, CommandFactory factory) {
		this.socket = socket;
		this.commandFactory = factory;
	}
	
	@Override
	public void run() {
		String message;
		try {
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = inputStream.readLine();
			System.out.println("ConnectionHandler::run(), request = " + message);
			ServerCommand command = commandFactory.createCommand(message);
			command.execute();
			outputStream = new PrintWriter(socket.getOutputStream(), true);
			outputStream.println(command.getResponse());
			//outputStream.println(Requests.END_OF_TRANSMISSION);
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
}
