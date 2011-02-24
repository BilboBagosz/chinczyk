package pl.krgr.chinczyk.network;

public interface Requests {
	
	String CONNECT 		= "HELLO CHN1.0";
	String GET_ROOMS 	= "GET ROOMS";
	String NEW_ROOM 	= "NEW ROOM";
	String PING 		= "PING";
	String JOIN_ROOM 	= "JOIN " +
					   	  "ROOM_ID:%d" +
					   	  "PLAYER_NAME:%s" +
					   	  "CAMP:%s";
	String STAND_UP 	= "STAND UP" +
					  	  "ROOM_ID:%d";
	String OPEN_ROOM 	= "OPEN ROOM" +
						  "ROOM_ID:%d";
}
