package pl.krgr.chinczyk.network;

public interface Responses {

	String HELLO 			= "HELLO";
	String CONNECT			= "OK PLAYER_ID %d";
	String ROOM_INFO 		= "ROOM_ID: %d " +
							  "PLAYER1_NAME %s " +
							  "PLAYER1_CAMP %s " +
							  "PLAYER2_NAME %s " +
							  "PLAYER2_CAMP %s " +
							  "PLAYER3_NAME %s " +
							  "PLAYER3_CAMP %s " +
							  "PLAYER4_NAME %s " +
							  "PLAYER4_CAMP %s " +
							  "STARTED: %s";
	String NEW_ROOM			= "OK " +
							  "ROOM_ID: %d";
	String PING				= "PONG";
	String JOIN_ROOM		= "OK " +
							  ROOM_INFO;
	String STAND_UP			= "OK " +
							  ROOM_INFO;
	String OPEN_ROOM		= "OK " +
							  ROOM_INFO;
	String ERROR			= "NOK " +
							  "MESSAGE: %s";							  
}
