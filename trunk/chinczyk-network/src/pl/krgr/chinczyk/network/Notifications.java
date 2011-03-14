package pl.krgr.chinczyk.network;

public interface Notifications {
	public String NOTIFICATION_PREFIX 		= "NOTIFICATION: ";
	public String SERVER_STOP				= NOTIFICATION_PREFIX + "SERVER_STOP ";
	public String NEW_ROOM_INFO				= NOTIFICATION_PREFIX + Responses.ROOM_INFO;
	public String ROOM_CHANGED				= NOTIFICATION_PREFIX + "CHANGED " + Responses.ROOM_INFO;
}
