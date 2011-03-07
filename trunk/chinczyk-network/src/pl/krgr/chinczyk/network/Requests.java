package pl.krgr.chinczyk.network;

public interface Requests {
	
	String HELLO 			= "HELLO CHN1.0";
	String CONNECT			= "CONNECT";
	String DISCONNECT		= "DISCONNECT";
	String GET_ROOMS 		= "GET ROOMS";
	String NEW_ROOM 		= "NEW ROOM";
	String PING 			= "PING";
	String JOIN_ROOM 		= "JOIN " +
					   	  	  "ROOM_ID: %d " +
					   	  	  "PLAYER_NAME: %s " +
					   	  	  "CAMP: %s";
	String STAND_UP 		= "STAND UP " +
					  	  	  "ROOM_ID: %d";
	String GET_ROOM_INFO 	= "GET ROOM INFO " +
						  	  "ROOM_ID: %d";
}
