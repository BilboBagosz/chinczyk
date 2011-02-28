package pl.krgr.chinczyk.test.network;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.ServerImpl;
import pl.krgr.chinczyk.test.network.commands.ConnectCommand;
import pl.krgr.chinczyk.test.network.commands.DisconnectCommand;
import pl.krgr.chinczyk.test.network.commands.HelloCommand;
import pl.krgr.chinczyk.test.network.commands.NewRoomCommand;

public class CommandsTest {

	private static final int PORT = 5555;
	private static Server server;
	
	@BeforeClass
	public static void runService() {
		try {
			server = new ServerImpl(PORT);
			server.start();
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHelloCommand() {
		Connector connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		HelloCommand helloCommand = new HelloCommand();
		connector.handleRequest(helloCommand);
		connector.disconnect();
		Assert.assertEquals(Responses.HELLO, helloCommand.getResponse());
	}	
	
	@Test
	public void testConnectCommand() {
		Connector connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			e.printStackTrace();
			return;
		}
		ConnectCommand connectCommand = new ConnectCommand();
		connector.handleRequest(connectCommand);
		int sessionId = server.getSessions().get(0);
		Assert.assertEquals(String.format(Responses.CONNECT, sessionId), connectCommand.getResponse());
		DisconnectCommand disconnectCommand = new DisconnectCommand();
		connector.handleRequest(disconnectCommand);		
		Assert.assertEquals(Responses.DISCONNECT, disconnectCommand.getResponse());
		connector.disconnect();
	}
	
	@Test
	public void testNewRoomCommand() {
		Connector connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			e.printStackTrace();
			return;
		}
		ConnectCommand connectCommand = new ConnectCommand();
		connector.handleRequest(connectCommand);
		NewRoomCommand newRoomCommand = new NewRoomCommand();
		connector.handleRequest(newRoomCommand);
		String expected = String.format(Responses.NEW_ROOM, 0, null, null, null, null, null, null, null, null, false);
		Assert.assertEquals(expected, newRoomCommand.getResponse());
		DisconnectCommand disconnectCommand = new DisconnectCommand();
		connector.handleRequest(disconnectCommand);				
		connector.disconnect();
	}
	
	@AfterClass
	public static void stopService() {
		server.stop();
	}
}
