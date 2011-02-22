package pl.krgr.chinczyk.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.Command;
import pl.krgr.chinczyk.network.commands.CommandFactory;

public class ConnectionHandler implements Runnable {

	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;
	
	public ConnectionHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		String message;
		try {
			inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(!Requests.END_OF_TRANSMISSION.equals((message = inputStream.readLine()))) {
				sb.append(message);
			}
			System.out.println("ConnectionHandler::run(), request = " + sb.toString());
			Command command = CommandFactory.createCommand(sb.toString());
			command.execute();
			outputStream = new PrintWriter(socket.getOutputStream(), true);
			outputStream.println(command.response());
			outputStream.println(Requests.END_OF_TRANSMISSION);
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
