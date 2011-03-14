package pl.krgr.chinczyk.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.commands.ClientCommand;

public class Connector implements Runnable {

	private String host;
	private int port;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String networkResponse;
	private NotificationHandler handler;
	
	public Connector(String host, int port, NotificationHandler handler) {
		this.host = host;
		this.port = port;
		this.handler = handler;
	}
	
	public void connect() throws NetworkException {
		try {
			clientSocket = new Socket(host, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Thread listener = new Thread(this);
            listener.start();
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
			clientSocket.close();
			in.close();
			setNetworkResponse("Disconnected");	
			synchronized (this) {
				notifyAll();
			}
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
	
	@Override
	public void run() {
		String serverMessage = null;
		try {
			while ((serverMessage = in.readLine()) != null) {
				if (ProtocolHelper.isNotification(serverMessage)) {
					handleNotification(serverMessage);
				} else {
					setNetworkResponse(serverMessage);
					synchronized (this) {
						notifyAll();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void handleNotification(String serverMessage) {
		handler.handleNotification(serverMessage);
	}

	public synchronized void handleRequest(ClientCommand command) throws ConnectorNotConnectedException {
		out.println(command.getRequest());
		while (getNetworkResponse() == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		command.setResponse(getNetworkResponse());
		setNetworkResponse(null);
//		try {
//			String response = in.readLine();
//			command.setResponse(response);
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new ConnectorNotConnectedException(e);
//		}
		command.execute();		
	}

	public synchronized void setNetworkResponse(String networkResponse) {
		this.networkResponse = networkResponse;
	}

	public synchronized String getNetworkResponse() {
		return networkResponse;
	}

}
