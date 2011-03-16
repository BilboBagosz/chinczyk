package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.model.AbstractCamp;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.ProtocolHelper;
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
		String[] protocolData = null;
		if (ProtocolHelper.matches(Requests.HELLO, message).length > 0) {
			return new HelloCommand();			
		}
		if (ProtocolHelper.matches(Requests.GET_ROOMS, message).length > 0) {
			return new GetRoomsCommand(serverInstance, sessionId);			
		}
		if (ProtocolHelper.matches(Requests.CONNECT, message).length > 0) {
			return new ConnectCommand(serverInstance, sessionId);
		}
		if (ProtocolHelper.matches(Requests.DISCONNECT, message).length > 0) {
			return new DisconnectCommand(serverInstance, sessionId);
		}
		if (ProtocolHelper.matches(Requests.NEW_ROOM, message).length > 0) {
			return new NewRoomCommand(serverInstance, sessionId);
		}
		if ((protocolData = ProtocolHelper.matches(Requests.JOIN_ROOM, message)).length > 0) {
			if (protocolData.length < 3) {
				return new ErrorCommand("Unknown Command");
			}
			int roomId = Integer.parseInt(protocolData[0]);
			String playerName = protocolData[1];
			Camp camp = AbstractCamp.fromString(protocolData[2]);
			return new JoinRoomCommand(serverInstance, sessionId, roomId, playerName, camp);
		}
		if ((protocolData = ProtocolHelper.matches(Requests.GET_ROOM_INFO, message)).length > 0) {
			int roomId = Integer.parseInt(protocolData[0]);
			return new GetRoomInfoCommand(serverInstance, sessionId, roomId);
		}
		if ((protocolData = ProtocolHelper.matches(Requests.STAND_UP, message)).length > 0) {
			int roomId = Integer.parseInt(protocolData[0]);
			String playerName = protocolData[1];
			return new StandUpCommand(serverInstance, sessionId, roomId, playerName);
		}
		if ((protocolData = ProtocolHelper.matches(Requests.OPEN_ROOM, message)).length > 0) {
			int roomId = Integer.parseInt(protocolData[0]);
			return new OpenRoomCommand(serverInstance, sessionId, roomId);
		}
		if ((protocolData = ProtocolHelper.matches(Requests.START_GAME, message)).length > 0) {
			int roomId = Integer.parseInt(protocolData[1]);
			String playerName = protocolData[0];
			return new StartGameCommand(serverInstance, sessionId, playerName, roomId);
		}
		return new ErrorCommand("Unknown Command");
	}
}
