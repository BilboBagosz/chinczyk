package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.control.GameAlreadyStartedException;
import pl.krgr.chinczyk.control.PlayerAlreadyRegisteredException;
import pl.krgr.chinczyk.model.Camp;
import pl.krgr.chinczyk.network.Responses;
import pl.krgr.chinczyk.server.NotConnectedException;
import pl.krgr.chinczyk.server.Server;

public class JoinRoomCommand extends AbstractServerCommand {

	private int roomId;
	private String playerName;
	private Camp camp;
	
	public JoinRoomCommand(Server server, int sessionId, int roomId, String playerName, Camp camp) {
		super(server, sessionId);
		this.roomId = roomId;
		this.playerName = playerName;
		this.camp = camp;
	}

	@Override
	public void execute() {
		try {
			String roomInfo = server.joinRoom(roomId, sessionId, playerName, camp);		
			if (roomInfo == null) {
				response = String.format(Responses.ERROR, "Incorect roomId, room = null");
				return;
			}
			response = Responses.OK + roomInfo;
		} catch (GameAlreadyStartedException e) {
			e.printStackTrace();
			response = String.format(Responses.ERROR, "GameAlreadyStarted");
		} catch (PlayerAlreadyRegisteredException e) {
			e.printStackTrace();
			response = String.format(Responses.ERROR, "PlayerAlreadyRegistered");
		} catch (NotConnectedException e) {
			e.printStackTrace();
			response = String.format(Responses.ERROR, "PlayerNotConnected");
		}
	}
}
