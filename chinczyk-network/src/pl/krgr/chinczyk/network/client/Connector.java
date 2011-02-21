package pl.krgr.chinczyk.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pl.krgr.chinczyk.network.NetworkException;

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
	
	public String sendRequest(String request) {
		StringBuilder res = new StringBuilder();
		out.print(request);
		out.close();
		try {
			String response = null;
			while ((response = in.readLine()) != null) {
				res.append(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return "";
		
	}
}
