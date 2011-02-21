package pl.krgr.chinczyk.network;

public interface Responses {

	String CONNECT 			= "HELLO \n";
	String ROOM_INFO 		= "ROOM_ID: %d \n" +
							  "PLAYER1_NAME %s \n" +
							  "PLAYER1_CAMP %s \n" +
							  "PLAYER2_NAME %s \n" +
							  "PLAYER2_CAMP %s \n" +
							  "PLAYER3_NAME %s \n" +
							  "PLAYER3_CAMP %s \n" +
							  "PLAYER4_NAME %s \n" +
							  "PLAYER4_CAMP %s \n" +
							  "STARTED: %s \n";
	String NEW_ROOM			= "OK \n" +
							  "ROOM_ID: %d \n";
	String PING				= "PONG \n";
	String JOIN_ROOM		= "OK \n" +
							  ROOM_INFO;
	String STAND_UP			= "OK \n" +
							  ROOM_INFO;
	String OPEN_ROOM		= "OK \n" +
							  ROOM_INFO;
	String ERROR			= "NOK \n" +
							  "MESSAGE: %s";							  
}
