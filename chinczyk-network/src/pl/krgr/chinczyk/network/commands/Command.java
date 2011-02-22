package pl.krgr.chinczyk.network.commands;

public interface Command {

	void execute();
	String request();
	String response();
}
