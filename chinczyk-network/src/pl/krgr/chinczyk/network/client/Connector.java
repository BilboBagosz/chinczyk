package pl.krgr.chinczyk.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.commands.ClientCommand;

public class Connector {

	private String host;
	private int port;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public Connector(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() throws NetworkException {
		try {
			clientSocket = new Socket(host, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new NetworkException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException(e);
		}		
	}
	
	public void disconnect() {
		out.close();
		try {
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out = null;
			in = null;
			clientSocket = null;
		}
	}
	
	public boolean isConnected() {
		if (out != null && in != null && clientSocket != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void handleRequest(ClientCommand command) {
		out.println(command.getRequest());
		try {
			String response = in.readLine();
			command.setResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		command.execute();		
	}
}
