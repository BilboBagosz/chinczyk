package pl.krgr.chinczyk.network;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.krgr.chinczyk.network.client.Connector;
import pl.krgr.chinczyk.network.commands.ClientCommand;
import pl.krgr.chinczyk.network.server.Service;
import pl.krgr.chinczyk.server.network.commands.CommandFactoryImpl;

public class ConnectorTest {

	private static final int PORT = 5555;
	private static Service service;
	private static Thread serviceRunner;
	private String response;
	
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
		connector.handleRequest(new ClientCommand() {

			private String response;
			
			@Override
			public void execute() {
				ConnectorTest.this.response = response;
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
		
		System.out.println("ConnectorTest::testConnector(), Response: " + response);
	}	
	
	@AfterClass
	public static void stopService() {
		serviceRunner.interrupt();
	}
}
