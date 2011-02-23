package pl.krgr.chinczyk.network.commands;

public interface CommandFactory {

	public ServerCommand createCommand(String request);
	
}
