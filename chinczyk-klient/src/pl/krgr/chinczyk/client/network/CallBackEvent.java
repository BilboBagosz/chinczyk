package pl.krgr.chinczyk.client.network;

public class CallBackEvent {

	private boolean result = false;
	private String message;
	private Object eventStructure;
	
	public CallBackEvent(boolean result, String message, Object eventStructure) {
		this.result = result;
		this.message = message;
		this.eventStructure = eventStructure;
	}
	
	public CallBackEvent(boolean result, String message) {
		this.result = result;
		this.message = message;
	}

	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getEventStructure() {
		return eventStructure;
	}
	public void setEventStructure(Object eventStructure) {
		this.eventStructure = eventStructure;
	}
	
}
