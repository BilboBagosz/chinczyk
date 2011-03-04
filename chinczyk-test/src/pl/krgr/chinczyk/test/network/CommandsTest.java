package pl.krgr.chinczyk.test.network;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.krgr.chinczyk.network.NetworkException;
import pl.krgr.chinczyk.network.ProtocolHelper;
import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.ServerImpl;
import pl.krgr.chinczyk.test.network.commands.ConnectCommand;
import pl.krgr.chinczyk.test.network.commands.DisconnectCommand;
import pl.krgr.chinczyk.test.network.commands.GetRoomInfoCommand;
import pl.krgr.chinczyk.test.network.commands.HelloCommand;
import pl.krgr.chinczyk.test.network.commands.JoinRoomCommand;
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
		initConnector();
		HelloCommand helloCommand = new HelloCommand();
		connector.handleRequest(helloCommand);
		disposeConnector();
		Assert.assertEquals(Responses.HELLO, helloCommand.getResponse());
	}
	
	@Test
	public void testConnectCommand() {
		initConnector();
		ConnectCommand connectCommand = connect();
		int sessionId = ((ServerImpl)server).getSessions().get(0).getSessionId();
		Assert.assertEquals(String.format(Responses.CONNECT, sessionId), connectCommand.getResponse());
		DisconnectCommand disconnectCommand = disconnect();		
		Assert.assertEquals(Responses.DISCONNECT, disconnectCommand.getResponse());
		disposeConnector();
	}
	
	@Test
	public void testNewRoomCommand() {
		initConnector();
		connect();

		NewRoomCommand newRoomCommand = new NewRoomCommand();
		connector.handleRequest(newRoomCommand);
		String expected = String.format(Responses.NEW_ROOM, 0, null, null, null, null, null, null, null, null, false);
		Assert.assertEquals(expected, newRoomCommand.getResponse());

		disconnect();				
		disposeConnector();
	}

	@Test
	public void testJoinRoomCommand() {
		initConnector();
		connect();
		int roomId = addNewRoom();
		
		JoinRoomCommand join = new JoinRoomCommand(roomId, "Krzysztof", "RED");
		connector.handleRequest(join);
		String response = join.getResponse();
		String expectedResponse = String.format(Responses.JOIN_ROOM, roomId, "Krzysztof", "RED", null, null, null, null, null, null, false);
		Assert.assertEquals(expectedResponse, response);

		disconnect();				
		disposeConnector();
	}

	@Test
	public void testGetRoomInfoCommand() {
		initConnector();
		connect();
		addNewRoom();
		int roomId = addNewRoom();
		GetRoomInfoCommand infoCommand = new GetRoomInfoCommand(roomId);
		connector.handleRequest(infoCommand);
		String response = infoCommand.getResponse();
		String expectedResponse = String.format(Responses.GET_ROOM_INFO, roomId, null, null, null, null, null, null, null, null, false);
		Assert.assertEquals(expectedResponse, response);
		disconnect();
		disposeConnector();
	}
	
	private int addNewRoom() {
		NewRoomCommand newRoomCommand = new NewRoomCommand();
		connector.handleRequest(newRoomCommand);
		String response = newRoomCommand.getResponse();
		String data[] = ProtocolHelper.matches(Responses.NEW_ROOM, response);
		int roomId = Integer.parseInt(data[0]);
		return roomId;
	}

	private DisconnectCommand disconnect() {
		DisconnectCommand disconnectCommand = new DisconnectCommand();
		connector.handleRequest(disconnectCommand);
		return disconnectCommand;
	}

	private ConnectCommand connect() {
		ConnectCommand connectCommand = new ConnectCommand();
		connector.handleRequest(connectCommand);
		return connectCommand;
	}

	@Test
	public void testTokenizers() {
		String response = String.format(Responses.NEW_ROOM, 10, "Pl1", "camp1", "pl2", "camp2", "pl3", "camp3", "pl4", "camp4", true);
		String data[] = ProtocolHelper.matches(Responses.NEW_ROOM, response);
		String expectedData[] = new String[] {"10", "Pl1", "camp1", "pl2", "camp2", "pl3", "camp3", "pl4", "camp4", "true"};
		Assert.assertEquals(expectedData.length, data.length);
		for (int i = 0; i < data.length; i++) {
			Assert.assertEquals(expectedData[i], data[i]);
		}
	}
	
	private void initConnector() {
		connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			throw new RuntimeException(e);
		}
	}	

	private void disposeConnector() {
		connector.disconnect();
	}
	
	@AfterClass
	public static void stopService() {
		server.stop();
	}
}
