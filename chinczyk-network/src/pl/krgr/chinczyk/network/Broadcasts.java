package pl.krgr.chinczyk.network;

public interface Broadcasts {
	String ROOM_CLOSED 		= "ROOM CLOSED \n" +
						 	  "ROOM_ID: %d \n";
	String ROOM_CHANGED 	= "ROOM_CHAGED \n" +
							  Responses.ROOM_INFO;
}
