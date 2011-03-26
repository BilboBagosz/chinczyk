package pl.krgr.chinczyk.client.presentation;

import pl.krgr.chinczyk.client.network.CallBackEvent;
import pl.krgr.chinczyk.client.network.HandlerCallback;

public class IgnoreHandlerCallback implements HandlerCallback {

	@Override
	public void commandExecuted(CallBackEvent event) {
		//do nothing, ignore
	}

}
