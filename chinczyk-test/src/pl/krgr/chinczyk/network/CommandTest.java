package pl.krgr.chinczyk.network;

import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.commands.ClientCommand;
import pl.krgr.chinczyk.server.Server;
import pl.krgr.chinczyk.server.ServerException;
import pl.krgr.chinczyk.server.ServerImpl;
import pl.krgr.chinczyk.server.nls.Messages;

public class CommandTest {

	private static final int PORT = 5555;
	private static Server server;
	private String response;
	
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
		connector.handleRequest(new ClientCommand() {

			private String response;
			
			@Override
			public void execute() {
				CommandTest.this.response = response;
			}

			@Override
			public String getRequest() {
				return Requests.HELLO;
			}

			@Override
			public void setResponse(String response) {
				this.response = response;
			}
			
		});
		connector.disconnect();
		Assert.assertEquals(Responses.HELLO, response);
	}	
	
	@Test
	public void testConnectCommand() {
		Connector connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		connector.handleRequest(new ClientCommand() {

			private String response;
			
			@Override
			public void execute() {
				CommandTest.this.response = response;
			}

			@Override
			public String getRequest() {
				return Requests.CONNECT;
			}

			@Override
			public void setResponse(String response) {
				this.response = response;
			}
			
		});
		connector.disconnect();
		List<Integer> sessions = server.getSessions();
		if (sessions.size() > 0) {
			int sessionId = server.getSessions().get(0);
			Assert.assertEquals(String.format(Responses.CONNECT, sessionId), response);
		} else {
			Assert.assertEquals(String.format(Responses.ERROR, Messages.ServerImpl_CannotConnect), response);
		}
	}
	
	@AfterClass
	public static void stopService() {
		server.stop();
	}
}
