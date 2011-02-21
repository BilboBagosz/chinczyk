package pl.krgr.chinczyk.network;

public interface Requests {
	
	String CONNECT 		= "HELLO \n";
	String GET_ROOMS 	= "GET ROOMS \n";
	String NEW_ROOM 	= "NEW ROOM \n";
	String PING 		= "PING \n";
	String JOIN_ROOM 	= "JOIN \n " +
					   	  "ROOM_ID:%d \n " +
					   	  "PLAYER_NAME:%s \n" +
					   	  "CAMP:%s \n";
	String STAND_UP 	= "STAND UP \n" +
					  	  "ROOM_ID:%d \n";
	String OPEN_ROOM 	= "OPEN ROOM \n" +
						  "ROOM_ID:%d \n";
}
