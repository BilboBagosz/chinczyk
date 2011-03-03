package pl.krgr.chinczyk.server.network.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.krgr.chinczyk.model.AbstractCamp;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.Requests;
import pl.krgr.chinczyk.network.commands.CommandFactory;
import pl.krgr.chinczyk.network.commands.ServerCommand;
import pl.krgr.chinczyk.server.Server;

public class CommandFactoryImpl implements CommandFactory {

	private Server serverInstance;
	
	public CommandFactoryImpl(Server server) {
		this.serverInstance = server;
	}
	
	@Override
	public ServerCommand createCommand(String message, int sessionId) {
		if (Requests.HELLO.equals(message)) {
			return new HelloCommand();
		}
		if (Requests.CONNECT.equals(message)) {
			return new ConnectCommand(serverInstance, sessionId);
		}
		if (Requests.DISCONNECT.equals(message)) {
			return new DisconnectCommand(serverInstance, sessionId);
		}
		if (Requests.NEW_ROOM.equals(message)) {
			return new NewRoomCommand(serverInstance, sessionId);
		}
		if (message.startsWith("JOIN")) {			
//			Pattern p = Pattern.compile("JOIN ROOM_ID:\\d PLAYER_NAME:\\w* CAMP:\\w*");
			Pattern p = Pattern.compile("ROOM_ID:\\d");
			Matcher m = p.matcher(message);
			if (m.find()) {
				
			}
			
			
			List<String> matches = new LinkedList<String>();
			while (m.find()) {
				matches.add(m.group());
			}
			if (matches.size() == 3) {
				int roomId = Integer.parseInt(matches.get(0));
				String playerName = matches.get(1);
				Camp camp = AbstractCamp.fromString(matches.get(2));
				return new JoinRoomCommand(serverInstance, sessionId, roomId, playerName, camp);
			} else {
				return new ErrorCommand("Unknown Command");
			}
		}
		return new ErrorCommand("Unknown Command");
	}
}
