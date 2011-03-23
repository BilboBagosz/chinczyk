package pl.krgr.chinczyk.network;

public interface Responses {

	String HELLO 			= "HELLO";
	String OK				= "OK ";
	String CONNECT			= OK + "PLAYER_ID %d";
	String ROOM_INFO 		= "ROOM_ID: %d " +
							  "PLAYER1_NAME %s " +
							  "PLAYER1_CAMP %s " +
							  "PLAYER2_NAME %s " +
							  "PLAYER2_CAMP %s " +
							  "PLAYER3_NAME %s " +
							  "PLAYER3_CAMP %s " +
							  "PLAYER4_NAME %s " +
							  "PLAYER4_CAMP %s " +
							  "STARTED: %b";
	String NEW_ROOM			= OK +
							  ROOM_INFO;
	String PING				= "PONG";
	String JOIN_ROOM		= OK +
							  ROOM_INFO;
	String STAND_UP			= OK +
							  ROOM_INFO;
	String GET_ROOM_INFO	= ROOM_INFO;
	String OPEN_ROOM		= OK + "OPEN ROOM " + ROOM_INFO;
	String ERROR			= "NOK " +
							  "MESSAGE: %s";
	String DISCONNECT		= OK + "DISCONNECTED";	
	String GET_ROOMS		= "<ROOM LIST>%s</ROOM LIST>";
	String LIST_SEPARATOR	= ";";
	String START_GAME		= "START GAME OK";
	String ROLL_DICE		= "ROLL DICE RESULT %d";
}
