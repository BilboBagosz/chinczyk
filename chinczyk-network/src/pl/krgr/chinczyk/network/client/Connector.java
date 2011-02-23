package pl.krgr.chinczyk.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.Requests;
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
		}
	}
	
	public void handleRequest(ClientCommand command) {
		StringBuilder res = new StringBuilder();
		out.println(command.getRequest());
		out.println(Requests.END_OF_TRANSMISSION);
		try {
			String response = null;
			while (!Requests.END_OF_TRANSMISSION.equals((response = in.readLine()))) {
				res.append(response);
			}
			command.setResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		command.execute();		
	}
}
