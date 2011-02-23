package pl.krgr.chinczyk.network;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.commands.ClientCommand;
import pl.krgr.chinczyk.network.server.Service;
import pl.krgr.chinczyk.server.network.commands.CommandFactoryImpl;
import pl.krgr.chinczyk.server.network.commands.ConnectCommand;

public class ConnectorTest {

	private static final int PORT = 5555;
	private static Service service;
	private static Thread serviceRunner;
	
	@BeforeClass
	public static void runService() {
		try {
			service = new Service(PORT, new CommandFactoryImpl());
			serviceRunner = new Thread() {
				public void run() {
					service.start();
				}
			};
			serviceRunner.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnector() {
		Connector connector = new Connector("localhost", PORT);
		try {
			connector.connect();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		String response;
		connector.handleRequest(new ClientCommand() {

			@Override
			public void execute() {
			}

			@Override
			public String getRequest() {
				return null;
			}

			@Override
			public String setResponse(String response) {
				return null;
			}
			
		});
		
		System.out.println("ConnectorTest::testConnector(), Response: " + response);
	}	
	
	@AfterClass
	public static void stopService() {
		serviceRunner.interrupt();
	}
}
