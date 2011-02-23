package pl.krgr.chinczyk.network.commands;

public interface ServerCommand {
	
	void execute();
	String getResponse();
}
