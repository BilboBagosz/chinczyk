package pl.krgr.chinczyk.server.network.commands;

import pl.krgr.chinczyk.network.Responses;

public class ErrorCommand extends AbstractCommand {

	private String message;
	
	public ErrorCommand(String message) {
		super(null, -1);
		this.message = message;
	}
	
	@Override
	public void execute() {
		response = String.format(Responses.ERROR, message);
	}
}
