package pl.krgr.chinczyk.network.commands;

public interface ClientCommand {

	void execute();
	String getRequest();
	void setResponse(String response);
}
