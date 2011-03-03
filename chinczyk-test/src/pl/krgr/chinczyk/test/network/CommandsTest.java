package pl.krgr.chinczyk.test.network;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.ProtocolTokenizer;
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
	private Connector connector;
	
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
		Connector connector = initConnector();
		HelloCommand helloCommand = new HelloCommand();
		connector.handleRequest(helloCommand);
		connector.disconnect();
		Assert.assertEquals(Responses.HELLO, helloCommand.getResponse());
	}
	
	@Test
	public void testConnectCommand() {
		initConnector();
		ConnectCommand connectCommand = new ConnectCommand();
		connector.handleRequest(connectCommand);
		int sessionId = server.getSessions().get(0);
		Assert.assertEquals(String.format(Responses.CONNECT, sessionId), connectCommand.getResponse());
		DisconnectCommand disconnectCommand = new DisconnectCommand();
		connector.handleRequest(disconnectCommand);		
		Assert.assertEquals(Responses.DISCONNECT, disconnectCommand.getResponse());
		disposeConnector();
	}
	
	@Test
	public void testNewRoomCommand() {
		initConnector();
		ConnectCommand connectCommand = new ConnectCommand();
		connector.handleRequest(connectCommand);
		NewRoomCommand newRoomCommand = new NewRoomCommand();
		connector.handleRequest(newRoomCommand);
		String expected = String.format(Responses.NEW_ROOM, 0, null, null, null, null, null, null, null, null, false);
		Assert.assertEquals(expected, newRoomCommand.getResponse());
		DisconnectCommand disconnectCommand = new DisconnectCommand();
		connector.handleRequest(disconnectCommand);				
		disposeConnector();
	}

	public void testJoinRoomCommand() {
		initConnector();
//		ConnectCommand connectCommand = new ConnectCommand();
//		connector.handleRequest(connectCommand);
//		NewRoomCommand newRoomCommand = new NewRoomCommand();
//		connector.handleRequest(newRoomCommand);
//		String response = newRoomCommand.getResponse();
//		Pattern p = Pattern.compile("OK ROOM_ID: \\d PLAYER");
//		
//		String res = Responses.NEW_ROOM;
		disposeConnector();
	}

	@Test
	public void testTokenizers() {
		String response = String.format(Responses.NEW_ROOM, 10, "Pl1", "camp1", "pl2", "camp2", "pl3", "camp3", "pl4", "camp4", true);
		String patterns[] = ProtocolTokenizer.tokenize(Responses.NEW_ROOM, response);
		//TODO finish it
		System.out.println(patterns);
	}
	
	private Connector initConnector() {
		Connector connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			throw new RuntimeException(e);
		}
		return connector;
	}	

	private void disposeConnector() {
		connector.disconnect();
	}
	
	@AfterClass
	public static void stopService() {
		server.stop();
	}
}
